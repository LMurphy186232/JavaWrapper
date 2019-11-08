package sortie.data.funcgroups.disperse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NonSpatialDisperseTest extends ModelTestCase {
  
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
      
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
      NonSpatialDisperse oDisperse = (NonSpatialDisperse) p_oDisps.get(0);

      assertEquals(15.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 0.001);
      assertEquals(13.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 0.001);
      assertEquals(17.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(0.5, ((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.2, ((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.3, ((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(0.2, ((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.8, ((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.4, ((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(0, DisperseBase.m_iSeedDistributionMethod.getValue());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests to make sure parameters are correctly displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      String[] p_sExpected;
      oManager.inputXMLParameterFile(writeNoDisperseXMLFile());
      DisperseBehaviors oDisperse = oManager.getDisperseBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      
      NonSpatialDisperse oBeh = (NonSpatialDisperse) oDisperse.createBehaviorFromParameterFileTag("NonSpatialDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Minimum DBH for Reproduction, in cm",
          "Slope Mean Non-Spatial Seed Rain, seeds/m2/ha of BA/yr",
          "Intercept of Mean Non-Spatial Seed Rain, seeds/m2/yr",
          "Seed Dist. Std. Deviation (Normal or Lognormal)",
          "Seed Dist. Clumping Parameter (Neg. Binomial)",
          "Seed Distribution"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);
      
      System.out.println("FormatDataForDisplay succeeded for disperse.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests the ValidateData function.
   */
  public void testValidateData() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = null;
      TreePopulation oPop = null;
      try {
        //Valid file
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        new File(sFileName).delete();
      }
      catch (ModelException oErr) {
        fail("Disperse validation failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
          
      //Case: a value in mp_fMinDbhForReproduction is negative
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
        NonSpatialDisperse oDisperse = (NonSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().remove(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch negative reproduction DBH" +
            " values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Minimum DBH for Reproduction") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }            
    }  
    catch (ModelException oErr) {
      fail("Disperse validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests species copying. Even though DisperseBehaviors doesn't explicitly
   * have code for copying species, this makes sure that it is treated
   * correctly.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Now copy a species
      oManager.getTreePopulation().doCopySpecies("Species_3", "Species_1");
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
      NonSpatialDisperse oDisperse = (NonSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 17.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(0)).floatValue(), 10, 0.001);
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(1)).floatValue(), 5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(2)).floatValue(), 10, 0.001);

      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(0)).floatValue(), 8, 0.001);
      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(1)).floatValue(), 50, 0.001);
      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(2)).floatValue(), 8, 0.001);

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.6, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);

      System.out.println("Copy species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  

  /**
   * Tests species changes. Even though DisperseBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);
     
      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
      NonSpatialDisperse oDisperse = (NonSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(1)).floatValue(), 5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(2)).floatValue(), 10, 0.001);

      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(0)).floatValue(), 0.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(1)).floatValue(), 50, 0.001);
      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(2)).floatValue(), 8, 0.001);
      
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      
      assertEquals(1, oDisperse.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Dispersed Seeds");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      assertEquals("Number of seeds for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Number of seeds for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Number of seeds for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Gap status", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Adult tree count", oGrid.getDataMembers()[4].getDisplayName());
      
      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
      oDisperse = (NonSpatialDisperse) p_oDisps.get(0);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(3)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fSlopeOfLambda.getValue().get(2)).floatValue(), 10, 0.001);

      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(3)).floatValue(), 0.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fInterceptOfLambda.getValue().get(2)).floatValue(), 8, 0.001);

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(3)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(3)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      
      assertEquals(1, oDisperse.getNumberOfGrids());
      oGrid = oManager.getGridByName("Dispersed Seeds");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(6, oGrid.getDataMembers().length);
      assertEquals("Number of seeds for Species 5", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Number of seeds for Species 4", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Number of seeds for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Number of seeds for Species 1", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Gap status", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Adult tree count", oGrid.getDataMembers()[5].getDisplayName());
      
      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests grid setup for DisperseBehaviors.
   */
  public void testGridSetup() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = write2DisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);
     
      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("NonSpatialDisperse");
      assertEquals(1, p_oDisps.size());
      NonSpatialDisperse oNSDisperse = (NonSpatialDisperse) p_oDisps.get(0);
      
      p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      NonGapSpatialDisperse oNGSDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
            
      assertEquals(1, oNSDisperse.getNumberOfGrids());      
      Grid oGrid = oNSDisperse.getGrid(0);
      assertEquals("Dispersed Seeds", oGrid.getName());
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      assertEquals("Number of seeds for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Number of seeds for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Number of seeds for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Gap status", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Adult tree count", oGrid.getDataMembers()[4].getDisplayName());
      
      
      assertEquals(1, oNGSDisperse.getNumberOfGrids());      
      oGrid = oNGSDisperse.getGrid(0);
      assertEquals("Dispersed Seeds", oGrid.getName());
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      assertEquals("Number of seeds for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Number of seeds for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Number of seeds for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Gap status", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Adult tree count", oGrid.getDataMembers()[4].getDisplayName());      
      
    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a file with all disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDisperseXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NonSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">5</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">10</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">0.3</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">50</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">8</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("</NonSpatialDisperse1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_standardDeviation>");
    oOut.write("<di_sdVal species=\"Species_1\">-4.1</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_2\">-4.2</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_3\">-4.3</di_sdVal>");
    oOut.write("</di_standardDeviation>");
    oOut.write("<di_clumpingParameter>");
    oOut.write("<di_cpVal species=\"Species_1\">-0.2</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_2\">-0.3</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_3\">-0.6</di_cpVal>");
    oOut.write("</di_clumpingParameter>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</GeneralDisperse>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with two disperse behaviors.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write2DisperseXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonGapDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NonSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">5</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">0.3</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">50</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("</NonSpatialDisperse1>");
    oOut.write("<NonGapDisperse2>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_3\">12.257</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_3\">2.2</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_3\">9.61</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_3\">3.2</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_lognormalCanopySTR>");
    oOut.write("<di_lcsVal species=\"Species_3\">17.206</di_lcsVal>");
    oOut.write("</di_lognormalCanopySTR>");
    oOut.write("<di_lognormalCanopyBeta>");
    oOut.write("<di_lcbVal species=\"Species_3\">2.9</di_lcbVal>");
    oOut.write("</di_lognormalCanopyBeta>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_3\">4.93</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_3\">3.9</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_3\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("</NonGapDisperse2>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_standardDeviation>");
    oOut.write("<di_sdVal species=\"Species_1\">-4.1</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_2\">-4.2</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_3\">-4.3</di_sdVal>");
    oOut.write("</di_standardDeviation>");
    oOut.write("<di_clumpingParameter>");
    oOut.write("<di_cpVal species=\"Species_1\">-0.2</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_2\">-0.3</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_3\">-0.6</di_cpVal>");
    oOut.write("</di_clumpingParameter>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</GeneralDisperse>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with no disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoDisperseXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
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
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
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
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("<tr_treemap>");
    oOut.write("<tm_treeSettings sp=\"Species_3\" tp=\"3\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">1</fl>");
    oOut.write("<fl c=\"1\">1</fl>");
    oOut.write("<fl c=\"2\">20</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">10</fl>");
    oOut.write("<fl c=\"1\">10</fl>");
    oOut.write("<fl c=\"2\">30</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">20</fl>");
    oOut.write("<fl c=\"1\">20</fl>");
    oOut.write("<fl c=\"2\">40</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">30</fl>");
    oOut.write("<fl c=\"1\">30</fl>");
    oOut.write("<fl c=\"2\">50</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">40</fl>");
    oOut.write("<fl c=\"1\">40</fl>");
    oOut.write("<fl c=\"2\">20</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">50</fl>");
    oOut.write("<fl c=\"1\">50</fl>");
    oOut.write("<fl c=\"2\">30</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">60</fl>");
    oOut.write("<fl c=\"1\">60</fl>");
    oOut.write("<fl c=\"2\">40</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">70</fl>");
    oOut.write("<fl c=\"1\">70</fl>");
    oOut.write("<fl c=\"2\">50</fl>");
    oOut.write("</tree>");
    oOut.write("</tr_treemap>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>10</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>10</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">13.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_1\">0.5</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">0.2</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">0.3</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">0.2</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">0.8</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">0.4</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
