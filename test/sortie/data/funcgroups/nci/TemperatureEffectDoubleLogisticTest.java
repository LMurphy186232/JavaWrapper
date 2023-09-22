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

public class TemperatureEffectDoubleLogisticTest extends TestCase {

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
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof TemperatureEffectDoubleLogistic);
      TemperatureEffectDoubleLogistic oTempEffect = (TemperatureEffectDoubleLogistic) oGrowth.mp_oEffects.get(1);
      
      assertEquals(0.8, ((Float) oTempEffect.mp_fAl.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.89, ((Float) oTempEffect.mp_fAl.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(6.85, ((Float) oTempEffect.mp_fBl.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(290, ((Float) oTempEffect.mp_fBl.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(3, ((Float) oTempEffect.mp_fCl.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float) oTempEffect.mp_fCl.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.5, ((Float) oTempEffect.mp_fAh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float) oTempEffect.mp_fAh.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(26.85, ((Float) oTempEffect.mp_fBh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(310, ((Float) oTempEffect.mp_fBh.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oTempEffect.mp_fCh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(60, ((Float) oTempEffect.mp_fCh.getValue().get(1)).floatValue(), 0.0001);      
                                          
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
    oOut.write("<nciWhichPrecipitationEffect>2</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>2</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>0</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciDoubLogPrecipEffAl>");
    oOut.write("<ndlpealVal species=\"Species_1\">0.35</ndlpealVal>");
    oOut.write("<ndlpealVal species=\"Species_2\">0.4</ndlpealVal>");
    oOut.write("</nciDoubLogPrecipEffAl>");
    oOut.write("<nciDoubLogPrecipEffBl>");
    oOut.write("<ndlpeblVal species=\"Species_1\">700</ndlpeblVal>");
    oOut.write("<ndlpeblVal species=\"Species_2\">600</ndlpeblVal>");
    oOut.write("</nciDoubLogPrecipEffBl>");
    oOut.write("<nciDoubLogPrecipEffCl>");
    oOut.write("<ndlpeclVal species=\"Species_1\">4</ndlpeclVal>");
    oOut.write("<ndlpeclVal species=\"Species_2\">3</ndlpeclVal>");
    oOut.write("</nciDoubLogPrecipEffCl>");
    oOut.write("<nciDoubLogPrecipEffAh>");
    oOut.write("<ndlpeahVal species=\"Species_1\">0.6</ndlpeahVal>");
    oOut.write("<ndlpeahVal species=\"Species_2\">0.66</ndlpeahVal>");
    oOut.write("</nciDoubLogPrecipEffAh>");
    oOut.write("<nciDoubLogPrecipEffBh>");
    oOut.write("<ndlpebhVal species=\"Species_1\">475</ndlpebhVal>");
    oOut.write("<ndlpebhVal species=\"Species_2\">542</ndlpebhVal>");
    oOut.write("</nciDoubLogPrecipEffBh>");
    oOut.write("<nciDoubLogPrecipEffCh>");
    oOut.write("<ndlpechVal species=\"Species_1\">7</ndlpechVal>");
    oOut.write("<ndlpechVal species=\"Species_2\">0.2</ndlpechVal>");
    oOut.write("</nciDoubLogPrecipEffCh>");
    oOut.write("<nciDoubleLogisticPrecipType>0</nciDoubleLogisticPrecipType>");
    oOut.write("<nciDoubLogTempEffAl>");
    oOut.write("<ndltealVal species=\"Species_1\">0.8</ndltealVal>");
    oOut.write("<ndltealVal species=\"Species_2\">0.89</ndltealVal>");
    oOut.write("</nciDoubLogTempEffAl>");
    oOut.write("<nciDoubLogTempEffBl>");
    oOut.write("<ndlteblVal species=\"Species_1\">6.85</ndlteblVal>");
    oOut.write("<ndlteblVal species=\"Species_2\">290</ndlteblVal>");
    oOut.write("</nciDoubLogTempEffBl>");
    oOut.write("<nciDoubLogTempEffCl>");
    oOut.write("<ndlteclVal species=\"Species_1\">3</ndlteclVal>");
    oOut.write("<ndlteclVal species=\"Species_2\">1</ndlteclVal>");
    oOut.write("</nciDoubLogTempEffCl>");
    oOut.write("<nciDoubLogTempEffAh>");
    oOut.write("<ndlteahVal species=\"Species_1\">0.5</ndlteahVal>");
    oOut.write("<ndlteahVal species=\"Species_2\">0.4</ndlteahVal>");
    oOut.write("</nciDoubLogTempEffAh>");
    oOut.write("<nciDoubLogTempEffBh>");
    oOut.write("<ndltebhVal species=\"Species_1\">26.85</ndltebhVal>");
    oOut.write("<ndltebhVal species=\"Species_2\">310</ndltebhVal>");
    oOut.write("</nciDoubLogTempEffBh>");
    oOut.write("<nciDoubLogTempEffCh>");
    oOut.write("<ndltechVal species=\"Species_1\">5</ndltechVal>");
    oOut.write("<ndltechVal species=\"Species_2\">60</ndltechVal>");
    oOut.write("</nciDoubLogTempEffCh>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}

