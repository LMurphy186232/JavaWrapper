package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class DensitySelfThinningTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("DensitySelfThinning");
      assertEquals(1, p_oBehs.size());
      DensitySelfThinning oMort = (DensitySelfThinning) p_oBehs.get(0);

      assertEquals(9, ((Float)oMort.mp_fDensSelfThinNeighRadius.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0, ((Float)oMort.mp_fDensSelfThinMinDensity.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.1019, ((Float)oMort.mp_fDensSelfThinAsymptote.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.5391, ((Float)oMort.mp_fDensSelfThinDiamEffect.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.00000877, ((Float)oMort.mp_fDensSelfThinDensityEffect.getValue().get(0)).floatValue(), 0.0001);
          
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("DensitySelfThinning");
      assertEquals(1, p_oBehs.size());
      DensitySelfThinning oMort = (DensitySelfThinning) p_oBehs.get(0);

      assertEquals(0.1019, ((Float)oMort.mp_fDensSelfThinAsymptote.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.5391, ((Float)oMort.mp_fDensSelfThinDiamEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.00000877, ((Float)oMort.mp_fDensSelfThinDensityEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(9, ((Float)oMort.mp_fDensSelfThinNeighRadius.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0, ((Float)oMort.mp_fDensSelfThinMinDensity.getValue().get(0)).floatValue(), 0.0001);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
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

      /********************
        Density self-thinning mortality testing
       *********************/
      try {
        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeValidFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        new File(sFileName).delete();

      }
      catch (ModelException oErr) {
        fail("Mortality validation failed with message " + oErr.getMessage());
      }

      //Exception processing - error file 1
      try {
        oManager.clearCurrentData();
        sFileName = writeErrorFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in error file 1.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - error file 2
      try {
        oManager.clearCurrentData();
        sFileName = writeErrorFile2();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in error file 2.");
      }
      catch (ModelException oErr) {
        ;
      }
      catch (java.io.IOException oE) {
        fail("Caught IOException.  Message: " + oE.getMessage());
      }

      //Exception processing - error file 3
      try {
        oManager.clearCurrentData();
        sFileName = writeErrorFile3();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in error file 3.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - error file 4
      try {
        oManager.clearCurrentData();
        sFileName = writeErrorFile4();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in error file 4.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - error file 5
      try {
        oManager.clearCurrentData();
        sFileName = writeErrorFile5();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in error file 5.");
      }
      catch (ModelException oErr) {
        ;
      }
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    catch (ModelException oErr) {
      fail("Mortality validation failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }

  /**
   * Writes a file of valid values for density self-thinning.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  public static String writeValidFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    writeCommonStuff(oOut);
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the timestep length is greater than 1.
   * @throws IOException if there is a problem writing the file.
   * @return String File to write to.
   */
  public static String writeErrorFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>3.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>densityselfthinning</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the neighborhood radius is less than or equal to 0.
   * @throws IOException if there is a problem writing the file.
   * @return String File to write to.
   */
  public static String writeErrorFile2() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    writeCommonStuff(oOut);
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">-9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the minimum density is less than 0.
   * @throws IOException if there is a problem writing the file.
   * @return String File to write to.
   */
  public static String writeErrorFile3() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    writeCommonStuff(oOut);
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">-20</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the self-thinning asymptote is less than or equal
   * to 0.
   * @throws IOException if there is a problem writing the file.
   * @return String File to write to.
   */
  public static String writeErrorFile4() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    writeCommonStuff(oOut);
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">-0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the density effect is less than or equal to 0.
   * @throws IOException if there is a problem writing the file.
   * @return String File to write to.
   */
  public static String writeErrorFile5() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    writeCommonStuff(oOut);
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">-0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes stuff common to all parameter files in this class.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing the file.
   */
  private static void writeCommonStuff(FileWriter oOut) throws java.io.
  IOException {
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DensitySelfThinning</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>densityselfthinning</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }   
}
