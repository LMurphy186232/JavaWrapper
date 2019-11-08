package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.nci.NitrogenEffectGaussian;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class GaussianNitrogenEffectTest extends TestCase {
  
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
      assertEquals(2, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertEquals(5, oGrowth.mp_oEffects.size());
            
      oGrowth = (NCIMasterGrowth) p_oBehs.get(1);
      assertEquals(6, oGrowth.mp_oEffects.size());
      assertTrue(oGrowth.mp_oEffects.get(5) instanceof NitrogenEffectGaussian);
      NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(5);
      
      assertEquals(3.41, ((Double) oNEffect.mp_fX0.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(1.13, ((Double) oNEffect.mp_fX0.getValue().get(1)).doubleValue(), 0.0001);

      assertEquals(1.07, ((Double) oNEffect.mp_fXb.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(3.6, ((Double) oNEffect.mp_fXb.getValue().get(1)).doubleValue(), 0.0001);                                   
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
      //Xb for a species = 0
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(2, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(1);
      assertTrue(oGrowth.mp_oEffects.get(5) instanceof NitrogenEffectGaussian);
      NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(5);
      
      oNEffect.mp_fXb.getValue().set(0, Float.valueOf((float)0));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad N Xb values.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
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
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>3</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>2</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>1</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>1</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_2\">11</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Species_1\">297.615</nsex0Val>");
    oOut.write("<nsex0Val species=\"Species_2\">18.07</nsex0Val>");
    oOut.write("</nciSizeEffectX0>");
    oOut.write("<nciSizeEffectXb>");
    oOut.write("<nsexbVal species=\"Species_1\">4.065</nsexbVal>");
    oOut.write("<nsexbVal species=\"Species_2\">0.902</nsexbVal>");
    oOut.write("</nciSizeEffectXb>");
    oOut.write("<nciSizeEffectLowerBound>");
    oOut.write("<nselbVal species=\"Species_1\">2.54</nselbVal>");
    oOut.write("<nselbVal species=\"Species_2\">7</nselbVal>");
    oOut.write("</nciSizeEffectLowerBound>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">0.2</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">0.5</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">0.9358206</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">1.16629</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">-0.06164447</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">-0.4051102</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciWeibPrecipEffA>");
    oOut.write("<nwpeaVal species=\"Species_1\">1296.228</nwpeaVal>");
    oOut.write("<nwpeaVal species=\"Species_2\">3306.159</nwpeaVal>");
    oOut.write("</nciWeibPrecipEffA>");
    oOut.write("<nciWeibPrecipEffB>");
    oOut.write("<nwpebVal species=\"Species_1\">0.8027915</nwpebVal>");
    oOut.write("<nwpebVal species=\"Species_2\">6.669916</nwpebVal>");
    oOut.write("</nciWeibPrecipEffB>");
    oOut.write("<nciWeibPrecipEffC>");
    oOut.write("<nwpecVal species=\"Species_1\">1032.264</nwpecVal>");
    oOut.write("<nwpecVal species=\"Species_2\">3835.918</nwpecVal>");
    oOut.write("</nciWeibPrecipEffC>");
    oOut.write("<nciWeibTempEffA>");
    oOut.write("<nwteaVal species=\"Species_1\">6.884225</nwteaVal>");
    oOut.write("<nwteaVal species=\"Species_2\">139.5745</nwteaVal>");
    oOut.write("</nciWeibTempEffA>");
    oOut.write("<nciWeibTempEffB>");
    oOut.write("<nwtebVal species=\"Species_1\">0.3014869</nwtebVal>");
    oOut.write("<nwtebVal species=\"Species_2\">0</nwtebVal>");
    oOut.write("</nciWeibTempEffB>");
    oOut.write("<nciWeibTempEffC>");
    oOut.write("<nwtecVal species=\"Species_1\">14.2357</nwtecVal>");
    oOut.write("<nwtecVal species=\"Species_2\">28.6192</nwtecVal>");
    oOut.write("</nciWeibTempEffC>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">6</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("<NCIMasterGrowth2>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>3</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>2</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>1</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>1</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>1</nciWhichNitrogenEffect>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">12</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_2\">13</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">1.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">20</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Species_1\">397.615</nsex0Val>");
    oOut.write("<nsex0Val species=\"Species_2\">28.07</nsex0Val>");
    oOut.write("</nciSizeEffectX0>");
    oOut.write("<nciSizeEffectXb>");
    oOut.write("<nsexbVal species=\"Species_1\">5.065</nsexbVal>");
    oOut.write("<nsexbVal species=\"Species_2\">1.902</nsexbVal>");
    oOut.write("</nciSizeEffectXb>");
    oOut.write("<nciSizeEffectLowerBound>");
    oOut.write("<nselbVal species=\"Species_1\">3.54</nselbVal>");
    oOut.write("<nselbVal species=\"Species_2\">8</nselbVal>");
    oOut.write("</nciSizeEffectLowerBound>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">8.261</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">0</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">1.001</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">2.914</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">-1.208</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">1.976</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciWeibTempEffA>");
    oOut.write("<nwteaVal species=\"Species_1\">8.897617</nwteaVal>");
    oOut.write("<nwteaVal species=\"Species_2\">8.91133</nwteaVal>");
    oOut.write("</nciWeibTempEffA>");
    oOut.write("<nciWeibTempEffB>");
    oOut.write("<nwtebVal species=\"Species_1\">1.205544</nwtebVal>");
    oOut.write("<nwtebVal species=\"Species_2\">1.475192</nwtebVal>");
    oOut.write("</nciWeibTempEffB>");
    oOut.write("<nciWeibTempEffC>");
    oOut.write("<nwtecVal species=\"Species_1\">13.2644</nwtecVal>");
    oOut.write("<nwtecVal species=\"Species_2\">12.4024</nwtecVal>");
    oOut.write("</nciWeibTempEffC>");
    oOut.write("<nciWeibPrecipEffA>");
    oOut.write("<nwpeaVal species=\"Species_1\">420.2115</nwpeaVal>");
    oOut.write("<nwpeaVal species=\"Species_2\">3.753555</nwpeaVal>");
    oOut.write("</nciWeibPrecipEffA>");
    oOut.write("<nciWeibPrecipEffB>");
    oOut.write("<nwpebVal species=\"Species_1\">0.4447052</nwpebVal>");
    oOut.write("<nwpebVal species=\"Species_2\">0.07809054</nwpebVal>");
    oOut.write("</nciWeibPrecipEffB>");
    oOut.write("<nciWeibPrecipEffC>");
    oOut.write("<nwpecVal species=\"Species_1\">0.000319117</nwpecVal>");
    oOut.write("<nwpecVal species=\"Species_2\">1048.734</nwpecVal>");
    oOut.write("</nciWeibPrecipEffC>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">7</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">8</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciNitrogenX0>");
    oOut.write("<nnx0Val species=\"Species_1\">3.41</nnx0Val>");
    oOut.write("<nnx0Val species=\"Species_2\">1.13</nnx0Val>");
    oOut.write("</nciNitrogenX0>");
    oOut.write("<nciNitrogenXb>");
    oOut.write("<nnxbVal species=\"Species_1\">1.07</nnxbVal>");
    oOut.write("<nnxbVal species=\"Species_2\">3.6</nnxbVal>");
    oOut.write("</nciNitrogenXb>");
    
    oOut.write("</NCIMasterGrowth2>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
