package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.IOException;

import sortie.ModelTestCase;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Tests the TreePopulation class, with snag stuff.
 * @author Lora E. Murphy
 */
public class TestTreePopulationSnags extends ModelTestCase {


  /**
   * Tests reading snag initial densities.
   */
  public void testLiveTreeDensityReading() {
    String sFunc = "testLiveTreeDensityReading";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile1(), null);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // No snag densities, just live tree densities
      //----------------------------------------------------------------------/
  
      assertEquals(1.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);

      // These are size class first, then species
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(0);
      assertEquals(10.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(1);
      assertEquals(11.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(33.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(2);
      assertEquals(12.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(45.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);


      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }
  
  /**
   * Tests reading snag initial densities.
   */
  public void testSnagDensityReading() {
    String sFunc = "testSnagDensityReading";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile2(), null);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // No live tree densities, just snag densities
      //----------------------------------------------------------------------/
      assertEquals(10.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);

      // These are size class first, then species
      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(0);
      assertEquals(0.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(1);
      assertEquals(25.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(2);
      assertEquals(40.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(50.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }
  
  /**
   * Tests reading live + snag initial densities.
   */
  public void testMixedTreeDensityReading() {
    String sFunc = "testMixedTreeDensityReading";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile3(), null);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // Both snag and live tree densities
      //----------------------------------------------------------------------/
      assertEquals(15.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);

      // Live trees - these are size class first, then species
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(0);
      assertEquals(10.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(1);
      assertEquals(11.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(33.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(2);
      assertEquals(12.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(45.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      // Snags - these are size class first, then species
      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(0);
      assertEquals(0.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(1);
      assertEquals(25.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(2);
      assertEquals(40.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(50.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);


      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }
  
  /**
   * Tests reading live + snag initial densities.
   */
  public void testMixedTreeDensityReading2() {
    String sFunc = "testMixedTreeDensityReading2";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile4(), null);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // Both snag and live tree densities
      //----------------------------------------------------------------------/
      assertEquals( 0.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals( 5.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);

      // Live trees - these are size class first, then species
      assertEquals(oPopBeh.mp_fInitialDensities.getValue().size(), 5);
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(0);
      assertEquals(100.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(200.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(300.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(1);
      assertEquals(110.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(150.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(210.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(2);
      assertEquals(10.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(3);
      assertEquals(11.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(33.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(4);
      assertEquals(12.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(45.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      // Snags - these are size class first, then species
      assertEquals(oPopBeh.mp_fSnagInitialDensities.getValue().size(), 3);
      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(0);
      assertEquals(0.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(1);
      assertEquals(25.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(2);
      assertEquals(40.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(50.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);


      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }

  
  /**
   * Tests writing out live + snag initial densities and then reading them
   * back in.
   */
  public void testFileWritingMixed() {
    String sFunc = "testFileWriting";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile4(), null);
      
      // Write out the file and read it back in
      oManager.writeParameterFile("newfile.xml");
      oManager.inputXMLFile("newfile.xml", null);
      
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // Both snag and live tree densities
      //----------------------------------------------------------------------/
      assertEquals( 0.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals( 5.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);

      // Live trees - these are size class first, then species
      assertEquals(oPopBeh.mp_fInitialDensities.getValue().size(), 5);
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(0);
      assertEquals(100.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(200.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(300.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(1);
      assertEquals(110.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(150.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(210.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(2);
      assertEquals(10.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(3);
      assertEquals(11.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(33.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fInitialDensities.getValue().get(4);
      assertEquals(12.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(45.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);
      
      // Snags - these are size class first, then species
      assertEquals(oPopBeh.mp_fSnagInitialDensities.getValue().size(), 3);
      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(0);
      assertEquals(0.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(1);
      assertEquals(25.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(2);
      assertEquals(40.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(50.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);


      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }
  
  /**
   * Tests writing snag initial densities only.
   */
  public void testFileWritingSnagsOnly() {
    String sFunc = "testFileWritingSnagsOnly";
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile2(), null);
      
     // Write out the file and read it back in
      oManager.writeParameterFile("newfile.xml");
      oManager.inputXMLFile("newfile.xml", null);
      
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oPopBeh = oPop.getTreeBehavior();
      ModelVector oVals;

      //----------------------------------------------------------------------/
      // No live tree densities, just snag densities
      //----------------------------------------------------------------------/
      assertEquals(10.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oPopBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);

      // These are size class first, then species
      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(0);
      assertEquals(0.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(1);
      assertEquals(25.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      oVals = (ModelVector)oPopBeh.mp_fSnagInitialDensities.getValue().get(2);
      assertEquals(40.0, ((Float) oVals.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float) oVals.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(50.0, ((Float) oVals.getValue().get(2)).floatValue(), 0.0001);

      System.out.println(sFunc + " test succeeded.");
    }
    catch (ModelException oErr) {
      fail(sFunc + " failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail(sFunc + " IO failure. Message: " + oErr.getMessage());
    }
  }


  /**
   * Writes a file with just tree initial densities.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFile1() throws IOException {
    String sFileName = "\\test1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><paramFile fileCode=\"07050101\"><plot><timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>");
    oOut.write("<tr_species speciesName=\"BEAL\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s1.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">10.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">11.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">12.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">20.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">22.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">24.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">30.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">33.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">45.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");

    writeCommonFileParts(oOut);

    oOut.close();
    return sFileName;    
  }
  
  /**
   * Writes a file with just snag initial densities.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFile2() throws IOException {
    String sFileName = "\\test1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><paramFile fileCode=\"07050101\"><plot><timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>");
    oOut.write("<tr_species speciesName=\"BEAL\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s15.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s35.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_snagInitialDensities>");
    oOut.write("<tr_sidVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s10.0\">0.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">25.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">40.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s10.0\">15.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">30.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">0.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s10.0\">20.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">35.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">50.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("</tr_snagInitialDensities>");

    writeCommonFileParts(oOut);

    oOut.close();
    return sFileName;    
  }
  
  /**
   * Writes a file with both tree and snag initial densities.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFile3() throws IOException {
    String sFileName = "\\test1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><paramFile fileCode=\"07050101\"><plot><timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>");
    oOut.write("<tr_species speciesName=\"BEAL\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s15.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s35.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">10.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">11.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">12.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">20.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">22.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">24.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">30.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">33.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">45.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_snagInitialDensities>");
    oOut.write("<tr_sidVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">0.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">25.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">40.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">15.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">30.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">0.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">20.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">35.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">50.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("</tr_snagInitialDensities>");

    writeCommonFileParts(oOut);

    oOut.close();
    return sFileName;    
  }
  
  
  /**
   * Writes a file with both tree and snag initial densities, but some of the
   * size classes are too small for the snags.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFile4() throws IOException {
    String sFileName = "\\test1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><paramFile fileCode=\"07050101\"><plot><timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>");
    oOut.write("<tr_species speciesName=\"BEAL\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"seedling\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s5.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s15.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s35.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_initialDensity sizeClass=\"seedling\">100.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">110.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">10.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">11.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">12.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_initialDensity sizeClass=\"seedling\">200.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">150.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">20.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">22.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">24.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_initialDensity sizeClass=\"seedling\">300.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">210.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">30.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">33.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">45.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_snagInitialDensities>");
    oOut.write("<tr_sidVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">0.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">25.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">40.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">15.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">30.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">0.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">20.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s20.0\">35.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">50.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("</tr_snagInitialDensities>");

    writeCommonFileParts(oOut);

    oOut.close();
    return sFileName;    
  }

  private void writeCommonFileParts(FileWriter oOut) throws IOException {
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"ACRU\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"ACSA\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"BEAL\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"ACRU\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"ACSA\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"BEAL\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Snag\"/>");      
    oOut.write("</behavior>");
    oOut.write("<behavior><behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>3.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"ACRU\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"ACSA\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"BEAL\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"ACRU\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"ACSA\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"BEAL\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"ACRU\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"ACSA\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"BEAL\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"ACRU\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"ACSA\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"BEAL\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"ACRU\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"ACSA\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"BEAL\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"ACRU\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"ACSA\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"BEAL\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"ACRU\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"ACSA\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"BEAL\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"ACRU\">25.7</tr_chVal>");
    oOut.write("<tr_chVal species=\"ACSA\">24.8</tr_chVal>");
    oOut.write("<tr_chVal species=\"BEAL\">23.2</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"ACRU\">0.108</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"ACSA\">0.107</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"BEAL\">0.109</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"ACRU\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"ACSA\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"BEAL\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdMaxCrownRad>");
    oOut.write("<tr_smcrVal species=\"ACRU\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"ACSA\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"BEAL\">10.0</tr_smcrVal>");
    oOut.write("</tr_stdMaxCrownRad>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"ACRU\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"ACSA\">0.58</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"BEAL\">0.54</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"ACRU\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"ACSA\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"BEAL\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"ACRU\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"ACSA\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"BEAL\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"ACRU\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"ACSA\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"BEAL\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"ACRU\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"ACSA\">0.062333334</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"BEAL\">0.063</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"ACRU\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"ACSA\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"BEAL\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<StochasticMortality1>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"ACRU\">0.01</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"ACSA\">0.01</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"BEAL\">0.01</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality1>");
    oOut.write("<RemoveDead2></RemoveDead2>");
    oOut.write("</paramFile>");
  }
}
