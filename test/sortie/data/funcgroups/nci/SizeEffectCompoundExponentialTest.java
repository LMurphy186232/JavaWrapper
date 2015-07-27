package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class SizeEffectCompoundExponentialTest extends TestCase {
  
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
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof SizeEffectCompoundExponential);
      SizeEffectCompoundExponential oSizeEffect = (SizeEffectCompoundExponential) oGrowth.mp_oEffects.get(0);
      
      assertEquals(0.15, ((Double) oSizeEffect.mp_fA.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(0.16, ((Double) oSizeEffect.mp_fA.getValue().get(1)).doubleValue(), 0.0001);

      assertEquals(-10, ((Double) oSizeEffect.mp_fB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-20, ((Double) oSizeEffect.mp_fB.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-0.1, ((Double) oSizeEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.03, ((Double) oSizeEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(6, ((Double) oSizeEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4, ((Double) oSizeEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
                                          
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
    oOut.write("<nciWhichSizeEffect>5</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciSizeEffectA>");
    oOut.write("<nseaVal species=\"Species_1\">0.15</nseaVal>");
    oOut.write("<nseaVal species=\"Species_2\">0.16</nseaVal>");
    oOut.write("</nciSizeEffectA>");
    oOut.write("<nciSizeEffectB>");
    oOut.write("<nsebVal species=\"Species_1\">-10</nsebVal>");
    oOut.write("<nsebVal species=\"Species_2\">-20</nsebVal>");
    oOut.write("</nciSizeEffectB>");
    oOut.write("<nciSizeEffectC>");
    oOut.write("<nsecVal species=\"Species_1\">-0.1</nsecVal>");
    oOut.write("<nsecVal species=\"Species_2\">-0.03</nsecVal>");
    oOut.write("</nciSizeEffectC>");
    oOut.write("<nciSizeEffectD>");
    oOut.write("<nsedVal species=\"Species_1\">6</nsedVal>");
    oOut.write("<nsedVal species=\"Species_2\">4</nsedVal>");
    oOut.write("</nciSizeEffectD>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
