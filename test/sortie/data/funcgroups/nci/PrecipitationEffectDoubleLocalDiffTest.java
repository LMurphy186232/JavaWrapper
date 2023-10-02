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

public class PrecipitationEffectDoubleLocalDiffTest extends TestCase {

  /**
   * Tests parameter file reading.
   */
  public void testParFileRead() {
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
      PrecipitationEffectDoubleLocalDiff oPrecipEffect = 
    		  (PrecipitationEffectDoubleLocalDiff) oMort.mp_oEffects.get(0);
      
      assertEquals(0.93, ((Float) oPrecipEffect.mp_fCurrAA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.8393E-05, ((Float) oPrecipEffect.mp_fCurrAA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9979457, ((Float) oPrecipEffect.mp_fCurrABLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9739622, ((Float) oPrecipEffect.mp_fCurrABLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9967062, ((Float) oPrecipEffect.mp_fCurrABHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9278246, ((Float) oPrecipEffect.mp_fCurrABHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.11607, ((Float) oPrecipEffect.mp_fCurrAC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.109959, ((Float) oPrecipEffect.mp_fCurrAC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9057236, ((Float) oPrecipEffect.mp_fCurrBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9948863, ((Float) oPrecipEffect.mp_fCurrBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9136399, ((Float) oPrecipEffect.mp_fCurrBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9475929, ((Float) oPrecipEffect.mp_fCurrBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-1.892206, ((Float) oPrecipEffect.mp_fCurrC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5525589, ((Float) oPrecipEffect.mp_fCurrC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.07, ((Float) oPrecipEffect.mp_fPrevAA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.999981607, ((Float) oPrecipEffect.mp_fPrevAA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9733152, ((Float) oPrecipEffect.mp_fPrevABLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9752083, ((Float) oPrecipEffect.mp_fPrevABLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float) oPrecipEffect.mp_fPrevABHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.90253, ((Float) oPrecipEffect.mp_fPrevABHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-2, ((Float) oPrecipEffect.mp_fPrevAC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-1.247639, ((Float) oPrecipEffect.mp_fPrevAC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9956259, ((Float) oPrecipEffect.mp_fPrevBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oPrecipEffect.mp_fPrevBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.9436914, ((Float) oPrecipEffect.mp_fPrevBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9082155, ((Float) oPrecipEffect.mp_fPrevBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-2, ((Float) oPrecipEffect.mp_fPrevC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float) oPrecipEffect.mp_fPrevC.getValue().get(1)).floatValue(), 0.0001);
                  
      assertEquals(2, oPrecipEffect.m_iPrecipType.getValue());
                                          
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
  private String writeXMLValidFile1() throws IOException {
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
    oOut.write("<nciWhichPrecipitationEffect>4</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>0</nciWhichInfectionEffect>");
    oOut.write("<mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_nmpsVal species=\"Species_1\">1</mo_nmpsVal>");
    oOut.write("<mo_nmpsVal species=\"Species_2\">1</mo_nmpsVal>");
    oOut.write("</mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_nciMortSurvPeriod>1</mo_nciMortSurvPeriod>");
    oOut.write("<nciDoubLocPrecipEffCurrAA>");
    oOut.write("<ndlpecaaVal species=\"Species_1\">0.93</ndlpecaaVal>");
    oOut.write("<ndlpecaaVal species=\"Species_2\">1.8393E-05</ndlpecaaVal>");
    oOut.write("</nciDoubLocPrecipEffCurrAA>");
    oOut.write("<nciDoubLocPrecipEffCurrABLo>");
    oOut.write("<ndlpecablVal species=\"Species_1\">0.9979457</ndlpecablVal>");
    oOut.write("<ndlpecablVal species=\"Species_2\">0.9739622</ndlpecablVal>");
    oOut.write("</nciDoubLocPrecipEffCurrABLo>");
    oOut.write("<nciDoubLocPrecipEffCurrABHi>");
    oOut.write("<ndlpecabhVal species=\"Species_1\">0.9967062</ndlpecabhVal>");
    oOut.write("<ndlpecabhVal species=\"Species_2\">0.9278246</ndlpecabhVal>");
    oOut.write("</nciDoubLocPrecipEffCurrABHi>");
    oOut.write("<nciDoubLocPrecipEffCurrAC>");
    oOut.write("<ndlpecacVal species=\"Species_1\">1.11607</ndlpecacVal>");
    oOut.write("<ndlpecacVal species=\"Species_2\">-0.109959</ndlpecacVal>");
    oOut.write("</nciDoubLocPrecipEffCurrAC>");
    oOut.write("<nciDoubLocPrecipEffCurrBLo>");
    oOut.write("<ndlpecblVal species=\"Species_1\">0.9057236</ndlpecblVal>");
    oOut.write("<ndlpecblVal species=\"Species_2\">0.9948863</ndlpecblVal>");
    oOut.write("</nciDoubLocPrecipEffCurrBLo>");
    oOut.write("<nciDoubLocPrecipEffCurrBHi>");
    oOut.write("<ndlpecbhVal species=\"Species_1\">0.9136399</ndlpecbhVal>");
    oOut.write("<ndlpecbhVal species=\"Species_2\">0.9475929</ndlpecbhVal>");
    oOut.write("</nciDoubLocPrecipEffCurrBHi>");
    oOut.write("<nciDoubLocPrecipEffCurrC>");
    oOut.write("<ndlpeccVal species=\"Species_1\">-1.892206</ndlpeccVal>");
    oOut.write("<ndlpeccVal species=\"Species_2\">0.5525589</ndlpeccVal>");
    oOut.write("</nciDoubLocPrecipEffCurrC>");
    oOut.write("<nciDoubLocPrecipEffPrevAA>");
    oOut.write("<ndlpepaaVal species=\"Species_1\">0.07</ndlpepaaVal>");
    oOut.write("<ndlpepaaVal species=\"Species_2\">0.999981607</ndlpepaaVal>");
    oOut.write("</nciDoubLocPrecipEffPrevAA>");
    oOut.write("<nciDoubLocPrecipEffPrevABLo>");
    oOut.write("<ndlpepablVal species=\"Species_1\">0.9733152</ndlpepablVal>");
    oOut.write("<ndlpepablVal species=\"Species_2\">0.9752083</ndlpepablVal>");
    oOut.write("</nciDoubLocPrecipEffPrevABLo>");
    oOut.write("<nciDoubLocPrecipEffPrevABHi>");
    oOut.write("<ndlpepabhVal species=\"Species_1\">0.9</ndlpepabhVal>");
    oOut.write("<ndlpepabhVal species=\"Species_2\">0.90253</ndlpepabhVal>");
    oOut.write("</nciDoubLocPrecipEffPrevABHi>");
    oOut.write("<nciDoubLocPrecipEffPrevAC>");
    oOut.write("<ndlpepacVal species=\"Species_1\">-2</ndlpepacVal>");
    oOut.write("<ndlpepacVal species=\"Species_2\">-1.247639</ndlpepacVal>");
    oOut.write("</nciDoubLocPrecipEffPrevAC>");
    oOut.write("<nciDoubLocPrecipEffPrevBLo>");
    oOut.write("<ndlpepblVal species=\"Species_1\">0.9956259</ndlpepblVal>");
    oOut.write("<ndlpepblVal species=\"Species_2\">0.9</ndlpepblVal>");
    oOut.write("</nciDoubLocPrecipEffPrevBLo>");
    oOut.write("<nciDoubLocPrecipEffPrevBHi>");
    oOut.write("<ndlpepbhVal species=\"Species_1\">0.9436914</ndlpepbhVal>");
    oOut.write("<ndlpepbhVal species=\"Species_2\">0.9082155</ndlpepbhVal>");
    oOut.write("</nciDoubLocPrecipEffPrevBHi>");
    oOut.write("<nciDoubLocPrecipEffPrevC>");
    oOut.write("<ndlpepcVal species=\"Species_1\">-2</ndlpepcVal>");
    oOut.write("<ndlpepcVal species=\"Species_2\">3</ndlpepcVal>");
    oOut.write("</nciDoubLocPrecipEffPrevC>");
    oOut.write("<nciDoubLocPrecipType>2</nciDoubLocPrecipType>");
    oOut.write("</NCIMasterMortality1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}

