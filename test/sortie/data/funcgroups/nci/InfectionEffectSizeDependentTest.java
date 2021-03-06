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

public class InfectionEffectSizeDependentTest extends TestCase {
  
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
      assertEquals(1, oGrowth.mp_oEffects.size());
            
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof InfectionEffectSizeDependent);
      InfectionEffectSizeDependent oInfEffect = (InfectionEffectSizeDependent) oGrowth.mp_oEffects.get(0);
      
      assertEquals(-0.1, ((Double) oInfEffect.mp_fA.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(-0.2, ((Double) oInfEffect.mp_fA.getValue().get(1)).doubleValue(), 0.0001);

      assertEquals(0.4, ((Double) oInfEffect.mp_fB.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(0.7, ((Double) oInfEffect.mp_fB.getValue().get(1)).doubleValue(), 0.0001);
      
      assertEquals(10, ((Double) oInfEffect.mp_fX0.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(26, ((Double) oInfEffect.mp_fX0.getValue().get(1)).doubleValue(), 0.0001);
      
      assertEquals(1, ((Double) oInfEffect.mp_fXb.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(5, ((Double) oInfEffect.mp_fXb.getValue().get(1)).doubleValue(), 0.0001);
      
      assertEquals(2, ((Double) oInfEffect.mp_fXp.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(4, ((Double) oInfEffect.mp_fXp.getValue().get(1)).doubleValue(), 0.0001);      
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
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof InfectionEffectSizeDependent);
      InfectionEffectSizeDependent oInfEffect = (InfectionEffectSizeDependent) oGrowth.mp_oEffects.get(0);
      oInfEffect.mp_fX0.getValue().set(0, Float.valueOf((float)0));
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
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof InfectionEffectSizeDependent);
      InfectionEffectSizeDependent oInfEffect = (InfectionEffectSizeDependent) oGrowth.mp_oEffects.get(0);
      oInfEffect.mp_fXb.getValue().set(0, Float.valueOf((float)0));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad Xb values.");
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
    oOut.write("<behaviorName>DensDepInfestation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<DensDepInfestation1>");
    oOut.write("<di_densDepInfMaxInfestation>1</di_densDepInfMaxInfestation>");
    oOut.write("<di_densDepInfA>0.2</di_densDepInfA>");
    oOut.write("<di_densDepInfBx>0.3</di_densDepInfBx>");
    oOut.write("<di_densDepInfBy>0.2</di_densDepInfBy>");
    oOut.write("<di_densDepInfMinDBH>");
    oOut.write("<di_ddimdVal species=\"Species_1\">0</di_ddimdVal>");
    oOut.write("<di_ddimdVal species=\"Species_2\">0</di_ddimdVal>");
    oOut.write("</di_densDepInfMinDBH>");
    oOut.write("<di_densDepInfCohortDBH>");
    oOut.write("<di_ddicdVal species=\"Species_1\">0</di_ddicdVal>");
    oOut.write("<di_ddicdVal species=\"Species_2\">0</di_ddicdVal>");
    oOut.write("</di_densDepInfCohortDBH>");
    oOut.write("<di_densDepInfPropResistant>");
    oOut.write("<di_ddiprVal species=\"Species_1\">0</di_ddiprVal>");
    oOut.write("<di_ddiprVal species=\"Species_2\">0</di_ddiprVal>");
    oOut.write("</di_densDepInfPropResistant>");
    oOut.write("<di_densDepInfPropCondSusceptible>");
    oOut.write("<di_ddipcsVal species=\"Species_1\">0</di_ddipcsVal>");
    oOut.write("<di_ddipcsVal species=\"Species_2\">0</di_ddipcsVal>");
    oOut.write("</di_densDepInfPropCondSusceptible>");
    oOut.write("<di_densDepInfStartYear>-5</di_densDepInfStartYear>");
    oOut.write("</DensDepInfestation1>");

    oOut.write("<NCIMasterGrowth2>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>0</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>0</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>0</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>2</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">12</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciInfectionEffectA>");
    oOut.write("<nieaVal species=\"Species_1\">-0.1</nieaVal>");
    oOut.write("<nieaVal species=\"Species_2\">-0.2</nieaVal>");
    oOut.write("</nciInfectionEffectA>");
    oOut.write("<nciInfectionEffectB>");
    oOut.write("<niebVal species=\"Species_1\">0.4</niebVal>");
    oOut.write("<niebVal species=\"Species_2\">0.7</niebVal>");
    oOut.write("</nciInfectionEffectB>");
    oOut.write("<nciInfectionEffectX0>");
    oOut.write("<niex0Val species=\"Species_1\">10</niex0Val>");
    oOut.write("<niex0Val species=\"Species_2\">26</niex0Val>");
    oOut.write("</nciInfectionEffectX0>");
    oOut.write("<nciInfectionEffectXb>");
    oOut.write("<niexbVal species=\"Species_1\">1</niexbVal>");
    oOut.write("<niexbVal species=\"Species_2\">5</niexbVal>");
    oOut.write("</nciInfectionEffectXb>");
    oOut.write("<nciInfectionEffectXp>");
    oOut.write("<niexpVal species=\"Species_1\">2</niexpVal>");
    oOut.write("<niexpVal species=\"Species_2\">4</niexpVal>");
    oOut.write("</nciInfectionEffectXp>");
    oOut.write("</NCIMasterGrowth2>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
