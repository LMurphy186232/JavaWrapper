package sortie.data.funcgroups.analysis;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.ModelException;
import sortie.gui.DetailedOutputGridSetup;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;

public class RipleysKCalculatorTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oRips = oAnalysis.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oRips.size());
      RipleysKCalculator oRip = (RipleysKCalculator) p_oRips.get(0);
      assertEquals( 50, oRip.m_fRipleysKMaxDistance.getValue(), 0.001);
      assertEquals( 1, oRip.m_fRipleysKDistanceInc.getValue(), 0.001);
      
      assertEquals(1, oRip.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Ripley's K");
      assertEquals(252, oGrid.getDataMembers().length);
      assertEquals("Distance Inc", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Max Distance", oGrid.getDataMembers()[1].getDisplayName());
      for (int iInc = 0; iInc < 50; iInc++) {
        assertEquals("All Species Dist "+iInc+" K Value", oGrid.getDataMembers()[iInc+2].getDisplayName());
      }         
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
   * Tests to make sure the appropriate parameters are displayed for each
   * behavior.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      String[] p_sExpected;

      RipleysKCalculator oRip = (RipleysKCalculator) oAnalysis.createBehaviorFromParameterFileTag("RipleysK");
      p_sExpected = new String[] {
          "Ripley's K - Maximum Distance (m)",
          "Ripley's K - Distance Increment (m)"};
      TestTable(oRip.formatDataForDisplay(oPop), p_sExpected);
      

      System.out.println("FormatDataForDisplay succeeded for Analysis.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests reading of parameter file XML Ripley's K calculator settings.
   */
  public void testReadRipleysKSettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oRips = oAnalysis.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oRips.size());
      RipleysKCalculator oRip = (RipleysKCalculator) p_oRips.get(0);
      assertEquals( 15, oRip.m_fRipleysKMaxDistance.getValue(), 0.001);
      assertEquals( 5, oRip.m_fRipleysKDistanceInc.getValue(), 0.001);
      
      assertEquals(1, oRip.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Ripley's K");
      assertEquals(14, oGrid.getDataMembers().length);
      assertEquals("Distance Inc", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Max Distance", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("All Species Dist 0 K Value", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("All Species Dist 1 K Value", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("All Species Dist 2 K Value", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Species 1 Dist 0 K Value", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Species 1 Dist 1 K Value", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Species 1 Dist 2 K Value", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Species 2 Dist 0 K Value", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Species 2 Dist 1 K Value", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Species 2 Dist 2 K Value", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Species 3 Dist 0 K Value", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Species 3 Dist 1 K Value", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Species 3 Dist 2 K Value", oGrid.getDataMembers()[13].getDisplayName());
      
      oManager.writeParameterFile(sFileName);
      oRip = (RipleysKCalculator) p_oRips.get(0);
      assertEquals( 15, oRip.m_fRipleysKMaxDistance.getValue(), 0.001);
      assertEquals( 5, oRip.m_fRipleysKDistanceInc.getValue(), 0.001);
      
      System.out.println("Ripley's K parameter reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Ripley's K reading test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests ValidateData() for data analysis behaviors.
   * @throws ModelException if the validation happens incorrectly.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid Ripley's K file
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getAnalysisBehaviors().validateData(oManager.getTreePopulation());
      
    }
    catch (ModelException oErr) {
      fail("Analysis behavior validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete(); 
    }
    
    //Exception - Ripley's K max distance not positive number
    try {
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oRips = oAnalysis.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oRips.size());
      RipleysKCalculator oBeh = (RipleysKCalculator) p_oRips.get(0);
      TreePopulation oPop = oManager.getTreePopulation();
      oBeh.m_fRipleysKMaxDistance.setValue(-2);
      oAnalysis.validateData(oPop);
      fail("Didn't catch non-positive Ripley's K maximum distance.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
    
    //Exception - Ripley's K distance increment not positive number
    try {
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oRips = oAnalysis.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oRips.size());
      RipleysKCalculator oBeh = (RipleysKCalculator) p_oRips.get(0);
      TreePopulation oPop = oManager.getTreePopulation();
      oBeh.m_fRipleysKDistanceInc.setValue(-2);
      oAnalysis.validateData(oPop);
      fail("Didn't catch non-positive Ripley's K distance increment.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
    
    //Exception - Ripley's K max distance not greater than distance increment
    try {
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oRips = oAnalysis.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oRips.size());
      RipleysKCalculator oBeh = (RipleysKCalculator) p_oRips.get(0);
      TreePopulation oPop = oManager.getTreePopulation();
      oBeh.m_fRipleysKMaxDistance.setValue(5);
      oBeh.m_fRipleysKDistanceInc.setValue(10);
      oAnalysis.validateData(oPop);
      fail("Didn't catch Ripley's K maximum distance less than distance increment.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Analysis behaviors ValidateData test was successful.");

  }


  /**
   * Makes sure that changes in user parameters are correctly reflected in the
   * Ripley's K grid and any output settings that may have been made from 
   * that grid.
   */
  public void testRipleysKGridChange() {
    
    //Allow the grid to be set up as default
    MainWindow oMainWindow = new MainWindow();
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = oMainWindow.getDataManager();
      oManager.clearCurrentData();
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAna = oManager.getAnalysisBehaviors();
      OutputBehaviors oOutput = oManager.getOutputBehaviors();
      DetailedOutput oDetailed = oOutput.getDetailedOutput();
      TreePopulation oPop = oManager.getTreePopulation();
      ArrayList<Behavior> p_oBehs = oAna.getBehaviorByParameterFileTag("RipleysK");
      assertEquals(1, p_oBehs.size());
      RipleysKCalculator oBeh = (RipleysKCalculator) p_oBehs.get(0);
      //oAna.validateData(oPop); //this causes grid setup
      
      //Set the grid settings to use all data members for Ripley's K
      DetailedOutputGridSetup oWindow = new DetailedOutputGridSetup(null, oDetailed);
      oWindow.m_jGridListCombo.setSelectedIndex(1);
      oWindow.actionPerformed(new ActionEvent(oWindow.m_jGridListCombo, 0, "OK"));
      int iNumToSelect = oWindow.m_jDataMemberListModel.getSize(),
          i;
      int[] p_iIndexes = new int[iNumToSelect];
      for (i = 0; i < iNumToSelect; i++)
        p_iIndexes[i] = i;  
      oWindow.m_jDataMemberList.setSelectedIndices(p_iIndexes); 
      oWindow.m_jTimestepsEdit.setText("1");
      oWindow.m_jAddButton.doClick();
      oWindow.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Verify the result
      DetailedGridSettings oSettings = oDetailed.getDetailedGridSetting(0);
      assertEquals(14, oSettings.getNumberOfFloats());
      
      //Now change the number of increments to 2
      oBeh.m_fRipleysKMaxDistance.setValue(12);
      oBeh.m_fRipleysKDistanceInc.setValue(6);
      oBeh.validateData(oPop);
      oSettings = oDetailed.getDetailedGridSetting(0);
      assertEquals(10, oSettings.getNumberOfFloats());
      assertEquals("0_3", oSettings.getFloat(2).getCodeName());
      assertEquals("1_3", oSettings.getFloat(3).getCodeName());
      
      assertEquals("0_0", oSettings.getFloat(4).getCodeName());
      assertEquals("1_0", oSettings.getFloat(5).getCodeName());
      
      assertEquals("0_1", oSettings.getFloat(6).getCodeName());
      assertEquals("1_1", oSettings.getFloat(7).getCodeName());
      
      assertEquals("0_2", oSettings.getFloat(8).getCodeName());
      assertEquals("1_2", oSettings.getFloat(9).getCodeName());
      
      System.out.println("Ripley's K grid change test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeRipleysKXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      Grid oGrid = oManager.getGridByName("Ripley's K");
      //Don't test grid cell size - this is always one cell per plot.
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(14, oGrid.getDataMembers().length);
      assertEquals("Distance Inc", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Max Distance", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("All Species Dist 0 K Value", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("All Species Dist 1 K Value", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("All Species Dist 2 K Value", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Species 1 Dist 0 K Value", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Species 1 Dist 1 K Value", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Species 1 Dist 2 K Value", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Species 2 Dist 0 K Value", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Species 2 Dist 1 K Value", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Species 2 Dist 2 K Value", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Species 3 Dist 0 K Value", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Species 3 Dist 1 K Value", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Species 3 Dist 2 K Value", oGrid.getDataMembers()[13].getDisplayName());
      
      
      String[] sSpecies = new String[] {
          "Species 2", "Species 3", "Species 4",
          "Species 5", "Species 6"};

      TreePopulation oPop = oManager.getTreePopulation(); 
      oPop.setSpeciesNames(sSpecies);
      oGrid = oManager.getGridByName("Ripley's K");
      //Don't test grid cell size - this is always one cell per plot.
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(20, oGrid.getDataMembers().length);
      assertEquals("Distance Inc", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Max Distance", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("All Species Dist 0 K Value", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("All Species Dist 1 K Value", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("All Species Dist 2 K Value", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Species 2 Dist 0 K Value", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Species 2 Dist 1 K Value", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Species 2 Dist 2 K Value", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Species 3 Dist 0 K Value", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Species 3 Dist 1 K Value", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Species 3 Dist 2 K Value", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Species 4 Dist 0 K Value", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Species 4 Dist 1 K Value", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Species 4 Dist 2 K Value", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Species 5 Dist 0 K Value", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Species 5 Dist 1 K Value", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Species 5 Dist 2 K Value", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Species 6 Dist 0 K Value", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Species 6 Dist 1 K Value", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Species 6 Dist 2 K Value", oGrid.getDataMembers()[19].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
    
  /**
   * Writes plot and trees with three species.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writePlotAndTrees(FileWriter oOut) throws IOException {
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
  }
  
  /**
   * Writes a valid file for testing parameter file reading of Ripley's K
   * calculator behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeRipleysKXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writePlotAndTrees(oOut);
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RipleysK</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Ripley's K\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<RipleysK1>");
    oOut.write("<an_RipleysKMaxDistance>15</an_RipleysKMaxDistance>");
    oOut.write("<an_RipleysKDistanceInc>5</an_RipleysKDistanceInc>");
    oOut.write("</RipleysK1>");
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
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>104.0</plot_lenX>");
    oOut.write("<plot_lenY>96.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Ripley's K</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<an_RipleysKMaxDistance>50</an_RipleysKMaxDistance>");
    oOut.write("<an_RipleysKDistanceInc>1</an_RipleysKDistanceInc>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

}
