package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class TemperatureEffectDoubleNoLocalDiffTest extends TestCase {

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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof TemperatureEffectDoubleNoLocalDiff);
      TemperatureEffectDoubleNoLocalDiff oTempEffect = (TemperatureEffectDoubleNoLocalDiff) oGrowth.mp_oEffects.get(0);
      
      assertEquals(0.77, ((Float) oTempEffect.mp_fCurrA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.008,((Float) oTempEffect.mp_fCurrA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.97, ((Float) oTempEffect.mp_fCurrBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.90, ((Float) oTempEffect.mp_fCurrBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.93, ((Float) oTempEffect.mp_fCurrBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.0,  ((Float) oTempEffect.mp_fCurrBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(17.24, ((Float) oTempEffect.mp_fCurrC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.21, ((Float) oTempEffect.mp_fCurrC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.85, ((Float) oTempEffect.mp_fPrevA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.72, ((Float) oTempEffect.mp_fPrevA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.1,  ((Float) oTempEffect.mp_fPrevBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.91, ((Float) oTempEffect.mp_fPrevBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.96, ((Float) oTempEffect.mp_fPrevBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.99, ((Float) oTempEffect.mp_fPrevBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(5.38,   ((Float) oTempEffect.mp_fPrevC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-19.21, ((Float) oTempEffect.mp_fPrevC.getValue().get(1)).floatValue(), 0.0001);
                                                
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
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
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
    oOut.write("<nciWhichTemperatureEffect>3</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>0</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciDoubNoLocTempEffCurrA>");
    oOut.write("<ndnltecaVal species=\"Species_1\">0.77</ndnltecaVal>");
    oOut.write("<ndnltecaVal species=\"Species_2\">0.008</ndnltecaVal>");
    oOut.write("</nciDoubNoLocTempEffCurrA>");
    oOut.write("<nciDoubNoLocTempEffCurrBLo>");
    oOut.write("<ndnltecblVal species=\"Species_1\">0.97</ndnltecblVal>");
    oOut.write("<ndnltecblVal species=\"Species_2\">0.9</ndnltecblVal>");
    oOut.write("</nciDoubNoLocTempEffCurrBLo>");
    oOut.write("<nciDoubNoLocTempEffCurrBHi>");
    oOut.write("<ndnltecbhVal species=\"Species_1\">0.93</ndnltecbhVal>");
    oOut.write("<ndnltecbhVal species=\"Species_2\">1</ndnltecbhVal>");
    oOut.write("</nciDoubNoLocTempEffCurrBHi>");
    oOut.write("<nciDoubNoLocTempEffCurrC>");
    oOut.write("<ndnlteccVal species=\"Species_1\">17.24</ndnlteccVal>");
    oOut.write("<ndnlteccVal species=\"Species_2\">11.21</ndnlteccVal>");
    oOut.write("</nciDoubNoLocTempEffCurrC>");
    oOut.write("<nciDoubNoLocTempEffPrevA>");
    oOut.write("<ndnltepaVal species=\"Species_1\">0.85</ndnltepaVal>");
    oOut.write("<ndnltepaVal species=\"Species_2\">0.72</ndnltepaVal>");
    oOut.write("</nciDoubNoLocTempEffPrevA>");
    oOut.write("<nciDoubNoLocTempEffPrevBLo>");
    oOut.write("<ndnltepblVal species=\"Species_1\">1.1</ndnltepblVal>");
    oOut.write("<ndnltepblVal species=\"Species_2\">0.91</ndnltepblVal>");
    oOut.write("</nciDoubNoLocTempEffPrevBLo>");
    oOut.write("<nciDoubNoLocTempEffPrevBHi>");
    oOut.write("<ndnltepbhVal species=\"Species_1\">0.96</ndnltepbhVal>");
    oOut.write("<ndnltepbhVal species=\"Species_2\">0.99</ndnltepbhVal>");
    oOut.write("</nciDoubNoLocTempEffPrevBHi>");
    oOut.write("<nciDoubNoLocTempEffPrevC>");
    oOut.write("<ndnltepcVal species=\"Species_1\">5.38</ndnltepcVal>");
    oOut.write("<ndnltepcVal species=\"Species_2\">-19.21</ndnltepcVal>");
    oOut.write("</nciDoubNoLocTempEffPrevC>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}

