package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import sortie.ModelTestCase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Tests the TreePopulation class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestTreeBehavior extends ModelTestCase {
  
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;  
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeV6XMLFile1();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oTreeBeh = oPop.getTreeBehavior();
      oPop.validateData(oPop);
      
      assertEquals(0.1, oTreeBeh.m_fInitialSeedlingSize.getValue(), 0.0001);
      assertEquals(8, oTreeBeh.mp_fSizeClasses.getValue().size());
      assertEquals(2.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(12.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);
      assertEquals(14.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(5)).floatValue(), 0.0001);
      assertEquals(16.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(6)).floatValue(), 0.0001);
      assertEquals(18.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(7)).floatValue(), 0.0001);
      
      
    }
    catch (ModelException oErr) {
      fail("Version 6 file reading failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  public void testSizeClassChanges() {
    GUIManager oManager = null;
    String sFileName = null;  
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oTreeBeh = oPop.getTreeBehavior();
      oPop.validateData(oPop);
                  
      assertEquals(25, oTreeBeh.mp_fSizeClasses.getValue().size());
      assertEquals(0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(7.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(12.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(17.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(22.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);
      assertEquals(27.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(5)).floatValue(), 0.0001);
      assertEquals(32.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(6)).floatValue(), 0.0001);
      assertEquals(37.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(7)).floatValue(), 0.0001);
      assertEquals(42.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(8)).floatValue(), 0.0001);
      assertEquals(47.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(9)).floatValue(), 0.0001);
      assertEquals(52.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(10)).floatValue(), 0.0001);
      assertEquals(57.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(11)).floatValue(), 0.0001);
      assertEquals(62.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(12)).floatValue(), 0.0001);
      assertEquals(67.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(13)).floatValue(), 0.0001);
      assertEquals(72.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(14)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(15)).floatValue(), 0.0001);
      assertEquals(32.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(16)).floatValue(), 0.0001);
      assertEquals(34.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(17)).floatValue(), 0.0001);
      assertEquals(36.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(18)).floatValue(), 0.0001);
      assertEquals(38.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(19)).floatValue(), 0.0001);
      assertEquals(40.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(20)).floatValue(), 0.0001);
      assertEquals(42.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(21)).floatValue(), 0.0001);
      assertEquals(44.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(22)).floatValue(), 0.0001);
      assertEquals(46.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(23)).floatValue(), 0.0001);
      assertEquals(48.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(24)).floatValue(), 0.0001);
      
      //Change the size classes
      Float[] p_fNew = new Float[] {new Float(7.5),
          new Float(12.5),
          new Float(17.5),
          new Float(22.5),
          new Float(27.5),
          new Float(32.5),
          new Float(37.5),
          new Float(42.5),
          new Float(47.5),
          new Float(52.5),
          new Float(57.5),
          new Float(62.5),
          new Float(67.5),
          new Float(72.5)};
      
      oTreeBeh.setSizeClasses(p_fNew);
      
      assertEquals(14, oTreeBeh.mp_fSizeClasses.getValue().size());
      assertEquals(7.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(17.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(22.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(27.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);
      assertEquals(32.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(5)).floatValue(), 0.0001);
      assertEquals(37.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(6)).floatValue(), 0.0001);
      assertEquals(42.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(7)).floatValue(), 0.0001);
      assertEquals(47.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(8)).floatValue(), 0.0001);
      assertEquals(52.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(9)).floatValue(), 0.0001);
      assertEquals(57.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(10)).floatValue(), 0.0001);
      assertEquals(62.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(11)).floatValue(), 0.0001);
      assertEquals(67.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(12)).floatValue(), 0.0001);
      assertEquals(72.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(13)).floatValue(), 0.0001);      
    }
    catch (ModelException oErr) {
      fail("Testing failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a parameter file.
   * @return String Filename written.
   * @throws IOException if there is a problem writing to the file.
   */
  private String writeXMLFile1() throws IOException {

    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07020101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>10</timesteps>"); 
    oOut.write("<randomSeed>0</randomSeed>"); 
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>"); 
    oOut.write("<plot_lenX>300.0</plot_lenX>"); 
    oOut.write("<plot_lenY>300.0</plot_lenY>"); 
    oOut.write("<plot_latitude>55.0</plot_latitude>"); 
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>"); 
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>"); 
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>"); 
    oOut.write("<plot_temp_C>0.0</plot_temp_C>"); 
    oOut.write("<plot_n_dep>0.0</plot_n_dep>"); 
    oOut.write("<plot_title>Curtis</plot_title>"); 
    oOut.write("</plot>"); 
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Interior_Spruce\"/>");
    oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
    oOut.write("</tr_speciesList>"); 
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"Seedling\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s7.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s12.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s17.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s22.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s27.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s32.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s37.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s42.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s47.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s52.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s57.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s62.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s67.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s72.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s30.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s32.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s34.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s36.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s38.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s40.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s42.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s44.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s46.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s48.0\"/>");
    oOut.write("</tr_sizeClasses>"); 
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"Interior_Spruce\">");
    oOut.write("<tr_initialDensity sizeClass=\"s27.5\">17.4</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s32.5\">14.6</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s37.5\">12.1</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s42.5\">6.2</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s47.5\">5.2</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s52.5\">5.4</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s57.5\">1.2</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s72.5\">0.9</tr_initialDensity>"); 
    oOut.write("</tr_idVals>"); 
    oOut.write("<tr_idVals whatSpecies=\"Subalpine_Fir\">");
    oOut.write("<tr_initialDensity sizeClass=\"s17.5\">172.2</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s22.5\">141.8</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s27.5\">177.5</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s32.5\">72.4</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s37.5\">54.1</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s42.5\">14.1</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s47.5\">1.8</tr_initialDensity>"); 
    oOut.write("<tr_initialDensity sizeClass=\"s52.5\">4.5</tr_initialDensity>"); 
    oOut.write("</tr_idVals>"); 
    oOut.write("</tr_initialDensities>"); 
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>"); 
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Interior_Spruce\">3.0</tr_madVal>"); 
    oOut.write("<tr_madVal species=\"Lodgepole_Pine\">5.0</tr_madVal>"); 
    oOut.write("<tr_madVal species=\"Subalpine_Fir\">5.0</tr_madVal>"); 
    oOut.write("<tr_madVal species=\"Trembling_Aspen\">5.0</tr_madVal>"); 
    oOut.write("</tr_minAdultDBH>"); 
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Interior_Spruce\">1.3</tr_mshVal>"); 
    oOut.write("<tr_mshVal species=\"Lodgepole_Pine\">1.3</tr_mshVal>"); 
    oOut.write("<tr_mshVal species=\"Subalpine_Fir\">1.3</tr_mshVal>"); 
    oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.3</tr_mshVal>"); 
    oOut.write("</tr_maxSeedlingHeight>"); 
    oOut.write("</trees>"); 

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Interior_Spruce\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Interior_Spruce\">0</tr_wahdVal>"); 
    oOut.write("<tr_wahdVal species=\"Lodgepole_Pine\">0</tr_wahdVal>"); 
    oOut.write("<tr_wahdVal species=\"Subalpine_Fir\">0</tr_wahdVal>"); 
    oOut.write("<tr_wahdVal species=\"Trembling_Aspen\">0</tr_wahdVal>"); 
    oOut.write("</tr_whatAdultHeightDiam>"); 
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Interior_Spruce\">0</tr_wsahdVal>"); 
    oOut.write("<tr_wsahdVal species=\"Lodgepole_Pine\">0</tr_wsahdVal>"); 
    oOut.write("<tr_wsahdVal species=\"Subalpine_Fir\">0</tr_wsahdVal>"); 
    oOut.write("<tr_wsahdVal species=\"Trembling_Aspen\">0</tr_wsahdVal>"); 
    oOut.write("</tr_whatSaplingHeightDiam>"); 
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Interior_Spruce\">0</tr_wsehdVal>"); 
    oOut.write("<tr_wsehdVal species=\"Lodgepole_Pine\">0</tr_wsehdVal>"); 
    oOut.write("<tr_wsehdVal species=\"Subalpine_Fir\">0</tr_wsehdVal>"); 
    oOut.write("<tr_wsehdVal species=\"Trembling_Aspen\">0</tr_wsehdVal>"); 
    oOut.write("</tr_whatSeedlingHeightDiam>"); 
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Interior_Spruce\">0</tr_wacrdVal>"); 
    oOut.write("<tr_wacrdVal species=\"Lodgepole_Pine\">0</tr_wacrdVal>"); 
    oOut.write("<tr_wacrdVal species=\"Subalpine_Fir\">0</tr_wacrdVal>"); 
    oOut.write("<tr_wacrdVal species=\"Trembling_Aspen\">0</tr_wacrdVal>"); 
    oOut.write("</tr_whatAdultCrownRadDiam>"); 
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Interior_Spruce\">0</tr_wscrdVal>"); 
    oOut.write("<tr_wscrdVal species=\"Lodgepole_Pine\">0</tr_wscrdVal>"); 
    oOut.write("<tr_wscrdVal species=\"Subalpine_Fir\">0</tr_wscrdVal>"); 
    oOut.write("<tr_wscrdVal species=\"Trembling_Aspen\">0</tr_wscrdVal>"); 
    oOut.write("</tr_whatSaplingCrownRadDiam>"); 
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Interior_Spruce\">0</tr_wachhVal>"); 
    oOut.write("<tr_wachhVal species=\"Lodgepole_Pine\">0</tr_wachhVal>"); 
    oOut.write("<tr_wachhVal species=\"Subalpine_Fir\">0</tr_wachhVal>"); 
    oOut.write("<tr_wachhVal species=\"Trembling_Aspen\">0</tr_wachhVal>"); 
    oOut.write("</tr_whatAdultCrownHeightHeight>"); 
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Interior_Spruce\">0</tr_wschhVal>"); 
    oOut.write("<tr_wschhVal species=\"Lodgepole_Pine\">0</tr_wschhVal>"); 
    oOut.write("<tr_wschhVal species=\"Subalpine_Fir\">0</tr_wschhVal>"); 
    oOut.write("<tr_wschhVal species=\"Trembling_Aspen\">0</tr_wschhVal>"); 
    oOut.write("</tr_whatSaplingCrownHeightHeight>"); 
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Interior_Spruce\">35.0</tr_chVal>"); 
    oOut.write("<tr_chVal species=\"Lodgepole_Pine\">33.3129</tr_chVal>"); 
    oOut.write("<tr_chVal species=\"Subalpine_Fir\">30.0</tr_chVal>"); 
    oOut.write("<tr_chVal species=\"Trembling_Aspen\">33.53</tr_chVal>"); 
    oOut.write("</tr_canopyHeight>"); 
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Interior_Spruce\">0.0239</tr_sacrVal>"); 
    oOut.write("<tr_sacrVal species=\"Lodgepole_Pine\">0.0303</tr_sacrVal>"); 
    oOut.write("<tr_sacrVal species=\"Subalpine_Fir\">0.0251</tr_sacrVal>"); 
    oOut.write("<tr_sacrVal species=\"Trembling_Aspen\">0.0328</tr_sacrVal>"); 
    oOut.write("</tr_stdAsympCrownRad>"); 
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Interior_Spruce\">1.0</tr_screVal>"); 
    oOut.write("<tr_screVal species=\"Lodgepole_Pine\">1.0</tr_screVal>"); 
    oOut.write("<tr_screVal species=\"Subalpine_Fir\">1.0</tr_screVal>"); 
    oOut.write("<tr_screVal species=\"Trembling_Aspen\">1.0</tr_screVal>"); 
    oOut.write("</tr_stdCrownRadExp>"); 
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Interior_Spruce\">0.405</tr_sachVal>"); 
    oOut.write("<tr_sachVal species=\"Lodgepole_Pine\">0.201</tr_sachVal>"); 
    oOut.write("<tr_sachVal species=\"Subalpine_Fir\">0.454</tr_sachVal>"); 
    oOut.write("<tr_sachVal species=\"Trembling_Aspen\">0.301</tr_sachVal>"); 
    oOut.write("</tr_stdAsympCrownHt>"); 
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Interior_Spruce\">1.0</tr_scheVal>"); 
    oOut.write("<tr_scheVal species=\"Lodgepole_Pine\">1.0</tr_scheVal>"); 
    oOut.write("<tr_scheVal species=\"Subalpine_Fir\">1.0</tr_scheVal>"); 
    oOut.write("<tr_scheVal species=\"Trembling_Aspen\">1.0</tr_scheVal>"); 
    oOut.write("</tr_stdCrownHtExp>"); 
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Interior_Spruce\">0.677076</tr_cdtdVal>"); 
    oOut.write("<tr_cdtdVal species=\"Lodgepole_Pine\">0.77533</tr_cdtdVal>"); 
    oOut.write("<tr_cdtdVal species=\"Subalpine_Fir\">0.63795</tr_cdtdVal>"); 
    oOut.write("<tr_cdtdVal species=\"Trembling_Aspen\">0.7855</tr_cdtdVal>"); 
    oOut.write("</tr_conversionDiam10ToDBH>"); 
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Interior_Spruce\">0.0</tr_idtdVal>"); 
    oOut.write("<tr_idtdVal species=\"Lodgepole_Pine\">0.0</tr_idtdVal>"); 
    oOut.write("<tr_idtdVal species=\"Subalpine_Fir\">0.0</tr_idtdVal>"); 
    oOut.write("<tr_idtdVal species=\"Trembling_Aspen\">0.0</tr_idtdVal>"); 
    oOut.write("</tr_interceptDiam10ToDBH>"); 
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Interior_Spruce\">0.0299364</tr_soahVal>"); 
    oOut.write("<tr_soahVal species=\"Lodgepole_Pine\">0.0395</tr_soahVal>"); 
    oOut.write("<tr_soahVal species=\"Subalpine_Fir\">0.0305068</tr_soahVal>"); 
    oOut.write("<tr_soahVal species=\"Trembling_Aspen\">0.03746</tr_soahVal>"); 
    oOut.write("</tr_slopeOfAsymHeight>"); 
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Interior_Spruce\">0.02071</tr_sohdVal>"); 
    oOut.write("<tr_sohdVal species=\"Lodgepole_Pine\">0.02468</tr_sohdVal>"); 
    oOut.write("<tr_sohdVal species=\"Subalpine_Fir\">0.01887</tr_sohdVal>"); 
    oOut.write("<tr_sohdVal species=\"Trembling_Aspen\">0.03979</tr_sohdVal>"); 
    oOut.write("</tr_slopeOfHeight-Diam10>"); 
    oOut.write("</allometry>"); 
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a parameter file. This has an empty initial densities tag.
   * @return String Filename written.
   * @throws IOException if there is a problem writing to the file.
   */
  private String writeV6XMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    oOut.write("<paramFile fileCode=\"06110101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>48.5</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_title>WS-WC full NCI growth community dynamics test</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"White_Cedar\"/>");
    oOut.write("<tr_species speciesName=\"Balsam_Fir\"/>");
    oOut.write("<tr_species speciesName=\"Mountain_Maple\"/>");
    oOut.write("<tr_species speciesName=\"White_Spruce\"/>");
    oOut.write("<tr_species speciesName=\"Jack_Pine\"/>");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
    oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s2.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s5.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s7.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s12.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s14.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s16.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s18.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities/>");
    oOut.write("<tr_seedlingHeightClass1>25.0</tr_seedlingHeightClass1>");
    oOut.write("<tr_seedlingHeightClass2>50.0</tr_seedlingHeightClass2>");
    oOut.write("<tr_seedlingHeight1Density>");
    oOut.write("<tr_sh1dVal species=\"White_Cedar\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Balsam_Fir\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Mountain_Maple\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"White_Spruce\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Jack_Pine\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Trembling_Aspen\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Paper_Birch\">0.0</tr_sh1dVal>");
    oOut.write("</tr_seedlingHeight1Density>");
    oOut.write("<tr_seedlingHeight2Density>");
    oOut.write("<tr_sh2dVal species=\"White_Cedar\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Balsam_Fir\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Mountain_Maple\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"White_Spruce\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Jack_Pine\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Trembling_Aspen\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Paper_Birch\">0.0</tr_sh2dVal>");
    oOut.write("</tr_seedlingHeight2Density>");
    oOut.write("<tr_seedlingHeight3Density>");
    oOut.write("<tr_sh3dVal species=\"White_Cedar\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Balsam_Fir\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Mountain_Maple\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"White_Spruce\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Jack_Pine\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Trembling_Aspen\">0.0</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Paper_Birch\">0.0</tr_sh3dVal>");
    oOut.write("</tr_seedlingHeight3Density>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"White_Cedar\">3.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Balsam_Fir\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Mountain_Maple\">6.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"White_Spruce\">3.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Jack_Pine\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Trembling_Aspen\">7.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Paper_Birch\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"White_Cedar\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Balsam_Fir\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Mountain_Maple\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"White_Spruce\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Jack_Pine\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Paper_Birch\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Mountain_Maple\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"White_Spruce\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Jack_Pine\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Mountain_Maple\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"White_Spruce\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Jack_Pine\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Mountain_Maple\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"White_Spruce\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Jack_Pine\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Mountain_Maple\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"White_Spruce\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Jack_Pine\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"White_Spruce\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Jack_Pine\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"White_Cedar\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Balsam_Fir\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Mountain_Maple\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"White_Spruce\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Jack_Pine\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Trembling_Aspen\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Paper_Birch\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"White_Cedar\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Balsam_Fir\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Mountain_Maple\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"White_Spruce\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Jack_Pine\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Trembling_Aspen\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Paper_Birch\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"White_Cedar\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Balsam_Fir\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Mountain_Maple\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"White_Spruce\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Jack_Pine\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Trembling_Aspen\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Paper_Birch\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"White_Cedar\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Balsam_Fir\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Mountain_Maple\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"White_Spruce\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Jack_Pine\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Trembling_Aspen\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Paper_Birch\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"White_Cedar\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Balsam_Fir\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Mountain_Maple\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"White_Spruce\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Jack_Pine\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Trembling_Aspen\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Paper_Birch\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"White_Cedar\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Balsam_Fir\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Mountain_Maple\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"White_Spruce\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Jack_Pine\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Trembling_Aspen\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Paper_Birch\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"White_Cedar\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Balsam_Fir\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Mountain_Maple\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"White_Spruce\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Jack_Pine\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Trembling_Aspen\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Paper_Birch\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"White_Cedar\">18.78</tr_chVal>");
    oOut.write("<tr_chVal species=\"Balsam_Fir\">21.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Mountain_Maple\">8.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"White_Spruce\">34.56</tr_chVal>");
    oOut.write("<tr_chVal species=\"Jack_Pine\">29.1</tr_chVal>");
    oOut.write("<tr_chVal species=\"Trembling_Aspen\">26.26</tr_chVal>");
    oOut.write("<tr_chVal species=\"Paper_Birch\">20.74</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"White_Cedar\">0.199</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Balsam_Fir\">0.266</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Mountain_Maple\">0.177</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"White_Spruce\">0.262</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Jack_Pine\">0.041</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Trembling_Aspen\">0.092</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Paper_Birch\">0.22</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"White_Cedar\">0.468</tr_screVal>");
    oOut.write("<tr_screVal species=\"Balsam_Fir\">0.366</tr_screVal>");
    oOut.write("<tr_screVal species=\"Mountain_Maple\">0.472</tr_screVal>");
    oOut.write("<tr_screVal species=\"White_Spruce\">0.361</tr_screVal>");
    oOut.write("<tr_screVal species=\"Jack_Pine\">0.859</tr_screVal>");
    oOut.write("<tr_screVal species=\"Trembling_Aspen\">0.761</tr_screVal>");
    oOut.write("<tr_screVal species=\"Paper_Birch\">0.506</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"White_Cedar\">0.652</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Balsam_Fir\">0.345</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Mountain_Maple\">0.357</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"White_Spruce\">0.63</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Jack_Pine\">1.225</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Trembling_Aspen\">0.262</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Paper_Birch\">0.966</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"White_Cedar\">0.982</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Balsam_Fir\">1.188</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Mountain_Maple\">0.877</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"White_Spruce\">1.01</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Jack_Pine\">0.581</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Trembling_Aspen\">1.11</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Paper_Birch\">0.751</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"White_Cedar\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Balsam_Fir\">0.7892</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Mountain_Maple\">0.8526</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"White_Spruce\">0.7557</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Jack_Pine\">0.7906</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Trembling_Aspen\">0.8241</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Paper_Birch\">0.7557</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"White_Cedar\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Balsam_Fir\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Mountain_Maple\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"White_Spruce\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Jack_Pine\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Trembling_Aspen\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Paper_Birch\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"White_Cedar\">0.046</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Balsam_Fir\">0.064</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Mountain_Maple\">0.226</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"White_Spruce\">0.03</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Jack_Pine\">0.051</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Trembling_Aspen\">0.067</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Paper_Birch\">0.084</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"White_Cedar\">0.018</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Balsam_Fir\">0.015</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Mountain_Maple\">0.041</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"White_Spruce\">0.016</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Jack_Pine\">0.041</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Trembling_Aspen\">0.031</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Paper_Birch\">0.048</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"White_Cedar\">0.005</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Balsam_Fir\">5.0E-4</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Mountain_Maple\">0.02</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"White_Spruce\">5.0E-4</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Jack_Pine\">6.0E-4</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Trembling_Aspen\">0.009</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Paper_Birch\">5.0E-4</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<mortalityUtility/>");
    oOut.write("</paramFile>");
    
    
    oOut.close();
    return sFileName;
  }

}
