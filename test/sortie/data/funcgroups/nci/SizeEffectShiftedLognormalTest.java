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

public class SizeEffectShiftedLognormalTest extends TestCase {
  
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
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof SizeEffectShiftedLognormal);
      SizeEffectShiftedLognormal oSizeEffect = (SizeEffectShiftedLognormal) oGrowth.mp_oEffects.get(0);
            
      assertEquals(297.615, ((Double) oSizeEffect.mp_fSizeEffectX0.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(18.07, ((Double) oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);

      assertEquals(4.065, ((Float) oSizeEffect.mp_fSizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.902, ((Float) oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float) oSizeEffect.mp_fSizeEffectXp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float) oSizeEffect.mp_fSizeEffectXp.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, ((Float) oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float) oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);
      
                                    
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
      //X0 (size effect mode) for any species = 0
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof SizeEffectShiftedLognormal);
      SizeEffectShiftedLognormal oSizeEffect = (SizeEffectShiftedLognormal) oGrowth.mp_oEffects.get(0);
      oSizeEffect.mp_fSizeEffectX0.getValue().set(0, Float.valueOf((float)0));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad X0 values.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
    try {
      //Xb (size effect mode) for any species = 0
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof SizeEffectShiftedLognormal);
      SizeEffectShiftedLognormal oSizeEffect = (SizeEffectShiftedLognormal) oGrowth.mp_oEffects.get(0);
      oSizeEffect.mp_fSizeEffectXb.getValue().set(0, Float.valueOf((float)0));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad Xb values.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}

    System.out.println("Growth ValidateData testing succeeded.");
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
    oOut.write("<nciWhichSizeEffect>4</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
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
    oOut.write("<nciSizeEffectXp>");
    oOut.write("<nsexpVal species=\"Species_1\">0.1</nsexpVal>");
    oOut.write("<nsexpVal species=\"Species_2\">0.2</nsexpVal>");
    oOut.write("</nciSizeEffectXp>");
    oOut.write("<nciSizeEffectLowerBound>");
    oOut.write("<nselbVal species=\"Species_1\">0</nselbVal>");
    oOut.write("<nselbVal species=\"Species_2\">5</nselbVal>");
    oOut.write("</nciSizeEffectLowerBound>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
