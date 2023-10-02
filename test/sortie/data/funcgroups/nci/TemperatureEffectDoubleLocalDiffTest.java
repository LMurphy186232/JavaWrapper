package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class TemperatureEffectDoubleLocalDiffTest extends TestCase {

  /**
   * Tests parameter file reading.
   */
  public void testParFileRead() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(1, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
      assertTrue(oMort.mp_oEffects.get(0) instanceof TemperatureEffectDoubleLocalDiff);
      TemperatureEffectDoubleLocalDiff oTempEffect = (TemperatureEffectDoubleLocalDiff) oMort.mp_oEffects.get(0);
      
      assertEquals(0.350302, ((Float) oTempEffect.mp_fCurrAA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.033936, ((Float) oTempEffect.mp_fCurrAA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.9694824, ((Float) oTempEffect.mp_fCurrABLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9851785, ((Float) oTempEffect.mp_fCurrABLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9928637, ((Float) oTempEffect.mp_fCurrABHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9223768, ((Float) oTempEffect.mp_fCurrABHi.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(4.112366, ((Float) oTempEffect.mp_fCurrAC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.127323, ((Float) oTempEffect.mp_fCurrAC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9004149, ((Float) oTempEffect.mp_fCurrBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.986613, ((Float) oTempEffect.mp_fCurrBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9840264, ((Float) oTempEffect.mp_fCurrBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9657315, ((Float) oTempEffect.mp_fCurrBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-0.7583497, ((Float) oTempEffect.mp_fCurrC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(9.963309, ((Float) oTempEffect.mp_fCurrC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.649698, ((Float) oTempEffect.mp_fPrevAA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.96606389, ((Float) oTempEffect.mp_fPrevAA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.9013303, ((Float) oTempEffect.mp_fPrevABLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9000145, ((Float) oTempEffect.mp_fPrevABLo.getValue().get(1)).floatValue(), 0.0001);
      
      
      assertEquals(0.9096494, ((Float) oTempEffect.mp_fPrevABHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9994287, ((Float) oTempEffect.mp_fPrevABHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.355715, ((Float) oTempEffect.mp_fPrevAC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.930429, ((Float) oTempEffect.mp_fPrevAC.getValue().get(1)).floatValue(), 0.0001);
      
      
      assertEquals(0.9001837, ((Float) oTempEffect.mp_fPrevBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9745306, ((Float) oTempEffect.mp_fPrevBLo.getValue().get(1)).floatValue(), 0.0001);
      
      
      assertEquals(0.9914288, ((Float) oTempEffect.mp_fPrevBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9304641, ((Float) oTempEffect.mp_fPrevBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-22.6664, ((Float) oTempEffect.mp_fPrevC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.490487, ((Float) oTempEffect.mp_fPrevC.getValue().get(1)).floatValue(), 0.0001);
                                                
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
    oOut.write("<behaviorName>NCIMasterMortality</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterMortality1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>0</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>0</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>0</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>4</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>0</nciWhichInfectionEffect>");
    oOut.write("<mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_nmpsVal species=\"Species_1\">1</mo_nmpsVal>");
    oOut.write("<mo_nmpsVal species=\"Species_2\">1</mo_nmpsVal>");
    oOut.write("</mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_nciMortSurvPeriod>1</mo_nciMortSurvPeriod>");
    oOut.write("<nciDoubLocTempEffCurrAA>");
    oOut.write("<ndltecaaVal species=\"Species_1\">0.350302</ndltecaaVal>");
    oOut.write("<ndltecaaVal species=\"Species_2\">0.03393611</ndltecaaVal>");
    oOut.write("</nciDoubLocTempEffCurrAA>");
    oOut.write("<nciDoubLocTempEffCurrABLo>");
    oOut.write("<ndltecablVal species=\"Species_1\">0.9694824</ndltecablVal>");
    oOut.write("<ndltecablVal species=\"Species_2\">0.9851785</ndltecablVal>");
    oOut.write("</nciDoubLocTempEffCurrABLo>");
    oOut.write("<nciDoubLocTempEffCurrABHi>");
    oOut.write("<ndltecabhVal species=\"Species_1\">0.9928637</ndltecabhVal>");
    oOut.write("<ndltecabhVal species=\"Species_2\">0.9223768</ndltecabhVal>");
    oOut.write("</nciDoubLocTempEffCurrABHi>");
    oOut.write("<nciDoubLocTempEffCurrAC>");
    oOut.write("<ndltecacVal species=\"Species_1\">4.112366</ndltecacVal>");
    oOut.write("<ndltecacVal species=\"Species_2\">4.127323</ndltecacVal>");
    oOut.write("</nciDoubLocTempEffCurrAC>");
    oOut.write("<nciDoubLocTempEffCurrBLo>");
    oOut.write("<ndltecblVal species=\"Species_1\">0.9004149</ndltecblVal>");
    oOut.write("<ndltecblVal species=\"Species_2\">0.986613</ndltecblVal>");
    oOut.write("</nciDoubLocTempEffCurrBLo>");
    oOut.write("<nciDoubLocTempEffCurrBHi>");
    oOut.write("<ndltecbhVal species=\"Species_1\">0.9840264</ndltecbhVal>");
    oOut.write("<ndltecbhVal species=\"Species_2\">0.9657315</ndltecbhVal>");
    oOut.write("</nciDoubLocTempEffCurrBHi>");
    oOut.write("<nciDoubLocTempEffCurrC>");
    oOut.write("<ndlteccVal species=\"Species_1\">-0.7583497</ndlteccVal>");
    oOut.write("<ndlteccVal species=\"Species_2\">9.963309</ndlteccVal>");
    oOut.write("</nciDoubLocTempEffCurrC>");
    oOut.write("<nciDoubLocTempEffPrevAA>");
    oOut.write("<ndltepaaVal species=\"Species_1\">0.649698</ndltepaaVal>");
    oOut.write("<ndltepaaVal species=\"Species_2\">0.96606389</ndltepaaVal>");
    oOut.write("</nciDoubLocTempEffPrevAA>");
    oOut.write("<nciDoubLocTempEffPrevABLo>");
    oOut.write("<ndltepablVal species=\"Species_1\">0.9013303</ndltepablVal>");
    oOut.write("<ndltepablVal species=\"Species_2\">0.9000145</ndltepablVal>");
    oOut.write("</nciDoubLocTempEffPrevABLo>");
    oOut.write("<nciDoubLocTempEffPrevABHi>");
    oOut.write("<ndltepabhVal species=\"Species_1\">0.9096494</ndltepabhVal>");
    oOut.write("<ndltepabhVal species=\"Species_2\">0.9994287</ndltepabhVal>");
    oOut.write("</nciDoubLocTempEffPrevABHi>");
    oOut.write("<nciDoubLocTempEffPrevAC>");
    oOut.write("<ndltepacVal species=\"Species_1\">1.355715</ndltepacVal>");
    oOut.write("<ndltepacVal species=\"Species_2\">2.930429</ndltepacVal>");
    oOut.write("</nciDoubLocTempEffPrevAC>");
    oOut.write("<nciDoubLocTempEffPrevBLo>");
    oOut.write("<ndltepblVal species=\"Species_1\">0.9001837</ndltepblVal>");
    oOut.write("<ndltepblVal species=\"Species_2\">0.9745306</ndltepblVal>");
    oOut.write("</nciDoubLocTempEffPrevBLo>");
    oOut.write("<nciDoubLocTempEffPrevBHi>");
    oOut.write("<ndltepbhVal species=\"Species_1\">0.9914288</ndltepbhVal>");
    oOut.write("<ndltepbhVal species=\"Species_2\">0.9304641</ndltepbhVal>");
    oOut.write("</nciDoubLocTempEffPrevBHi>");
    oOut.write("<nciDoubLocTempEffPrevC>");
    oOut.write("<ndltepcVal species=\"Species_1\">-22.6664</ndltepcVal>");
    oOut.write("<ndltepcVal species=\"Species_2\">1.490487</ndltepcVal>");
    oOut.write("</nciDoubLocTempEffPrevC>");
    oOut.write("</NCIMasterMortality1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}

