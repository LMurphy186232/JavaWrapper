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

public class NCINeighborBATest extends TestCase {

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
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCINeighborBA);
      NCINeighborBA oNCITerm = (NCINeighborBA) oGrowth.mp_oEffects.get(1);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, oNCITerm.m_iNCIOnlyLargerNeighbors.getValue());
      
      assertEquals(1000.0, oNCITerm.m_fBADivisor.getValue(), 0.00001);
                              
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
      //Max radius of neighbor effects is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCINeighborBA);
      NCINeighborBA oNCITerm = (NCINeighborBA) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().set(0, new Float(-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI max radius values.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
    try {
      //Min neighbor DBH is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCINeighborBA);
      NCINeighborBA oNCITerm = (NCINeighborBA) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCIMinNeighborDBH.getValue().set(0, new Float(-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad min neighbor DBH values.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}

    try {
      //Applied to seedlings and use larger neighbors is true.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCINeighborBA);
      NCINeighborBA oNCITerm = (NCINeighborBA) oMaster.mp_oEffects.get(1);
      oNCITerm.m_iNCIOnlyLargerNeighbors.setValue(1);
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch applied to seedlings.");
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>2</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>4</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>1</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_2\">11</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">-1.208</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">1.976</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Species_1\">297.615</nsex0Val>");
    oOut.write("<nsex0Val species=\"Species_2\">18.07</nsex0Val>");
    oOut.write("</nciSizeEffectX0>");
    oOut.write("<nciSizeEffectXb>");
    oOut.write("<nsexbVal species=\"Species_1\">4.065</nsexbVal>");
    oOut.write("<nsexbVal species=\"Species_2\">0.902</nsexbVal>");
    oOut.write("</nciSizeEffectXb>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">8.261</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">0</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">1.001</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">2.914</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">6</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciBAOnlyLargerNeighbors>0</nciBAOnlyLargerNeighbors>");
    oOut.write("<nciBADivisor>1000</nciBADivisor>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");    

    oOut.close();
    return sFileName;
  }
}
