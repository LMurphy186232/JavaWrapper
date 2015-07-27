package sortie.data.funcgroups.substrate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SubstrateBehaviors;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class DetailedSubstrateTest extends TestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      SubstrateBehaviors oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
      assertEquals(1, p_oBehs.size());
      DetailedSubstrate oSub = (DetailedSubstrate) p_oBehs.get(0);
      
      assertEquals(40, oSub.m_fLogSizeClassBoundary.getValue(), 0.001);
      
      ModelEnum oEnum = (ModelEnum) oSub.mp_iSpeciesGroup.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(0.1, oSub.m_fPropFallenTreesDecayClass1.getValue(), 0.001);
      assertEquals(0.2, oSub.m_fPropFallenTreesDecayClass2.getValue(), 0.001);
      assertEquals(0.3, oSub.m_fPropFallenTreesDecayClass3.getValue(), 0.001);
      assertEquals(0.4, oSub.m_fPropFallenTreesDecayClass4.getValue(), 0.001);
      assertEquals(0.5, oSub.m_fPropFallenTreesDecayClass5.getValue(), 0.001);
      assertEquals(0.6, oSub.m_fPropFallenSnagsDecayClass1.getValue(), 0.001);
      assertEquals(0.7, oSub.m_fPropFallenSnagsDecayClass2.getValue(), 0.001);
      assertEquals(0.8, oSub.m_fPropFallenSnagsDecayClass3.getValue(), 0.001);
      assertEquals(0.9, oSub.m_fPropFallenSnagsDecayClass4.getValue(), 0.001);
      assertEquals(1.0, oSub.m_fPropFallenSnagsDecayClass5.getValue(), 0.001);
      assertEquals(-1.1, oSub.m_fSpGroup1SmallClass1Alpha.getValue(), 0.001);
      assertEquals(-0.81, oSub.m_fSpGroup1SmallClass2Alpha.getValue(), 0.001);
      assertEquals(-1.21, oSub.m_fSpGroup1SmallClass3Alpha.getValue(), 0.001);
      assertEquals(-0.5, oSub.m_fSpGroup1SmallClass4Alpha.getValue(), 0.001);
      assertEquals(-1.2, oSub.m_fSpGroup1SmallClass5Alpha.getValue(), 0.001);
      assertEquals(-1.22, oSub.m_fSpGroup1LargeClass1Alpha.getValue(), 0.001);
      assertEquals(-0.52, oSub.m_fSpGroup1LargeClass2Alpha.getValue(), 0.001);
      assertEquals(-1.3, oSub.m_fSpGroup1LargeClass3Alpha.getValue(), 0.001);
      assertEquals(-1.4, oSub.m_fSpGroup1LargeClass4Alpha.getValue(), 0.001);
      assertEquals(-0.82, oSub.m_fSpGroup1LargeClass5Alpha.getValue(), 0.001);
      assertEquals(1, oSub.m_fSpGroup1SmallClass1Beta.getValue(), 0.001);
      assertEquals(1.4, oSub.m_fSpGroup1SmallClass2Beta.getValue(), 0.001);
      assertEquals(1.5, oSub.m_fSpGroup1SmallClass3Beta.getValue(), 0.001);
      assertEquals(2.1, oSub.m_fSpGroup1SmallClass4Beta.getValue(), 0.001);
      assertEquals(1.7, oSub.m_fSpGroup1SmallClass5Beta.getValue(), 0.001);
      assertEquals(1.8, oSub.m_fSpGroup1LargeClass1Beta.getValue(), 0.001);
      assertEquals(2.5, oSub.m_fSpGroup1LargeClass2Beta.getValue(), 0.001);
      assertEquals(1.11, oSub.m_fSpGroup1LargeClass3Beta.getValue(), 0.001);
      assertEquals(1.12, oSub.m_fSpGroup1LargeClass4Beta.getValue(), 0.001);
      assertEquals(1.13, oSub.m_fSpGroup1LargeClass5Beta.getValue(), 0.001);
      assertEquals(0.01, oSub.m_fSpGroup1SmallClass1InitLog.getValue(), 0.001);
      assertEquals(0.02, oSub.m_fSpGroup1SmallClass2InitLog.getValue(), 0.001);
      assertEquals(0.03, oSub.m_fSpGroup1SmallClass3InitLog.getValue(), 0.001);
      assertEquals(0.04, oSub.m_fSpGroup1SmallClass4InitLog.getValue(), 0.001);
      assertEquals(0.08, oSub.m_fSpGroup1SmallClass5InitLog.getValue(), 0.001);
      assertEquals(0.71, oSub.m_fSpGroup1LargeClass1InitLog.getValue(), 0.001);
      assertEquals(0.72, oSub.m_fSpGroup1LargeClass2InitLog.getValue(), 0.001);
      assertEquals(0.73, oSub.m_fSpGroup1LargeClass3InitLog.getValue(), 0.001);
      assertEquals(0.74, oSub.m_fSpGroup1LargeClass4InitLog.getValue(), 0.001);
      assertEquals(0.75, oSub.m_fSpGroup1LargeClass5InitLog.getValue(), 0.001);
      assertEquals(0.81, oSub.m_fInitCondScarSoil.getValue(), 0.001);
      assertEquals(0.82, oSub.m_fInitCondTipup.getValue(), 0.001);
      assertEquals(-0.5, oSub.m_fScarSoilA.getValue(), 0.001);
      assertEquals(1, oSub.m_fScarSoilB.getValue(), 0.001);
      assertEquals(-0.55, oSub.m_fTipUpA.getValue(), 0.001);
      assertEquals(1.5, oSub.m_fTipUpB.getValue(), 0.001);
      assertEquals(2, oSub.m_iMaxDecayTime.getValue());
      assertEquals(0.45, ((Float)oSub.mp_fProportionOfFallenThatUproot.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.46, ((Float)oSub.mp_fProportionOfSnagsThatUproot.getValue().get(0)).floatValue(), 0.001);
      assertEquals(3, oSub.m_fRootTipupFactor.getValue(), 0.001);
      assertEquals(0.4, oSub.m_fMossProportion.getValue(), 0.001);
      assertEquals(0, oSub.m_iDirectionalTreeFall.getValue());
      assertEquals(20, oSub.m_fInitSmallLogMeanDiam.getValue(), 0.001);
      assertEquals(50, oSub.m_fInitLargeLogMeanDiam.getValue(), 0.001);
    
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
   * Tests ValidateData().
   * 
   * @throws ModelException if there's an uncaught validation problem.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SubstrateBehaviors oSubBeh = null;
    DetailedSubstrate oSub = null;
    try {
      try {

        oManager = new GUIManager(null);

        // Valid file 1
        sFileName = writeXMLValidFile();
        oManager.inputXMLParameterFile(sFileName);
        oSubBeh = oManager.getSubstrateBehaviors();
        ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
        assertEquals(1, p_oBehs.size());
        oSub = (DetailedSubstrate) p_oBehs.get(0);
        oSub.validateData(oManager.getTreePopulation());
      } catch (ModelException oErr) {
        fail("Substrate validation failed with message " + oErr.getMessage());
      } catch (java.io.IOException oE) {
        fail("Caught IOException.  Message: " + oE.getMessage());
      } finally {
        new File(sFileName).delete();
      }

      oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
      assertEquals(1, p_oBehs.size());
      oSub = (DetailedSubstrate) p_oBehs.get(0);

      // Case: betas too large
      float fBadValue = 100000, fTemp = 0;
      try {
        fTemp = oSub.m_fScarSoilB.getValue();
        oSub.m_fScarSoilB.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fScarSoilB.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fTipUpB.getValue();
        oSub.m_fTipUpB.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fTipUpB.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass1Beta.getValue();
        oSub.m_fSpGroup1SmallClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass2Beta.getValue();
        oSub.m_fSpGroup1SmallClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass3Beta.getValue();
        oSub.m_fSpGroup1SmallClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass4Beta.getValue();
        oSub.m_fSpGroup1SmallClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass5Beta.getValue();
        oSub.m_fSpGroup1SmallClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass1Beta.getValue();
        oSub.m_fSpGroup2SmallClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass2Beta.getValue();
        oSub.m_fSpGroup2SmallClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass3Beta.getValue();
        oSub.m_fSpGroup2SmallClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass4Beta.getValue();
        oSub.m_fSpGroup2SmallClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass5Beta.getValue();
        oSub.m_fSpGroup2SmallClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass1Beta.getValue();
        oSub.m_fSpGroup3SmallClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass2Beta.getValue();
        oSub.m_fSpGroup3SmallClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass3Beta.getValue();
        oSub.m_fSpGroup3SmallClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass4Beta.getValue();
        oSub.m_fSpGroup3SmallClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass5Beta.getValue();
        oSub.m_fSpGroup3SmallClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass1Beta.getValue();
        oSub.m_fSpGroup1LargeClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass2Beta.getValue();
        oSub.m_fSpGroup1LargeClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass3Beta.getValue();
        oSub.m_fSpGroup1LargeClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass4Beta.getValue();
        oSub.m_fSpGroup1LargeClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass5Beta.getValue();
        oSub.m_fSpGroup1LargeClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass1Beta.getValue();
        oSub.m_fSpGroup2LargeClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass2Beta.getValue();
        oSub.m_fSpGroup2LargeClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass3Beta.getValue();
        oSub.m_fSpGroup2LargeClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass4Beta.getValue();
        oSub.m_fSpGroup2LargeClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass5Beta.getValue();
        oSub.m_fSpGroup2LargeClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass1Beta.getValue();
        oSub.m_fSpGroup3LargeClass1Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass1Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass2Beta.getValue();
        oSub.m_fSpGroup3LargeClass2Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass2Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass3Beta.getValue();
        oSub.m_fSpGroup3LargeClass3Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass3Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass4Beta.getValue();
        oSub.m_fSpGroup3LargeClass4Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass4Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass5Beta.getValue();
        oSub.m_fSpGroup3LargeClass5Beta.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge beta.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass5Beta.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      // Case: positive alphas
      fBadValue = 1;
      try {
        fTemp = oSub.m_fScarSoilA.getValue();
        oSub.m_fScarSoilA.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fScarSoilA.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fTipUpA.getValue();
        oSub.m_fTipUpA.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fTipUpA.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass1Alpha.getValue();
        oSub.m_fSpGroup1SmallClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass2Alpha.getValue();
        oSub.m_fSpGroup1SmallClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass3Alpha.getValue();
        oSub.m_fSpGroup1SmallClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass4Alpha.getValue();
        oSub.m_fSpGroup1SmallClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1SmallClass5Alpha.getValue();
        oSub.m_fSpGroup1SmallClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1SmallClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass1Alpha.getValue();
        oSub.m_fSpGroup2SmallClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass2Alpha.getValue();
        oSub.m_fSpGroup2SmallClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass3Alpha.getValue();
        oSub.m_fSpGroup2SmallClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass4Alpha.getValue();
        oSub.m_fSpGroup2SmallClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2SmallClass5Alpha.getValue();
        oSub.m_fSpGroup2SmallClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2SmallClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass1Alpha.getValue();
        oSub.m_fSpGroup3SmallClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass2Alpha.getValue();
        oSub.m_fSpGroup3SmallClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass3Alpha.getValue();
        oSub.m_fSpGroup3SmallClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass4Alpha.getValue();
        oSub.m_fSpGroup3SmallClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3SmallClass5Alpha.getValue();
        oSub.m_fSpGroup3SmallClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3SmallClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass1Alpha.getValue();
        oSub.m_fSpGroup1LargeClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass2Alpha.getValue();
        oSub.m_fSpGroup1LargeClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass3Alpha.getValue();
        oSub.m_fSpGroup1LargeClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass4Alpha.getValue();
        oSub.m_fSpGroup1LargeClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass5Alpha.getValue();
        oSub.m_fSpGroup1LargeClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass1Alpha.getValue();
        oSub.m_fSpGroup2LargeClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass2Alpha.getValue();
        oSub.m_fSpGroup2LargeClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass3Alpha.getValue();
        oSub.m_fSpGroup2LargeClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass4Alpha.getValue();
        oSub.m_fSpGroup2LargeClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup2LargeClass5Alpha.getValue();
        oSub.m_fSpGroup2LargeClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup2LargeClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass1Alpha.getValue();
        oSub.m_fSpGroup3LargeClass1Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass1Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass2Alpha.getValue();
        oSub.m_fSpGroup3LargeClass2Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass2Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass3Alpha.getValue();
        oSub.m_fSpGroup3LargeClass3Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass3Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass4Alpha.getValue();
        oSub.m_fSpGroup3LargeClass4Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass4Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup3LargeClass5Alpha.getValue();
        oSub.m_fSpGroup3LargeClass5Alpha.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch huge alpha.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup3LargeClass5Alpha.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      // Case: initial conditions proportions are greater than one
      fBadValue = 1;
      try {
        fTemp = oSub.m_fSpGroup1LargeClass1InitLog.getValue();
        oSub.m_fSpGroup1LargeClass1InitLog.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad initial conditions.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1InitLog.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass1PartCutLog.getValue();
        oSub.m_fSpGroup1LargeClass1PartCutLog.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad initial conditions.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1PartCutLog.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass1GapCutLog.getValue();
        oSub.m_fSpGroup1LargeClass1GapCutLog.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad initial conditions.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1GapCutLog.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fSpGroup1LargeClass1ClearCutLog.getValue();
        oSub.m_fSpGroup1LargeClass1ClearCutLog.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad initial conditions.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fSpGroup1LargeClass1ClearCutLog.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      // Case: log mean diameters are not on the correct side of the log
      // boundary
      fBadValue = oSub.m_fLogSizeClassBoundary.getValue() + 1;
      try {
        fTemp = oSub.m_fInitSmallLogMeanDiam.getValue();
        oSub.m_fInitSmallLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad small log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fInitSmallLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fPartCutSmallLogMeanDiam.getValue();
        oSub.m_fPartCutSmallLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad small log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fPartCutSmallLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fGapCutSmallLogMeanDiam.getValue();
        oSub.m_fGapCutSmallLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad small log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fGapCutSmallLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fClearCutSmallLogMeanDiam.getValue();
        oSub.m_fClearCutSmallLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad small log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fClearCutSmallLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      fBadValue = oSub.m_fLogSizeClassBoundary.getValue() - 1;
      try {
        fTemp = oSub.m_fInitLargeLogMeanDiam.getValue();
        oSub.m_fInitLargeLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad large log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fInitLargeLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fPartCutLargeLogMeanDiam.getValue();
        oSub.m_fPartCutLargeLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad large log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fPartCutLargeLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fGapCutLargeLogMeanDiam.getValue();
        oSub.m_fGapCutLargeLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad large log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fGapCutLargeLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      try {
        fTemp = oSub.m_fClearCutLargeLogMeanDiam.getValue();
        oSub.m_fClearCutLargeLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch bad large log mean diameter.");
      } catch (ModelException oErr) {
        ;
      }
      oSub.m_fClearCutLargeLogMeanDiam.setValue(fTemp);
      oSubBeh.validateData(oManager.getTreePopulation());

      // Case - log diams ignored if harvest not used
      p_oBehs = oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oBehs.size());
      oManager.getDisturbanceBehaviors().removeBehavior(p_oBehs.get(0));
      try {
        fTemp = oSub.m_fClearCutLargeLogMeanDiam.getValue();
        oSub.m_fClearCutLargeLogMeanDiam.setValue(fBadValue);
        oSubBeh.validateData(oManager.getTreePopulation());
      } catch (ModelException oErr) {
        fail("Substrate validation failed with message " + oErr.getMessage());
      }

      //Case - error if snag dynamics not used
      try {
        p_oBehs = oManager.getSnagDynamicsBehaviors()
            .getBehaviorByParameterFileTag("SnagDecayClassDynamics");
        oManager.getSnagDynamicsBehaviors().removeBehavior(p_oBehs.get(0));
        oSubBeh.validateData(oManager.getTreePopulation());
        fail("Substrate didn't catch missing snag dynamics behavior.");
      } catch (ModelException oErr) {;}
    } catch (ModelException oErr) {
      fail("Substrate validation failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }

    System.out.println("Substrate ValidateData testing succeeded.");
  }


  /**
   * Tests species changes. Even though SubstrateBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile();
      oManager.inputXMLParameterFile(sFileName);
      SubstrateBehaviors oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
      assertEquals(1, p_oBehs.size());
      DetailedSubstrate oSubs = (DetailedSubstrate) p_oBehs.get(0);

      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(0)).floatValue(), 0.048, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(1)).floatValue(), 0.049, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(2)).floatValue(), 0.053, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(0)).floatValue(), 0.051, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(1)).floatValue(), 0.052, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(2)).floatValue(), 0.054, 0.0001);

      ModelEnum oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(1);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(2);
      assertEquals(oEnum.getValue(), 3);

      // Now change the species
      String[] sNewSpecies = new String[] { "Species 4", "Species 3", 
          "Species 2",  "Species 1" };
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oSubBeh = oManager.getSubstrateBehaviors();
      p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
      assertEquals(1, p_oBehs.size());
      oSubs = (DetailedSubstrate) p_oBehs.get(0);

      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(3)).floatValue(), 0.048, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(2)).floatValue(), 0.049, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(1)).floatValue(), 0.053, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(3)).floatValue(), 0.051, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(2)).floatValue(), 0.052, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(1)).floatValue(), 0.054, 0.0001);

      int i;
      assertEquals(4, oSubs.mp_iSpeciesGroup.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oSubs.mp_iSpeciesGroup.getValue().get(i));
      }
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(3);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(2);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(1);
      assertEquals(oEnum.getValue(), 3);

      System.out.println("Change of species test succeeded.");
    } catch (ModelException oErr) {
      fail("Change of species (substrate) test failed with message "
          + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }


  /**
   * Tests to make sure parameter files are properly read.
   */
  public void testParFileRead() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);      
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile();
      oManager.inputXMLParameterFile(sFileName);
      SubstrateBehaviors oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("DetailedSubstrate");
      assertEquals(1, p_oBehs.size());
      DetailedSubstrate oSubs = (DetailedSubstrate) p_oBehs.get(0);

      assertEquals(oSubs.m_fLogSizeClassBoundary.getValue(), 40, 0.0001);
      ModelEnum oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(1);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oSubs.mp_iSpeciesGroup.getValue().get(2);
      assertEquals(oEnum.getValue(), 3);
      assertEquals(oSubs.m_fPropFallenTreesDecayClass1.getValue(), 0.1, 0.0001);
      assertEquals(oSubs.m_fPropFallenTreesDecayClass2.getValue(), 0.2, 0.0001);
      assertEquals(oSubs.m_fPropFallenTreesDecayClass3.getValue(), 0.3, 0.0001);
      assertEquals(oSubs.m_fPropFallenTreesDecayClass4.getValue(), 0.04, 0.0001);
      assertEquals(oSubs.m_fPropFallenTreesDecayClass5.getValue(), 0.36, 0.0001);
      assertEquals(oSubs.m_fPropFallenSnagsDecayClass1.getValue(), 0.006, 0.0001);
      assertEquals(oSubs.m_fPropFallenSnagsDecayClass2.getValue(), 0.007, 0.0001);
      assertEquals(oSubs.m_fPropFallenSnagsDecayClass3.getValue(), 0.008, 0.0001);
      assertEquals(oSubs.m_fPropFallenSnagsDecayClass4.getValue(), 0.009, 0.0001);
      assertEquals(oSubs.m_fPropFallenSnagsDecayClass5.getValue(), 0.97, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1Alpha.getValue(), -1, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2Alpha.getValue(), -0.8, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3Alpha.getValue(), -1.2, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4Alpha.getValue(), -0.5, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5Alpha.getValue(), -2, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1Alpha.getValue(), -3, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2Alpha.getValue(), -4, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3Alpha.getValue(), -5, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4Alpha.getValue(), -6, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5Alpha.getValue(), -7, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1Beta.getValue(), -8, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2Beta.getValue(), -9, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3Beta.getValue(), -10, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4Beta.getValue(), -11, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5Beta.getValue(), -12, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1Beta.getValue(), -13, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2Beta.getValue(), -14, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3Beta.getValue(), -15, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4Beta.getValue(), -16, 0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5Beta.getValue(), -17, 0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1InitLog.getValue(), 0.0011,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2InitLog.getValue(), 0.0012,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3InitLog.getValue(), 0.0013,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4InitLog.getValue(), 0.0014,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5InitLog.getValue(), 0.0015,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1InitLog.getValue(), 0.0016,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2InitLog.getValue(), 0.0017,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3InitLog.getValue(), 0.0018,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4InitLog.getValue(), 0.0019,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5InitLog.getValue(), 0.002,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1PartCutLog.getValue(), 0.0021,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2PartCutLog.getValue(), 0.0022,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3PartCutLog.getValue(), 0.0023,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4PartCutLog.getValue(), 0.0024,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5PartCutLog.getValue(), 0.0025,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1PartCutLog.getValue(), 0.0026,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2PartCutLog.getValue(), 0.0027,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3PartCutLog.getValue(), 0.0028,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4PartCutLog.getValue(), 0.0029,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5PartCutLog.getValue(), 0.0030,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1GapCutLog.getValue(), 0.0031,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2GapCutLog.getValue(), 0.0032,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3GapCutLog.getValue(), 0.0033,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4GapCutLog.getValue(), 0.0034,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5GapCutLog.getValue(), 0.0035,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1GapCutLog.getValue(), 0.0036,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2GapCutLog.getValue(), 0.0037,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3GapCutLog.getValue(), 0.0038,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4GapCutLog.getValue(), 0.0039,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5GapCutLog.getValue(), 0.0040,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass1ClearCutLog.getValue(), 0.0041,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass2ClearCutLog.getValue(), 0.0042,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass3ClearCutLog.getValue(), 0.0043,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass4ClearCutLog.getValue(), 0.0044,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1SmallClass5ClearCutLog.getValue(), 0.0045,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass1ClearCutLog.getValue(), 0.0046,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass2ClearCutLog.getValue(), 0.0047,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass3ClearCutLog.getValue(), 0.0048,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass4ClearCutLog.getValue(), 0.0049,
          0.0001);
      assertEquals(oSubs.m_fSpGroup1LargeClass5ClearCutLog.getValue(), 0.0050,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1Alpha.getValue(), -18, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2Alpha.getValue(), -19, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3Alpha.getValue(), -20, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4Alpha.getValue(), -21, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5Alpha.getValue(), -22, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1Alpha.getValue(), -23, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2Alpha.getValue(), -24, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3Alpha.getValue(), -25, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4Alpha.getValue(), -26, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5Alpha.getValue(), -27, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1Beta.getValue(), 0.012, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2Beta.getValue(), 0.013, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3Beta.getValue(), 0.014, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4Beta.getValue(), 0.015, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5Beta.getValue(), 0.016, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1Beta.getValue(), 0.017, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2Beta.getValue(), 0.018, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3Beta.getValue(), 0.019, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4Beta.getValue(), 0.020, 0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5Beta.getValue(), 0.021, 0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1InitLog.getValue(), 0.0051,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2InitLog.getValue(), 0.0052,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3InitLog.getValue(), 0.0053,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4InitLog.getValue(), 0.0054,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5InitLog.getValue(), 0.0055,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1InitLog.getValue(), 0.0056,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2InitLog.getValue(), 0.0057,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3InitLog.getValue(), 0.0058,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4InitLog.getValue(), 0.0059,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5InitLog.getValue(), 0.0060,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1PartCutLog.getValue(), 0.0061,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2PartCutLog.getValue(), 0.0062,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3PartCutLog.getValue(), 0.0063,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4PartCutLog.getValue(), 0.0064,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5PartCutLog.getValue(), 0.0065,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1PartCutLog.getValue(), 0.0066,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2PartCutLog.getValue(), 0.0067,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3PartCutLog.getValue(), 0.0068,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4PartCutLog.getValue(), 0.0069,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5PartCutLog.getValue(), 0.0070,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1GapCutLog.getValue(), 0.0071,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2GapCutLog.getValue(), 0.0072,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3GapCutLog.getValue(), 0.0073,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4GapCutLog.getValue(), 0.0074,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5GapCutLog.getValue(), 0.0075,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1GapCutLog.getValue(), 0.0076,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2GapCutLog.getValue(), 0.0077,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3GapCutLog.getValue(), 0.0078,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4GapCutLog.getValue(), 0.0079,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5GapCutLog.getValue(), 0.0080,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass1ClearCutLog.getValue(), 0.0081,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass2ClearCutLog.getValue(), 0.0082,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass3ClearCutLog.getValue(), 0.0083,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass4ClearCutLog.getValue(), 0.0084,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2SmallClass5ClearCutLog.getValue(), 0.0085,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass1ClearCutLog.getValue(), 0.0086,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass2ClearCutLog.getValue(), 0.0087,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass3ClearCutLog.getValue(), 0.0088,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass4ClearCutLog.getValue(), 0.0089,
          0.0001);
      assertEquals(oSubs.m_fSpGroup2LargeClass5ClearCutLog.getValue(), 0.0090,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1Alpha.getValue(), -0.1, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2Alpha.getValue(), -0.2, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3Alpha.getValue(), -0.3, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4Alpha.getValue(), -0.4, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5Alpha.getValue(), -0.5, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1Alpha.getValue(), -0.6, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2Alpha.getValue(), -0.7, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3Alpha.getValue(), -0.8, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4Alpha.getValue(), -0.9, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5Alpha.getValue(), -0.11, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1Beta.getValue(), -0.12, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2Beta.getValue(), -0.13, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3Beta.getValue(), -0.14, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4Beta.getValue(), -0.15, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5Beta.getValue(), -0.16, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1Beta.getValue(), -0.17, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2Beta.getValue(), -0.18, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3Beta.getValue(), -0.19, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4Beta.getValue(), -0.21, 0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5Beta.getValue(), -0.22, 0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1InitLog.getValue(), 0.0091,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2InitLog.getValue(), 0.0092,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3InitLog.getValue(), 0.0093,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4InitLog.getValue(), 0.0094,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5InitLog.getValue(), 0.0095,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1InitLog.getValue(), 0.0096,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2InitLog.getValue(), 0.0097,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3InitLog.getValue(), 0.0098,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4InitLog.getValue(), 0.0099,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5InitLog.getValue(), 0.0001,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1PartCutLog.getValue(), 0.0002,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2PartCutLog.getValue(), 0.0003,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3PartCutLog.getValue(), 0.0004,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4PartCutLog.getValue(), 0.0005,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5PartCutLog.getValue(), 0.0006,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1PartCutLog.getValue(), 0.0007,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2PartCutLog.getValue(), 0.0008,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3PartCutLog.getValue(), 0.0009,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4PartCutLog.getValue(), 0.00011,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5PartCutLog.getValue(), 0.00012,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1GapCutLog.getValue(), 0.00013,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2GapCutLog.getValue(), 0.00014,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3GapCutLog.getValue(), 0.00015,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4GapCutLog.getValue(), 0.00016,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5GapCutLog.getValue(), 0.00017,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1GapCutLog.getValue(), 0.00018,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2GapCutLog.getValue(), 0.00019,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3GapCutLog.getValue(), 0.00021,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4GapCutLog.getValue(), 0.00022,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5GapCutLog.getValue(), 0.00023,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass1ClearCutLog.getValue(), 0.00024,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass2ClearCutLog.getValue(), 0.00025,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass3ClearCutLog.getValue(), 0.00026,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass4ClearCutLog.getValue(), 0.00027,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3SmallClass5ClearCutLog.getValue(), 0.00028,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass1ClearCutLog.getValue(), 0.00029,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass2ClearCutLog.getValue(), 0.00031,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass3ClearCutLog.getValue(), 0.00032,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass4ClearCutLog.getValue(), 0.00033,
          0.0001);
      assertEquals(oSubs.m_fSpGroup3LargeClass5ClearCutLog.getValue(), 0.00034,
          0.0001);
      assertEquals(oSubs.m_fGapCutScarSoil.getValue(), 0.035, 0.0001);
      assertEquals(oSubs.m_fGapCutTipup.getValue(), 0.036, 0.0001);
      assertEquals(oSubs.m_fPartialCutScarSoil.getValue(), 0.037, 0.0001);
      assertEquals(oSubs.m_fPartialCutTipup.getValue(), 0.038, 0.0001);
      assertEquals(oSubs.m_fInitCondScarSoil.getValue(), 0.039, 0.0001);
      assertEquals(oSubs.m_fInitCondTipup.getValue(), 0.041, 0.0001);
      assertEquals(oSubs.m_fClearCutScarSoil.getValue(), 0.042, 0.0001);
      assertEquals(oSubs.m_fClearCutTipup.getValue(), 0.043, 0.0001);
      assertEquals(oSubs.m_fScarSoilA.getValue(), -0.044, 0.0001);
      assertEquals(oSubs.m_fScarSoilB.getValue(), 0.045, 0.0001);
      assertEquals(oSubs.m_fTipUpA.getValue(), -0.046, 0.0001);
      assertEquals(oSubs.m_fTipUpB.getValue(), 0.047, 0.0001);
      assertEquals(oSubs.m_iMaxDecayTime.getValue(), 2);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue().get(0)).floatValue(), 0.048, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue().get(1)).floatValue(), 0.049, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue().get(2)).floatValue(), 0.053, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(0)).floatValue(), 0.051, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(1)).floatValue(), 0.052, 0.0001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(2)).floatValue(), 0.054, 0.0001);
      assertEquals(oSubs.m_fRootTipupFactor.getValue(), 3, 0.0001);
      assertEquals(oSubs.m_fMossProportion.getValue(), 0.4, 0.0001);
      assertEquals(oSubs.m_iDirectionalTreeFall.getValue(), 0);
      assertEquals(oSubs.m_fInitSmallLogMeanDiam.getValue(), 20, 0.0001);
      assertEquals(oSubs.m_fInitLargeLogMeanDiam.getValue(), 50, 0.0001);
      assertEquals(oSubs.m_fPartCutSmallLogMeanDiam.getValue(), 21, 0.0001);
      assertEquals(oSubs.m_fPartCutLargeLogMeanDiam.getValue(), 51, 0.0001);
      assertEquals(oSubs.m_fGapCutSmallLogMeanDiam.getValue(), 22, 0.0001);
      assertEquals(oSubs.m_fGapCutLargeLogMeanDiam.getValue(), 52, 0.0001);
      assertEquals(oSubs.m_fClearCutSmallLogMeanDiam.getValue(), 23, 0.0001);
      assertEquals(oSubs.m_fClearCutLargeLogMeanDiam.getValue(), 53, 0.0001);
      
      //Check grid setup
      assertEquals(2, oSubs.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("DetailedSubstrate");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      
    } catch (ModelException oErr) {
      fail("Substrate parameter file read test failed with message "
          + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      if (sFileName != null) {
        new File(sFileName).delete();
      }
    }
  }

  /**
   * Writes a detailed substrate file.
   * 
   * @throws IOException if there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLValidFile() throws IOException {
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
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Harvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    // Have to have a mortality behavior so dead is registered
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    // Have to have a snag dynamics behavior to register
    // appropriate data members
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SnagDecayClassDynamics</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DetailedSubstrate</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"DetailedSubstrate\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<Harvest1>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Species_1\" />");
    oOut.write("<ha_timestep>2</ha_timestep>");
    oOut.write("<ha_cutType>gap</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>25.0</ha_high>");
    oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_applyToCell x=\"0\" y=\"2\" />");
    oOut.write("</ha_cutEvent>");
    oOut.write("</Harvest1>");
    oOut.write("<StochasticMortality2>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_1\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_2\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_3\">0.0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality2>");
    oOut.write("<DetailedSubstrate4>");
    oOut.write("<su_logSizeClassBoundary>40</su_logSizeClassBoundary>");
    oOut.write("<su_logSpGroupAssignment>");
    oOut.write("<su_lsgaVal species=\"Species_1\">1</su_lsgaVal>");
    oOut.write("<su_lsgaVal species=\"Species_2\">2</su_lsgaVal>");
    oOut.write("<su_lsgaVal species=\"Species_3\">3</su_lsgaVal>");
    oOut.write("</su_logSpGroupAssignment>");
    oOut.write("<su_propFallenTreesLogDecayClass1>0.1</su_propFallenTreesLogDecayClass1>");
    oOut.write("<su_propFallenTreesLogDecayClass2>0.2</su_propFallenTreesLogDecayClass2>");
    oOut.write("<su_propFallenTreesLogDecayClass3>0.3</su_propFallenTreesLogDecayClass3>");
    oOut.write("<su_propFallenTreesLogDecayClass4>0.04</su_propFallenTreesLogDecayClass4>");
    oOut.write("<su_propFallenTreesLogDecayClass5>0.36</su_propFallenTreesLogDecayClass5>");
    oOut.write("<su_propFallenSnagsLogDecayClass1>0.006</su_propFallenSnagsLogDecayClass1>");
    oOut.write("<su_propFallenSnagsLogDecayClass2>0.007</su_propFallenSnagsLogDecayClass2>");
    oOut.write("<su_propFallenSnagsLogDecayClass3>0.008</su_propFallenSnagsLogDecayClass3>");
    oOut.write("<su_propFallenSnagsLogDecayClass4>0.009</su_propFallenSnagsLogDecayClass4>");
    oOut.write("<su_propFallenSnagsLogDecayClass5>0.97</su_propFallenSnagsLogDecayClass5>");
    oOut.write("<su_logSpGroup1SmallDecayClass1DecayAlpha>-1</su_logSpGroup1SmallDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass2DecayAlpha>-0.8</su_logSpGroup1SmallDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass3DecayAlpha>-1.2</su_logSpGroup1SmallDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass4DecayAlpha>-0.5</su_logSpGroup1SmallDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass5DecayAlpha>-2</su_logSpGroup1SmallDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass1DecayAlpha>-3</su_logSpGroup1LargeDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass2DecayAlpha>-4</su_logSpGroup1LargeDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass3DecayAlpha>-5</su_logSpGroup1LargeDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass4DecayAlpha>-6</su_logSpGroup1LargeDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass5DecayAlpha>-7</su_logSpGroup1LargeDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass1DecayBeta>-8</su_logSpGroup1SmallDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass2DecayBeta>-9</su_logSpGroup1SmallDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass3DecayBeta>-10</su_logSpGroup1SmallDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass4DecayBeta>-11</su_logSpGroup1SmallDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass5DecayBeta>-12</su_logSpGroup1SmallDecayClass5DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass1DecayBeta>-13</su_logSpGroup1LargeDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass2DecayBeta>-14</su_logSpGroup1LargeDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass3DecayBeta>-15</su_logSpGroup1LargeDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass4DecayBeta>-16</su_logSpGroup1LargeDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass5DecayBeta>-17</su_logSpGroup1LargeDecayClass5DecayBeta>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass1>0.0011</su_initialLogSpGroup1SmallDecayClass1>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass2>0.0012</su_initialLogSpGroup1SmallDecayClass2>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass3>0.0013</su_initialLogSpGroup1SmallDecayClass3>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass4>0.0014</su_initialLogSpGroup1SmallDecayClass4>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass5>0.0015</su_initialLogSpGroup1SmallDecayClass5>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass1>0.0016</su_initialLogSpGroup1LargeDecayClass1>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass2>0.0017</su_initialLogSpGroup1LargeDecayClass2>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass3>0.0018</su_initialLogSpGroup1LargeDecayClass3>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass4>0.0019</su_initialLogSpGroup1LargeDecayClass4>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass5>0.002</su_initialLogSpGroup1LargeDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup1SmallDecayClass1>0.0021</su_partialCutLogSpGroup1SmallDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup1SmallDecayClass2>0.0022</su_partialCutLogSpGroup1SmallDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup1SmallDecayClass3>0.0023</su_partialCutLogSpGroup1SmallDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup1SmallDecayClass4>0.0024</su_partialCutLogSpGroup1SmallDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup1SmallDecayClass5>0.0025</su_partialCutLogSpGroup1SmallDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup1LargeDecayClass1>0.0026</su_partialCutLogSpGroup1LargeDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup1LargeDecayClass2>0.0027</su_partialCutLogSpGroup1LargeDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup1LargeDecayClass3>0.0028</su_partialCutLogSpGroup1LargeDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup1LargeDecayClass4>0.0029</su_partialCutLogSpGroup1LargeDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup1LargeDecayClass5>0.0030</su_partialCutLogSpGroup1LargeDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup1SmallDecayClass1>0.0031</su_gapCutLogSpGroup1SmallDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup1SmallDecayClass2>0.0032</su_gapCutLogSpGroup1SmallDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup1SmallDecayClass3>0.0033</su_gapCutLogSpGroup1SmallDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup1SmallDecayClass4>0.0034</su_gapCutLogSpGroup1SmallDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup1SmallDecayClass5>0.0035</su_gapCutLogSpGroup1SmallDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup1LargeDecayClass1>0.0036</su_gapCutLogSpGroup1LargeDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup1LargeDecayClass2>0.0037</su_gapCutLogSpGroup1LargeDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup1LargeDecayClass3>0.0038</su_gapCutLogSpGroup1LargeDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup1LargeDecayClass4>0.0039</su_gapCutLogSpGroup1LargeDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup1LargeDecayClass5>0.0040</su_gapCutLogSpGroup1LargeDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup1SmallDecayClass1>0.0041</su_clearCutLogSpGroup1SmallDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup1SmallDecayClass2>0.0042</su_clearCutLogSpGroup1SmallDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup1SmallDecayClass3>0.0043</su_clearCutLogSpGroup1SmallDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup1SmallDecayClass4>0.0044</su_clearCutLogSpGroup1SmallDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup1SmallDecayClass5>0.0045</su_clearCutLogSpGroup1SmallDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup1LargeDecayClass1>0.0046</su_clearCutLogSpGroup1LargeDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup1LargeDecayClass2>0.0047</su_clearCutLogSpGroup1LargeDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup1LargeDecayClass3>0.0048</su_clearCutLogSpGroup1LargeDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup1LargeDecayClass4>0.0049</su_clearCutLogSpGroup1LargeDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup1LargeDecayClass5>0.0050</su_clearCutLogSpGroup1LargeDecayClass5>");
    oOut.write("<su_logSpGroup2SmallDecayClass1DecayAlpha>-18</su_logSpGroup2SmallDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup2SmallDecayClass2DecayAlpha>-19</su_logSpGroup2SmallDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup2SmallDecayClass3DecayAlpha>-20</su_logSpGroup2SmallDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup2SmallDecayClass4DecayAlpha>-21</su_logSpGroup2SmallDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup2SmallDecayClass5DecayAlpha>-22</su_logSpGroup2SmallDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup2LargeDecayClass1DecayAlpha>-23</su_logSpGroup2LargeDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup2LargeDecayClass2DecayAlpha>-24</su_logSpGroup2LargeDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup2LargeDecayClass3DecayAlpha>-25</su_logSpGroup2LargeDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup2LargeDecayClass4DecayAlpha>-26</su_logSpGroup2LargeDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup2LargeDecayClass5DecayAlpha>-27</su_logSpGroup2LargeDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup2SmallDecayClass1DecayBeta>0.012</su_logSpGroup2SmallDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup2SmallDecayClass2DecayBeta>0.013</su_logSpGroup2SmallDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup2SmallDecayClass3DecayBeta>0.014</su_logSpGroup2SmallDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup2SmallDecayClass4DecayBeta>0.015</su_logSpGroup2SmallDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup2SmallDecayClass5DecayBeta>0.016</su_logSpGroup2SmallDecayClass5DecayBeta>");
    oOut.write("<su_logSpGroup2LargeDecayClass1DecayBeta>0.017</su_logSpGroup2LargeDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup2LargeDecayClass2DecayBeta>0.018</su_logSpGroup2LargeDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup2LargeDecayClass3DecayBeta>0.019</su_logSpGroup2LargeDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup2LargeDecayClass4DecayBeta>0.020</su_logSpGroup2LargeDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup2LargeDecayClass5DecayBeta>0.021</su_logSpGroup2LargeDecayClass5DecayBeta>");
    oOut.write("<su_initialLogSpGroup2SmallDecayClass1>0.0051</su_initialLogSpGroup2SmallDecayClass1>");
    oOut.write("<su_initialLogSpGroup2SmallDecayClass2>0.0052</su_initialLogSpGroup2SmallDecayClass2>");
    oOut.write("<su_initialLogSpGroup2SmallDecayClass3>0.0053</su_initialLogSpGroup2SmallDecayClass3>");
    oOut.write("<su_initialLogSpGroup2SmallDecayClass4>0.0054</su_initialLogSpGroup2SmallDecayClass4>");
    oOut.write("<su_initialLogSpGroup2SmallDecayClass5>0.0055</su_initialLogSpGroup2SmallDecayClass5>");
    oOut.write("<su_initialLogSpGroup2LargeDecayClass1>0.0056</su_initialLogSpGroup2LargeDecayClass1>");
    oOut.write("<su_initialLogSpGroup2LargeDecayClass2>0.0057</su_initialLogSpGroup2LargeDecayClass2>");
    oOut.write("<su_initialLogSpGroup2LargeDecayClass3>0.0058</su_initialLogSpGroup2LargeDecayClass3>");
    oOut.write("<su_initialLogSpGroup2LargeDecayClass4>0.0059</su_initialLogSpGroup2LargeDecayClass4>");
    oOut.write("<su_initialLogSpGroup2LargeDecayClass5>0.0060</su_initialLogSpGroup2LargeDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup2SmallDecayClass1>0.0061</su_partialCutLogSpGroup2SmallDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup2SmallDecayClass2>0.0062</su_partialCutLogSpGroup2SmallDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup2SmallDecayClass3>0.0063</su_partialCutLogSpGroup2SmallDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup2SmallDecayClass4>0.0064</su_partialCutLogSpGroup2SmallDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup2SmallDecayClass5>0.0065</su_partialCutLogSpGroup2SmallDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup2LargeDecayClass1>0.0066</su_partialCutLogSpGroup2LargeDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup2LargeDecayClass2>0.0067</su_partialCutLogSpGroup2LargeDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup2LargeDecayClass3>0.0068</su_partialCutLogSpGroup2LargeDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup2LargeDecayClass4>0.0069</su_partialCutLogSpGroup2LargeDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup2LargeDecayClass5>0.0070</su_partialCutLogSpGroup2LargeDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup2SmallDecayClass1>0.0071</su_gapCutLogSpGroup2SmallDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup2SmallDecayClass2>0.0072</su_gapCutLogSpGroup2SmallDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup2SmallDecayClass3>0.0073</su_gapCutLogSpGroup2SmallDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup2SmallDecayClass4>0.0074</su_gapCutLogSpGroup2SmallDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup2SmallDecayClass5>0.0075</su_gapCutLogSpGroup2SmallDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup2LargeDecayClass1>0.0076</su_gapCutLogSpGroup2LargeDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup2LargeDecayClass2>0.0077</su_gapCutLogSpGroup2LargeDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup2LargeDecayClass3>0.0078</su_gapCutLogSpGroup2LargeDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup2LargeDecayClass4>0.0079</su_gapCutLogSpGroup2LargeDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup2LargeDecayClass5>0.0080</su_gapCutLogSpGroup2LargeDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup2SmallDecayClass1>0.0081</su_clearCutLogSpGroup2SmallDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup2SmallDecayClass2>0.0082</su_clearCutLogSpGroup2SmallDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup2SmallDecayClass3>0.0083</su_clearCutLogSpGroup2SmallDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup2SmallDecayClass4>0.0084</su_clearCutLogSpGroup2SmallDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup2SmallDecayClass5>0.0085</su_clearCutLogSpGroup2SmallDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup2LargeDecayClass1>0.0086</su_clearCutLogSpGroup2LargeDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup2LargeDecayClass2>0.0087</su_clearCutLogSpGroup2LargeDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup2LargeDecayClass3>0.0088</su_clearCutLogSpGroup2LargeDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup2LargeDecayClass4>0.0089</su_clearCutLogSpGroup2LargeDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup2LargeDecayClass5>0.0090</su_clearCutLogSpGroup2LargeDecayClass5>");
    oOut.write("<su_logSpGroup3SmallDecayClass1DecayAlpha>-0.1</su_logSpGroup3SmallDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup3SmallDecayClass2DecayAlpha>-0.2</su_logSpGroup3SmallDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup3SmallDecayClass3DecayAlpha>-0.3</su_logSpGroup3SmallDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup3SmallDecayClass4DecayAlpha>-0.4</su_logSpGroup3SmallDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup3SmallDecayClass5DecayAlpha>-0.5</su_logSpGroup3SmallDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup3LargeDecayClass1DecayAlpha>-0.6</su_logSpGroup3LargeDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup3LargeDecayClass2DecayAlpha>-0.7</su_logSpGroup3LargeDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup3LargeDecayClass3DecayAlpha>-0.8</su_logSpGroup3LargeDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup3LargeDecayClass4DecayAlpha>-0.9</su_logSpGroup3LargeDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup3LargeDecayClass5DecayAlpha>-0.11</su_logSpGroup3LargeDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup3SmallDecayClass1DecayBeta>-0.12</su_logSpGroup3SmallDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup3SmallDecayClass2DecayBeta>-0.13</su_logSpGroup3SmallDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup3SmallDecayClass3DecayBeta>-0.14</su_logSpGroup3SmallDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup3SmallDecayClass4DecayBeta>-0.15</su_logSpGroup3SmallDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup3SmallDecayClass5DecayBeta>-0.16</su_logSpGroup3SmallDecayClass5DecayBeta>");
    oOut.write("<su_logSpGroup3LargeDecayClass1DecayBeta>-0.17</su_logSpGroup3LargeDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup3LargeDecayClass2DecayBeta>-0.18</su_logSpGroup3LargeDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup3LargeDecayClass3DecayBeta>-0.19</su_logSpGroup3LargeDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup3LargeDecayClass4DecayBeta>-0.21</su_logSpGroup3LargeDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup3LargeDecayClass5DecayBeta>-0.22</su_logSpGroup3LargeDecayClass5DecayBeta>");
    oOut.write("<su_initialLogSpGroup3SmallDecayClass1>0.0091</su_initialLogSpGroup3SmallDecayClass1>");
    oOut.write("<su_initialLogSpGroup3SmallDecayClass2>0.0092</su_initialLogSpGroup3SmallDecayClass2>");
    oOut.write("<su_initialLogSpGroup3SmallDecayClass3>0.0093</su_initialLogSpGroup3SmallDecayClass3>");
    oOut.write("<su_initialLogSpGroup3SmallDecayClass4>0.0094</su_initialLogSpGroup3SmallDecayClass4>");
    oOut.write("<su_initialLogSpGroup3SmallDecayClass5>0.0095</su_initialLogSpGroup3SmallDecayClass5>");
    oOut.write("<su_initialLogSpGroup3LargeDecayClass1>0.0096</su_initialLogSpGroup3LargeDecayClass1>");
    oOut.write("<su_initialLogSpGroup3LargeDecayClass2>0.0097</su_initialLogSpGroup3LargeDecayClass2>");
    oOut.write("<su_initialLogSpGroup3LargeDecayClass3>0.0098</su_initialLogSpGroup3LargeDecayClass3>");
    oOut.write("<su_initialLogSpGroup3LargeDecayClass4>0.0099</su_initialLogSpGroup3LargeDecayClass4>");
    oOut.write("<su_initialLogSpGroup3LargeDecayClass5>0.0001</su_initialLogSpGroup3LargeDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup3SmallDecayClass1>0.0002</su_partialCutLogSpGroup3SmallDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup3SmallDecayClass2>0.0003</su_partialCutLogSpGroup3SmallDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup3SmallDecayClass3>0.0004</su_partialCutLogSpGroup3SmallDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup3SmallDecayClass4>0.0005</su_partialCutLogSpGroup3SmallDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup3SmallDecayClass5>0.0006</su_partialCutLogSpGroup3SmallDecayClass5>");
    oOut.write("<su_partialCutLogSpGroup3LargeDecayClass1>0.0007</su_partialCutLogSpGroup3LargeDecayClass1>");
    oOut.write("<su_partialCutLogSpGroup3LargeDecayClass2>0.0008</su_partialCutLogSpGroup3LargeDecayClass2>");
    oOut.write("<su_partialCutLogSpGroup3LargeDecayClass3>0.0009</su_partialCutLogSpGroup3LargeDecayClass3>");
    oOut.write("<su_partialCutLogSpGroup3LargeDecayClass4>0.00011</su_partialCutLogSpGroup3LargeDecayClass4>");
    oOut.write("<su_partialCutLogSpGroup3LargeDecayClass5>0.00012</su_partialCutLogSpGroup3LargeDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup3SmallDecayClass1>0.00013</su_gapCutLogSpGroup3SmallDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup3SmallDecayClass2>0.00014</su_gapCutLogSpGroup3SmallDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup3SmallDecayClass3>0.00015</su_gapCutLogSpGroup3SmallDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup3SmallDecayClass4>0.00016</su_gapCutLogSpGroup3SmallDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup3SmallDecayClass5>0.00017</su_gapCutLogSpGroup3SmallDecayClass5>");
    oOut.write("<su_gapCutLogSpGroup3LargeDecayClass1>0.00018</su_gapCutLogSpGroup3LargeDecayClass1>");
    oOut.write("<su_gapCutLogSpGroup3LargeDecayClass2>0.00019</su_gapCutLogSpGroup3LargeDecayClass2>");
    oOut.write("<su_gapCutLogSpGroup3LargeDecayClass3>0.00021</su_gapCutLogSpGroup3LargeDecayClass3>");
    oOut.write("<su_gapCutLogSpGroup3LargeDecayClass4>0.00022</su_gapCutLogSpGroup3LargeDecayClass4>");
    oOut.write("<su_gapCutLogSpGroup3LargeDecayClass5>0.00023</su_gapCutLogSpGroup3LargeDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup3SmallDecayClass1>0.00024</su_clearCutLogSpGroup3SmallDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup3SmallDecayClass2>0.00025</su_clearCutLogSpGroup3SmallDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup3SmallDecayClass3>0.00026</su_clearCutLogSpGroup3SmallDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup3SmallDecayClass4>0.00027</su_clearCutLogSpGroup3SmallDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup3SmallDecayClass5>0.00028</su_clearCutLogSpGroup3SmallDecayClass5>");
    oOut.write("<su_clearCutLogSpGroup3LargeDecayClass1>0.00029</su_clearCutLogSpGroup3LargeDecayClass1>");
    oOut.write("<su_clearCutLogSpGroup3LargeDecayClass2>0.00031</su_clearCutLogSpGroup3LargeDecayClass2>");
    oOut.write("<su_clearCutLogSpGroup3LargeDecayClass3>0.00032</su_clearCutLogSpGroup3LargeDecayClass3>");
    oOut.write("<su_clearCutLogSpGroup3LargeDecayClass4>0.00033</su_clearCutLogSpGroup3LargeDecayClass4>");
    oOut.write("<su_clearCutLogSpGroup3LargeDecayClass5>0.00034</su_clearCutLogSpGroup3LargeDecayClass5>");
    oOut.write("<su_gapCutScarSoil>0.035</su_gapCutScarSoil>");
    oOut.write("<su_gapCutTipup>0.036</su_gapCutTipup>");
    oOut.write("<su_partialCutScarSoil>0.037</su_partialCutScarSoil>");
    oOut.write("<su_partialCutTipup>0.038</su_partialCutTipup>");
    oOut.write("<su_initialScarSoil>0.039</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.041</su_initialTipup>");
    oOut.write("<su_clearCutScarSoil>0.042</su_clearCutScarSoil>");
    oOut.write("<su_clearCutTipup>0.043</su_clearCutTipup>");
    oOut.write("<su_scarSoilDecayAlpha>-0.044</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>0.045</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-0.046</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>0.047</su_tipupDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>2</su_maxNumberDecayYears>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_1\">0.048</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_2\">0.049</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_3\">0.053</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">0.051</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_2\">0.052</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_3\">0.054</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.4</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>0</su_directionalTreeFall>");
    oOut.write("<su_initialSmallLogMeanDiameter>20</su_initialSmallLogMeanDiameter>");
    oOut.write("<su_initialLargeLogMeanDiameter>50</su_initialLargeLogMeanDiameter>");
    oOut.write("<su_partialCutSmallLogMeanDiameter>21</su_partialCutSmallLogMeanDiameter>");
    oOut.write("<su_partialCutLargeLogMeanDiameter>51</su_partialCutLargeLogMeanDiameter>");
    oOut.write("<su_gapCutSmallLogMeanDiameter>22</su_gapCutSmallLogMeanDiameter>");
    oOut.write("<su_gapCutLargeLogMeanDiameter>52</su_gapCutLargeLogMeanDiameter>");
    oOut.write("<su_clearCutSmallLogMeanDiameter>23</su_clearCutSmallLogMeanDiameter>");
    oOut.write("<su_clearCutLargeLogMeanDiameter>53</su_clearCutLargeLogMeanDiameter>");
    oOut.write("</DetailedSubstrate4>");

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
    oOut.write("<timesteps>10</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>snag decay class dynamics</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>detailedsubstrate</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">1.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("<mo_randomJuvenileMortality>");
    oOut.write("<mo_rjmVal species=\"Species_1\">0.0</mo_rjmVal>");
    oOut.write("</mo_randomJuvenileMortality>");
    oOut.write("</mortality>");              
    oOut.write("<snagDynamics>");
    oOut.write("<sd_snagDecompTreefallAlpha>9999</sd_snagDecompTreefallAlpha>");
    oOut.write("<sd_snagDecompTreefallBeta>");
    oOut.write("<sd_sdtbVal species=\"Species_1\">0.001</sd_sdtbVal>");
    oOut.write("</sd_snagDecompTreefallBeta>");
    oOut.write("<sd_snagDecompTreefallDelta>1</sd_snagDecompTreefallDelta>");
    oOut.write("<sd_snagDecompTreefallTheta>2</sd_snagDecompTreefallTheta>");
    oOut.write("<sd_snagDecompTreefallIota>3</sd_snagDecompTreefallIota>");
    oOut.write("<sd_snagDecompTreefallLambda>4</sd_snagDecompTreefallLambda>");
    oOut.write("<sd_snagDecompSnagfallAlpha>5</sd_snagDecompSnagfallAlpha>");
    oOut.write("<sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_sdsbVal species=\"Species_1\">0.006</sd_sdsbVal>");
    oOut.write("</sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_snagDecompSnagfallGamma2>7</sd_snagDecompSnagfallGamma2>");
    oOut.write("<sd_snagDecompSnagfallGamma3>8</sd_snagDecompSnagfallGamma3>");
    oOut.write("<sd_snagDecompSnagfallGamma4>9</sd_snagDecompSnagfallGamma4>");
    oOut.write("<sd_snagDecompSnagfallGamma5>0</sd_snagDecompSnagfallGamma5>");
    oOut.write("<sd_snagDecompSnagfallZeta>0</sd_snagDecompSnagfallZeta>");
    oOut.write("<sd_snagDecompSnagfallEta>0</sd_snagDecompSnagfallEta>");
    oOut.write("<sd_snagDecompSnagfallKappa>0</sd_snagDecompSnagfallKappa>");
    oOut.write("<sd_snagDecompLiveTo1Prob>1</sd_snagDecompLiveTo1Prob>");
    oOut.write("<sd_snagDecompLiveTo2Prob>0</sd_snagDecompLiveTo2Prob>");
    oOut.write("<sd_snagDecompLiveTo3Prob>0</sd_snagDecompLiveTo3Prob>");
    oOut.write("<sd_snagDecompLiveTo4Prob>0</sd_snagDecompLiveTo4Prob>");
    oOut.write("<sd_snagDecompLiveTo5Prob>0</sd_snagDecompLiveTo5Prob>");
    oOut.write("<sd_snagDecomp1To1Prob>1</sd_snagDecomp1To1Prob>");
    oOut.write("<sd_snagDecomp1To2Prob>0</sd_snagDecomp1To2Prob>");
    oOut.write("<sd_snagDecomp1To3Prob>0</sd_snagDecomp1To3Prob>");
    oOut.write("<sd_snagDecomp1To4Prob>0</sd_snagDecomp1To4Prob>");
    oOut.write("<sd_snagDecomp1To5Prob>0</sd_snagDecomp1To5Prob>");
    oOut.write("<sd_snagDecomp2To2Prob>1</sd_snagDecomp2To2Prob>");
    oOut.write("<sd_snagDecomp2To3Prob>0</sd_snagDecomp2To3Prob>");
    oOut.write("<sd_snagDecomp2To4Prob>0</sd_snagDecomp2To4Prob>");
    oOut.write("<sd_snagDecomp2To5Prob>0</sd_snagDecomp2To5Prob>");
    oOut.write("<sd_snagDecomp3To3Prob>1</sd_snagDecomp3To3Prob>");
    oOut.write("<sd_snagDecomp3To4Prob>0</sd_snagDecomp3To4Prob>");
    oOut.write("<sd_snagDecomp3To5Prob>0</sd_snagDecomp3To5Prob>");
    oOut.write("<sd_snagDecomp4To4Prob>1</sd_snagDecomp4To4Prob>");
    oOut.write("<sd_snagDecomp4To5Prob>0</sd_snagDecomp4To5Prob>");
    oOut.write("<sd_snagDecomp5To5Prob>1</sd_snagDecomp5To5Prob>");
    oOut.write("<sd_minSnagBreakHeight>0.1</sd_minSnagBreakHeight>");
    oOut.write("<sd_maxSnagBreakHeight>0.1</sd_maxSnagBreakHeight>");
    oOut.write("</snagDynamics>");
    oOut.write("<substrate>");
    oOut.write("<su_logSizeClassBoundary>40</su_logSizeClassBoundary>");
    oOut.write("<su_logSpGroupAssignment>");
    oOut.write("<su_lsgaVal species=\"Species_1\">1</su_lsgaVal>");
    oOut.write("</su_logSpGroupAssignment>");
    oOut.write("<su_propFallenTreesLogDecayClass1>0.1</su_propFallenTreesLogDecayClass1>");
    oOut.write("<su_propFallenTreesLogDecayClass2>0.2</su_propFallenTreesLogDecayClass2>");
    oOut.write("<su_propFallenTreesLogDecayClass3>0.3</su_propFallenTreesLogDecayClass3>");
    oOut.write("<su_propFallenTreesLogDecayClass4>0.4</su_propFallenTreesLogDecayClass4>");
    oOut.write("<su_propFallenTreesLogDecayClass5>0.5</su_propFallenTreesLogDecayClass5>");
    oOut.write("<su_propFallenSnagsLogDecayClass1>0.6</su_propFallenSnagsLogDecayClass1>");
    oOut.write("<su_propFallenSnagsLogDecayClass2>0.7</su_propFallenSnagsLogDecayClass2>");
    oOut.write("<su_propFallenSnagsLogDecayClass3>0.8</su_propFallenSnagsLogDecayClass3>");
    oOut.write("<su_propFallenSnagsLogDecayClass4>0.9</su_propFallenSnagsLogDecayClass4>");
    oOut.write("<su_propFallenSnagsLogDecayClass5>1.0</su_propFallenSnagsLogDecayClass5>");
    oOut.write("<su_logSpGroup1SmallDecayClass1DecayAlpha>-1.1</su_logSpGroup1SmallDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass2DecayAlpha>-0.81</su_logSpGroup1SmallDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass3DecayAlpha>-1.21</su_logSpGroup1SmallDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass4DecayAlpha>-0.5</su_logSpGroup1SmallDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass5DecayAlpha>-1.2</su_logSpGroup1SmallDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass1DecayAlpha>-1.22</su_logSpGroup1LargeDecayClass1DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass2DecayAlpha>-0.52</su_logSpGroup1LargeDecayClass2DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass3DecayAlpha>-1.3</su_logSpGroup1LargeDecayClass3DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass4DecayAlpha>-1.4</su_logSpGroup1LargeDecayClass4DecayAlpha>");
    oOut.write("<su_logSpGroup1LargeDecayClass5DecayAlpha>-0.82</su_logSpGroup1LargeDecayClass5DecayAlpha>");
    oOut.write("<su_logSpGroup1SmallDecayClass1DecayBeta>1</su_logSpGroup1SmallDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass2DecayBeta>1.4</su_logSpGroup1SmallDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass3DecayBeta>1.5</su_logSpGroup1SmallDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass4DecayBeta>2.1</su_logSpGroup1SmallDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup1SmallDecayClass5DecayBeta>1.7</su_logSpGroup1SmallDecayClass5DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass1DecayBeta>1.8</su_logSpGroup1LargeDecayClass1DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass2DecayBeta>2.5</su_logSpGroup1LargeDecayClass2DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass3DecayBeta>1.11</su_logSpGroup1LargeDecayClass3DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass4DecayBeta>1.12</su_logSpGroup1LargeDecayClass4DecayBeta>");
    oOut.write("<su_logSpGroup1LargeDecayClass5DecayBeta>1.13</su_logSpGroup1LargeDecayClass5DecayBeta>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass1>0.01</su_initialLogSpGroup1SmallDecayClass1>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass2>0.02</su_initialLogSpGroup1SmallDecayClass2>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass3>0.03</su_initialLogSpGroup1SmallDecayClass3>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass4>0.04</su_initialLogSpGroup1SmallDecayClass4>");
    oOut.write("<su_initialLogSpGroup1SmallDecayClass5>0.08</su_initialLogSpGroup1SmallDecayClass5>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass1>0.71</su_initialLogSpGroup1LargeDecayClass1>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass2>0.72</su_initialLogSpGroup1LargeDecayClass2>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass3>0.73</su_initialLogSpGroup1LargeDecayClass3>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass4>0.74</su_initialLogSpGroup1LargeDecayClass4>");
    oOut.write("<su_initialLogSpGroup1LargeDecayClass5>0.75</su_initialLogSpGroup1LargeDecayClass5>");
    oOut.write("<su_initialScarSoil>0.81</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.82</su_initialTipup>");
    oOut.write("<su_scarSoilDecayAlpha>-0.5</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>1</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-0.55</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>1.5</su_tipupDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>2</su_maxNumberDecayYears>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_1\">0.45</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">0.46</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.4</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>0</su_directionalTreeFall>");
    oOut.write("<su_initialSmallLogMeanDiameter>20</su_initialSmallLogMeanDiameter>");
    oOut.write("<su_initialLargeLogMeanDiameter>50</su_initialLargeLogMeanDiameter>");
    oOut.write("</substrate>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
