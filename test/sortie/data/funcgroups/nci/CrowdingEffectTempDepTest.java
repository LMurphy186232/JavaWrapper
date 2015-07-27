package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class CrowdingEffectTempDepTest extends TestCase {
  
  /**
   * Makes sure that values are managed correctly when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      int i;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(0) instanceof CrowdingEffectTempDep);
      CrowdingEffectTempDep oCrowdingEffect = (CrowdingEffectTempDep) oMaster.mp_oEffects.get(0);           

      //Verify how the data was read in before species copy
      assertEquals(4, oMaster.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oMaster.getNumberOfCombos(); i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      
      assertEquals(4, oCrowdingEffect.getNumberOfCombos());
      for (i = 0; i < oCrowdingEffect.getNumberOfCombos(); i++) {
        oCombo = oCrowdingEffect.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      
      assertEquals(1.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(300.89, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(270.00, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(284.98, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(310, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(203.04, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(163.77, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.007, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(56.76, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);

      
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertEquals(5, oMaster.getNumberOfCombos());
      for (i = 0; i < oMaster.getNumberOfCombos() - 1; i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oMaster.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());
      
      oCrowdingEffect = (CrowdingEffectTempDep) oMaster.mp_oEffects.get(0);
      assertEquals(5, oCrowdingEffect.getNumberOfCombos());
      for (i = 0; i < oCrowdingEffect.getNumberOfCombos() - 1; i++) {
        oCombo = oCrowdingEffect.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oCrowdingEffect.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());
      
      assertEquals(1.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(4.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(7)).floatValue(), 0.0001);
      
      assertEquals(300.89, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(270.00, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(284.98, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(310, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(270.00, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(7)).floatValue(), 0.0001);
      
      assertEquals(203.04, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(163.77, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.007, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(56.76, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(163.77, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(7)).floatValue(), 0.0001);
      
      assertEquals(1.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(7)).floatValue(), 0.0001);
      
      assertEquals(0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(7)).floatValue(), 0.0001);
                              
      System.out.println("Test copy species was successful.");
    }
    catch (ModelException oErr) {
      fail("Test copy species failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that values are managed correctly when species change.
   */
  public void testChangeOfSpecies() {
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
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(0) instanceof CrowdingEffectTempDep);
      CrowdingEffectTempDep oCrowdingEffect = (CrowdingEffectTempDep) oMaster.mp_oEffects.get(0);
      
      assertEquals(0.953, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.37, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(283, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(276, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(3.2, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.62, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.81, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-0.15, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.13, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      
                  
      //Change the species
     String[] sNewSpecies = new String[] {
            "Species 2",
            "Species 3",
            "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(0) instanceof CrowdingEffectTempDep);
      oCrowdingEffect = (CrowdingEffectTempDep) oMaster.mp_oEffects.get(0);
      
      assertEquals(0.37, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(276, ((Float) oCrowdingEffect.mp_fCX0.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(2, ((Float) oCrowdingEffect.mp_fCXb.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.81, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.13, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
                  
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
   * Writes a valid NCI growth file. All effects.
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    
    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>" + NCIEffect.shading_effect.no_shading.ordinal() + "</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>" + NCIEffect.crowding_effect.crowding_effect_temp_dep.ordinal() + "</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>" + NCIEffect.nci_term.no_nci_term.ordinal() + "</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>" + NCIEffect.size_effect.no_size_effect.ordinal() + "</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>" + NCIEffect.damage_effect.no_damage_effect.ordinal() + "</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>" + NCIEffect.precipitation_effect.no_precip_effect.ordinal() + "</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>" + NCIEffect.temperature_effect.no_temp_effect.ordinal() + "</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>" + NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal() + "</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>" + NCIEffect.infection_effect.no_infection_effect.ordinal() + "</nciWhichInfectionEffect>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">10.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15.31</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">0.953</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">0.37</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingCX0>");
    oOut.write("<nccx0Val species=\"Species_1\">283</nccx0Val>");
    oOut.write("<nccx0Val species=\"Species_2\">276</nccx0Val>");
    oOut.write("</nciCrowdingCX0>");
    oOut.write("<nciCrowdingCXb>");
    oOut.write("<nccxbVal species=\"Species_1\">3.2</nccxbVal>");
    oOut.write("<nccxbVal species=\"Species_2\">2</nccxbVal>");
    oOut.write("</nciCrowdingCXb>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">0.62</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">0.81</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">-0.15</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">0.13</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("</NCIMasterGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid NCI growth file
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileForSpeciesCopy() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\" />");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\" />");
    oOut.write("<tr_species speciesName=\"Hybrid_Spruce\" />");
    oOut.write("<tr_species speciesName=\"Lodgepole_Pine\" />");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\" />");
    oOut.write("<tr_species speciesName=\"Black_Cottonwood\" />");
    oOut.write("<tr_species speciesName=\"Paper_Birch\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>" + NCIEffect.shading_effect.no_shading.ordinal() + "</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>" + NCIEffect.crowding_effect.crowding_effect_temp_dep.ordinal() + "</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>" + NCIEffect.nci_term.no_nci_term.ordinal() + "</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>" + NCIEffect.size_effect.no_size_effect.ordinal() + "</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>" + NCIEffect.damage_effect.no_damage_effect.ordinal() + "</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>" + NCIEffect.precipitation_effect.no_precip_effect.ordinal() + "</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>" + NCIEffect.temperature_effect.no_temp_effect.ordinal() + "</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>" + NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal() + "</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>" + NCIEffect.infection_effect.no_infection_effect.ordinal() + "</nciWhichInfectionEffect>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Western_Hemlock\">1.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Western_Redcedar\">2.5</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Amabilis_Fir\">1.18</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Subalpine_Fir\">1.19</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Western_Hemlock\">0.43</ncgVal>");
    oOut.write("<ncgVal species=\"Western_Redcedar\">0.36</ncgVal>");
    oOut.write("<ncgVal species=\"Amabilis_Fir\">0.53</ncgVal>");
    oOut.write("<ncgVal species=\"Subalpine_Fir\">0.46</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Western_Hemlock\">1.98</nccVal>");
    oOut.write("<nccVal species=\"Western_Redcedar\">4.57</nccVal>");
    oOut.write("<nccVal species=\"Amabilis_Fir\">2.98</nccVal>");
    oOut.write("<nccVal species=\"Subalpine_Fir\">5.57</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingCX0>");
    oOut.write("<nccx0Val species=\"Western_Hemlock\">300.89</nccx0Val>");
    oOut.write("<nccx0Val species=\"Western_Redcedar\">270.00</nccx0Val>");
    oOut.write("<nccx0Val species=\"Amabilis_Fir\">284.98</nccx0Val>");
    oOut.write("<nccx0Val species=\"Subalpine_Fir\">310</nccx0Val>");
    oOut.write("</nciCrowdingCX0>");
    oOut.write("<nciCrowdingCXb>");
    oOut.write("<nccxbVal species=\"Western_Hemlock\">203.04</nccxbVal>");
    oOut.write("<nccxbVal species=\"Western_Redcedar\">163.77</nccxbVal>");
    oOut.write("<nccxbVal species=\"Amabilis_Fir\">0.007</nccxbVal>");
    oOut.write("<nccxbVal species=\"Subalpine_Fir\">56.76</nccxbVal>");
    oOut.write("</nciCrowdingCXb>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Western_Hemlock\">1.63</ncdVal>");
    oOut.write("<ncdVal species=\"Western_Redcedar\">1.26</ncdVal>");
    oOut.write("<ncdVal species=\"Amabilis_Fir\">2.63</ncdVal>");
    oOut.write("<ncdVal species=\"Subalpine_Fir\">2.26</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
