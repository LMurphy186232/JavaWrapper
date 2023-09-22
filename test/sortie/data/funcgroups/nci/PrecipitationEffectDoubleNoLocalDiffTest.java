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

public class PrecipitationEffectDoubleNoLocalDiffTest extends TestCase {

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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      PrecipitationEffectDoubleNoLocalDiff oPrecipEffect = 
    		  (PrecipitationEffectDoubleNoLocalDiff) oGrowth.mp_oEffects.get(0);
      
      assertEquals(0.01, ((Float) oPrecipEffect.mp_fCurrA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1,    ((Float) oPrecipEffect.mp_fCurrA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.93, ((Float) oPrecipEffect.mp_fCurrBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.90, ((Float) oPrecipEffect.mp_fCurrBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.91, ((Float) oPrecipEffect.mp_fCurrBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.96, ((Float) oPrecipEffect.mp_fCurrBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.87, ((Float) oPrecipEffect.mp_fCurrC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.30, ((Float) oPrecipEffect.mp_fCurrC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.86, ((Float) oPrecipEffect.mp_fPrevA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oPrecipEffect.mp_fPrevA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.98, ((Float) oPrecipEffect.mp_fPrevBLo.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.97, ((Float) oPrecipEffect.mp_fPrevBLo.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.95, ((Float) oPrecipEffect.mp_fPrevBHi.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.92, ((Float) oPrecipEffect.mp_fPrevBHi.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.45, ((Float) oPrecipEffect.mp_fPrevC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.84, ((Float) oPrecipEffect.mp_fPrevC.getValue().get(1)).floatValue(), 0.0001);
                  
      assertEquals(1, oPrecipEffect.m_iPrecipType.getValue());
                                          
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
    oOut.write("<nciWhichPrecipitationEffect>3</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>0</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">1</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">1</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciDoubNoLocPrecipEffCurrA>");
    oOut.write("<ndnlpecaVal species=\"Species_1\">0.01</ndnlpecaVal>");
    oOut.write("<ndnlpecaVal species=\"Species_2\">1</ndnlpecaVal>");
    oOut.write("</nciDoubNoLocPrecipEffCurrA>");
    oOut.write("<nciDoubNoLocPrecipEffCurrBLo>");
    oOut.write("<ndnlpecblVal species=\"Species_1\">0.93</ndnlpecblVal>");
    oOut.write("<ndnlpecblVal species=\"Species_2\">0.90</ndnlpecblVal>");
    oOut.write("</nciDoubNoLocPrecipEffCurrBLo>");
    oOut.write("<nciDoubNoLocPrecipEffCurrBHi>");
    oOut.write("<ndnlpecbhVal species=\"Species_1\">0.91</ndnlpecbhVal>");
    oOut.write("<ndnlpecbhVal species=\"Species_2\">0.96</ndnlpecbhVal>");
    oOut.write("</nciDoubNoLocPrecipEffCurrBHi>");
    oOut.write("<nciDoubNoLocPrecipEffCurrC>");
    oOut.write("<ndnlpeccVal species=\"Species_1\">0.87</ndnlpeccVal>");
    oOut.write("<ndnlpeccVal species=\"Species_2\">0.3</ndnlpeccVal>");
    oOut.write("</nciDoubNoLocPrecipEffCurrC>");
    oOut.write("<nciDoubNoLocPrecipEffPrevA>");
    oOut.write("<ndnlpepaVal species=\"Species_1\">0.86</ndnlpepaVal>");
    oOut.write("<ndnlpepaVal species=\"Species_2\">0.34</ndnlpepaVal>");
    oOut.write("</nciDoubNoLocPrecipEffPrevA>");
    oOut.write("<nciDoubNoLocPrecipEffPrevBLo>");
    oOut.write("<ndnlpepblVal species=\"Species_1\">0.98</ndnlpepblVal>");
    oOut.write("<ndnlpepblVal species=\"Species_2\">0.97</ndnlpepblVal>");
    oOut.write("</nciDoubNoLocPrecipEffPrevBLo>");
    oOut.write("<nciDoubNoLocPrecipEffPrevBHi>");
    oOut.write("<ndnlpepbhVal species=\"Species_1\">0.95</ndnlpepbhVal>");
    oOut.write("<ndnlpepbhVal species=\"Species_2\">0.92</ndnlpepbhVal>");
    oOut.write("</nciDoubNoLocPrecipEffPrevBHi>");
    oOut.write("<nciDoubNoLocPrecipEffPrevC>");
    oOut.write("<ndnlpepcVal species=\"Species_1\">0.45</ndnlpepcVal>");
    oOut.write("<ndnlpepcVal species=\"Species_2\">0.84</ndnlpepcVal>");
    oOut.write("</nciDoubNoLocPrecipEffPrevC>");
    oOut.write("<nciDoubNoLocPrecipType>1</nciDoubNoLocPrecipType>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}

