package sortie.data.funcgroups;

import java.io.File;
import java.io.FileWriter;

import sortie.ModelTestCase;
import sortie.data.funcgroups.light.QuadratGLILight;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

public class TestBehavior extends ModelTestCase {

  /**
   * Tests change of species
   */
  public void testChangeOfSpecies() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      //Old number of species
      int iOldNumberOfSpecies = 4;

      //New species indexer
      int[] p_oIndexer = new int[5];
      p_oIndexer[0] = -1; //First species in new list is new
      p_oIndexer[1] = 1; //Second species is the same from old to new
      p_oIndexer[2] = 3; //Third species in new list is same as fourth in old
      p_oIndexer[3] = 0; //Fourth species in new list is same as first in old
      p_oIndexer[4] = -1; //Fifth species in new list is old

      //Should not get touched
      ModelVector p_oNonSpeciesSpecificTooShort = new ModelVector("Too short",
          "", "", 0, ModelVector.FLOAT);
      //Should not get touched - right length but not allowed
      ModelVector p_oNonSpeciesSpecific = new ModelVector("Too long", "", "", 0,
          ModelVector.STRING);
      //Species-specific set of strings
      ModelVector p_oStringSpeciesSpecific = new ModelVector("String", "", "", 0,
          ModelVector.STRING);
      //Species-specific set of floats
      ModelVector p_oFloatSpeciesSpecific = new ModelVector("Float", "", "", 0,
          ModelVector.FLOAT);
      //Species-specific set of ModelEnums
      ModelVector p_oModelEnumSpeciesSpecific = new ModelVector("ModelEnum", "", "", 0,
          ModelVector.MODEL_ENUM);
      //Species-specific set of integers
      ModelVector p_oIntegerSpeciesSpecific = new ModelVector("ModelInteger", "", "", 0,
          ModelVector.INTEGER);

      p_oNonSpeciesSpecificTooShort.setIsSpeciesSpecific(false);
      p_oNonSpeciesSpecific.setIsSpeciesSpecific(false);

      //Give all the vectors data
      p_oNonSpeciesSpecificTooShort.getValue().add(Float.valueOf(1));
      p_oNonSpeciesSpecificTooShort.getValue().add(Float.valueOf(2));

      p_oNonSpeciesSpecific.getValue().add("test 1");
      p_oNonSpeciesSpecific.getValue().add("test 2");
      p_oNonSpeciesSpecific.getValue().add("test 3");
      p_oNonSpeciesSpecific.getValue().add("test 4");

      p_oStringSpeciesSpecific.getValue().add("species 0");
      p_oStringSpeciesSpecific.getValue().add("species 1");
      p_oStringSpeciesSpecific.getValue().add("species 2");
      p_oStringSpeciesSpecific.getValue().add("species 3");

      p_oFloatSpeciesSpecific.getValue().add(Float.valueOf(0));
      p_oFloatSpeciesSpecific.getValue().add(Float.valueOf(1));
      p_oFloatSpeciesSpecific.getValue().add(Float.valueOf(2));
      p_oFloatSpeciesSpecific.getValue().add(Float.valueOf(3));

      p_oModelEnumSpeciesSpecific.getValue().add(new ModelEnum(null, null,
          "enum 0", ""));
      p_oModelEnumSpeciesSpecific.getValue().add(new ModelEnum(null, null,
          "enum 1", ""));
      p_oModelEnumSpeciesSpecific.getValue().add(new ModelEnum(null, null,
          "enum 2", ""));
      p_oModelEnumSpeciesSpecific.getValue().add(new ModelEnum(null, null,
          "enum 3", ""));

      p_oIntegerSpeciesSpecific.getValue().add(Integer.valueOf(0));
      p_oIntegerSpeciesSpecific.getValue().add(Integer.valueOf(1));
      p_oIntegerSpeciesSpecific.getValue().add(Integer.valueOf(2));
      p_oIntegerSpeciesSpecific.getValue().add(Integer.valueOf(3));

      //Put the data in the test object
      BehaviorTester oTest = new BehaviorTester(oManager);
      //ModelInt - should be ignored
      oTest.mp_oAllData.add(new ModelInt(1, "test", "test"));
      oTest.mp_oAllData.add(p_oIntegerSpeciesSpecific);
      oTest.mp_oAllData.add(p_oNonSpeciesSpecificTooShort);
      oTest.mp_oAllData.add(p_oStringSpeciesSpecific);
      oTest.mp_oAllData.add(p_oNonSpeciesSpecific);
      oTest.mp_oAllData.add(p_oModelEnumSpeciesSpecific);
      oTest.mp_oAllData.add(p_oFloatSpeciesSpecific);

      oTest.changeOfSpecies(iOldNumberOfSpecies, p_oIndexer, new String[] {"1", "2", "3", "4", "5"});

      //Validate each one
      assertEquals(2, p_oNonSpeciesSpecificTooShort.getValue().size());
      Float fTest = (Float) p_oNonSpeciesSpecificTooShort.getValue().get(
          0);
      assertEquals(Float.valueOf(1), fTest);
      fTest = (Float) p_oNonSpeciesSpecificTooShort.getValue().get(1);
      assertEquals(Float.valueOf(2), fTest);

      assertEquals(4, p_oNonSpeciesSpecific.getValue().size());
      String sTest = (String) p_oNonSpeciesSpecific.getValue().get(0);
      assertEquals("test 1", sTest);
      sTest = (String) p_oNonSpeciesSpecific.getValue().get(1);
      assertEquals("test 2", sTest);
      sTest = (String) p_oNonSpeciesSpecific.getValue().get(2);
      assertEquals("test 3", sTest);
      sTest = (String) p_oNonSpeciesSpecific.getValue().get(3);
      assertEquals("test 4", sTest);

      assertEquals(5, p_oStringSpeciesSpecific.getValue().size());
      sTest = (String) p_oStringSpeciesSpecific.getValue().get(0);
      assertEquals(null, sTest);
      sTest = (String) p_oStringSpeciesSpecific.getValue().get(1);
      assertEquals("species 1", sTest);
      sTest = (String) p_oStringSpeciesSpecific.getValue().get(2);
      assertEquals("species 3", sTest);
      sTest = (String) p_oStringSpeciesSpecific.getValue().get(3);
      assertEquals("species 0", sTest);
      sTest = (String) p_oStringSpeciesSpecific.getValue().get(4);
      assertEquals(null, sTest);

      assertEquals(5, p_oFloatSpeciesSpecific.getValue().size());
      fTest = (Float) p_oFloatSpeciesSpecific.getValue().get(0);
      assertEquals(null, fTest);
      fTest = (Float) p_oFloatSpeciesSpecific.getValue().get(1);
      assertEquals(Float.valueOf(1), fTest);
      fTest = (Float) p_oFloatSpeciesSpecific.getValue().get(2);
      assertEquals(Float.valueOf(3), fTest);
      fTest = (Float) p_oFloatSpeciesSpecific.getValue().get(3);
      assertEquals(Float.valueOf(0), fTest);
      fTest = (Float) p_oFloatSpeciesSpecific.getValue().get(4);
      assertEquals(null, fTest);

      assertEquals(5, p_oModelEnumSpeciesSpecific.getValue().size());
      ModelEnum oEnumTest = (ModelEnum) p_oModelEnumSpeciesSpecific.getValue().
          get(0);
      assertEquals(null, fTest);
      oEnumTest = (ModelEnum) p_oModelEnumSpeciesSpecific.getValue().get(
          1);
      assertTrue(oEnumTest.equals(new ModelEnum(null, null, "enum 1", "")));
      oEnumTest = (ModelEnum) p_oModelEnumSpeciesSpecific.getValue().get(
          2);
      assertTrue(oEnumTest.equals(new ModelEnum(null, null, "enum 3", "")));
      oEnumTest = (ModelEnum) p_oModelEnumSpeciesSpecific.getValue().get(
          3);
      assertTrue(oEnumTest.equals(new ModelEnum(null, null, "enum 0", "")));
      oEnumTest = (ModelEnum) p_oModelEnumSpeciesSpecific.getValue().get(
          4);
      assertEquals(null, fTest);

      assertEquals(5, p_oIntegerSpeciesSpecific.getValue().size());
      Integer iTest = (Integer) p_oIntegerSpeciesSpecific.getValue().get(
          0);
      assertEquals(null, iTest);
      iTest = (Integer) p_oIntegerSpeciesSpecific.getValue().get(1);
      assertEquals(Integer.valueOf(1), iTest);
      iTest = (Integer) p_oIntegerSpeciesSpecific.getValue().get(2);
      assertEquals(Integer.valueOf(3), iTest);
      iTest = (Integer) p_oIntegerSpeciesSpecific.getValue().get(3);
      assertEquals(Integer.valueOf(0), iTest);
      iTest = (Integer) p_oIntegerSpeciesSpecific.getValue().get(4);
      assertEquals(null, iTest);

      ////////////////////////////////////////////
      // Testing with some real data
      ////////////////////////////////////////////
      sFileName = null;
      try {
        oManager = new GUIManager(null);
        sFileName = writeXMLFile1();
        oManager.inputXMLParameterFile(sFileName);

        String[] sNewSpecies = new String[] {
            "Test 1",
            "TSCA",
            "QURU",
            "PRSE",
            "PIST",
            "Test 2",
            "FRAM",
            "FAGR",
            "BEAL",
            "ACSA",
            "ACRU"
        };
        oManager.getTreePopulation().setSpeciesNames(sNewSpecies);

        LightBehaviors oLightBeh = oManager.getLightBehaviors();
        QuadratGLILight oLight = (QuadratGLILight)oLightBeh.getBehaviorByParameterFileTag("QuadratLight").get(0);
        ModelVector p_oLEC = oLight.getLightTransmissionCoefficient();
        assertEquals(11, p_oLEC.getValue().size());
        //Test 1
        fTest = (Float) p_oLEC.getValue().get(0);
        assertEquals(null, fTest);
        //TSCA
        fTest = (Float) p_oLEC.getValue().get(1);
        assertEquals(Float.valueOf((float)0.5), fTest);
        //QURU
        fTest = (Float) p_oLEC.getValue().get(2);
        assertEquals(Float.valueOf((float)0.9), fTest);
        //PRSE
        fTest = (Float) p_oLEC.getValue().get(3);
        assertEquals(Float.valueOf((float)0.8), fTest);
        //PIST
        fTest = (Float) p_oLEC.getValue().get(4);
        assertEquals(Float.valueOf((float)0.7), fTest);
        //Test 2
        fTest = (Float) p_oLEC.getValue().get(5);
        assertEquals(null, fTest);
        //FRAM
        fTest = (Float) p_oLEC.getValue().get(6);
        assertEquals(Float.valueOf((float)0.6), fTest);
        //FAGR
        fTest = (Float) p_oLEC.getValue().get(7);
        assertEquals(Float.valueOf((float)0.4), fTest);
        //BEAL
        fTest = (Float) p_oLEC.getValue().get(8);
        assertEquals(Float.valueOf((float)0.3), fTest);
        //ACSA
        fTest = (Float) p_oLEC.getValue().get(9);
        assertEquals(Float.valueOf((float)0.2), fTest);
        //ACRU
        fTest = (Float) p_oLEC.getValue().get(10);
        assertEquals(Float.valueOf((float)0.1), fTest);

      } catch (ModelException oErr) {
        fail("ChangeOfSpecies testing failed with message " + oErr.getMessage());
      } finally {
        new File(sFileName).delete();
      }


      System.out.println("ChangeOfSpecies testing succeeded.");
    } catch (ModelException oErr) {
      fail("ChangeOfSpecies testing failed with message " + oErr.getMessage());
    }
  }


  /**
   * Tests copy species
   */
  public void testCopySpecies() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile1();
      Float fTest;
      oManager.inputXMLParameterFile(sFileName);

      //Add a new species at the end
      String[] sNewSpecies = new String[] {
          "ACRU",
          "ACSA",
          "BEAL",
          "FAGR",
          "TSCA",
          "FRAM",
          "PIST",
          "PRSE",
          "QURU",
      "Test 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      //Copy BEAL to the last one
      oManager.getTreePopulation().doCopySpecies("BEAL", "Test_1");

      //Test a float value
      LightBehaviors oLightBeh = oManager.getLightBehaviors();
      QuadratGLILight oLight = (QuadratGLILight)oLightBeh.getBehaviorByParameterFileTag("QuadratLight").get(0);
      ModelVector p_oLEC = oLight.getLightTransmissionCoefficient();
      assertEquals(10, p_oLEC.getValue().size());
      //ACRU
      fTest = (Float) p_oLEC.getValue().get(0);
      assertEquals(Float.valueOf((float)0.1), fTest);
      //ACSA
      fTest = (Float) p_oLEC.getValue().get(1);
      assertEquals(Float.valueOf((float)0.2), fTest);
      //BEAL
      fTest = (Float) p_oLEC.getValue().get(2);
      assertEquals(Float.valueOf((float)0.3), fTest);
      //FAGR
      fTest = (Float) p_oLEC.getValue().get(3);
      assertEquals(Float.valueOf((float)0.4), fTest);
      //TSCA
      fTest = (Float) p_oLEC.getValue().get(4);
      assertEquals(Float.valueOf((float)0.5), fTest);
      //FRAM
      fTest = (Float) p_oLEC.getValue().get(5);
      assertEquals(Float.valueOf((float)0.6), fTest);
      //PIST
      fTest = (Float) p_oLEC.getValue().get(6);
      assertEquals(Float.valueOf((float)0.7), fTest);
      //PRSE
      fTest = (Float) p_oLEC.getValue().get(7);
      assertEquals(Float.valueOf((float)0.8), fTest);
      //QURU
      fTest = (Float) p_oLEC.getValue().get(8);
      assertEquals(Float.valueOf((float)0.9), fTest);
      //Test 1
      fTest = (Float) p_oLEC.getValue().get(9);
      assertEquals(Float.valueOf((float)0.3), fTest);

      //Test a ModelEnum
      Allometry oAllom = oManager.getTreePopulation().getAllometry();
      ModelVector p_oWhatAHD = oAllom.mp_iWhatAdultHDFunction;
      ModelEnum oEnum;
      assertEquals(10, p_oWhatAHD.getValue().size());
      //ACRU
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      //ACSA
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(1);
      assertEquals(0, oEnum.getValue());
      //BEAL
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(2);
      assertEquals(1, oEnum.getValue());
      //FAGR
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      //TSCA
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(4);
      assertEquals(0, oEnum.getValue());
      //FRAM
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(5);
      assertEquals(0, oEnum.getValue());
      //PIST
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(6);
      assertEquals(0, oEnum.getValue());
      //PRSE
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(7);
      assertEquals(0, oEnum.getValue());
      //QURU
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(8);
      assertEquals(0, oEnum.getValue());
      //Test 1
      oEnum = (ModelEnum) p_oWhatAHD.getValue().get(9);
      assertEquals(1, oEnum.getValue());



      System.out.println("CopySpecies testing succeeded.");
    } catch (ModelException oErr) {
      fail("ChangeOfSpecies testing failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * This writes an XML file to test species changing.  This file contains
   * light information.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>400</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>500.0</plot_lenX>");
      oOut.write("<plot_lenY>500.0</plot_lenY>");
      oOut.write("<plot_latitude>41.92</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"BEAL\"/>");
      oOut.write("<tr_species speciesName=\"FAGR\"/>");
      oOut.write("<tr_species speciesName=\"TSCA\"/>");
      oOut.write("<tr_species speciesName=\"FRAM\"/>");
      oOut.write("<tr_species speciesName=\"PIST\"/>");
      oOut.write("<tr_species speciesName=\"PRSE\"/>");
      oOut.write("<tr_species speciesName=\"QURU\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"FAGR\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"TSCA\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"FRAM\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"PIST\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"PRSE\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"QURU\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"PIST\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"PRSE\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"QURU\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ACRU\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"ACSA\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"BEAL\">1</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"FAGR\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"TSCA\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"FRAM\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"PIST\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"PRSE\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"QURU\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("</allometry>");
      oOut.write("<GeneralLight>");
      oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
      oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
      oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
      oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
      oOut.write("<li_lightExtinctionCoefficient>");
      oOut.write("<li_lecVal species=\"ACRU\">0.1</li_lecVal>");
      oOut.write("<li_lecVal species=\"ACSA\">0.2</li_lecVal>");
      oOut.write("<li_lecVal species=\"BEAL\">0.3</li_lecVal>");
      oOut.write("<li_lecVal species=\"FAGR\">0.4</li_lecVal>");
      oOut.write("<li_lecVal species=\"TSCA\">0.5</li_lecVal>");
      oOut.write("<li_lecVal species=\"FRAM\">0.6</li_lecVal>");
      oOut.write("<li_lecVal species=\"PIST\">0.7</li_lecVal>");
      oOut.write("<li_lecVal species=\"PRSE\">0.8</li_lecVal>");
      oOut.write("<li_lecVal species=\"QURU\">0.9</li_lecVal>");
      oOut.write("</li_lightExtinctionCoefficient>");
      oOut.write("<li_snagAgeClass1>0</li_snagAgeClass1>");
      oOut.write("<li_snagAgeClass2>0</li_snagAgeClass2>");
      oOut.write("</GeneralLight>");
      oOut.write("<QuadratLight1>");
      oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
      oOut.write("<li_numAltGrids>12</li_numAltGrids>");
      oOut.write("<li_numAziGrids>18</li_numAziGrids>");
      oOut.write("</QuadratLight1>");
      oOut.write("<ConstRadialGrowth2>");
      oOut.write("<gr_adultConstRadialInc>");
      oOut.write("<gr_acriVal species=\"PIST\">1.14</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"PRSE\">2.47</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"QURU\">0.72</gr_acriVal>");
      oOut.write("</gr_adultConstRadialInc>");
      oOut.write("</ConstRadialGrowth2>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test species changing.  This file contains
   * light information.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile2() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>400</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>500.0</plot_lenX>");
      oOut.write("<plot_lenY>500.0</plot_lenY>");
      oOut.write("<plot_latitude>41.92</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"BEAL\"/>");
      oOut.write("<tr_species speciesName=\"FAGR\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"FAGR\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ACRU\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"ACSA\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"BEAL\">1</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"FAGR\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("</allometry>");
      oOut.write("<GeneralLight>");
      oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
      oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
      oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
      oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
      oOut.write("<li_lightExtinctionCoefficient>");
      oOut.write("<li_lecVal species=\"ACRU\">0.1</li_lecVal>");
      oOut.write("<li_lecVal species=\"ACSA\">0.2</li_lecVal>");
      oOut.write("<li_lecVal species=\"BEAL\">0.3</li_lecVal>");
      oOut.write("<li_lecVal species=\"FAGR\">0.4</li_lecVal>");
      oOut.write("</li_lightExtinctionCoefficient>");
      oOut.write("<li_snagAgeClass1>0</li_snagAgeClass1>");
      oOut.write("<li_snagAgeClass2>0</li_snagAgeClass2>");
      oOut.write("</GeneralLight>");
      oOut.write("<QuadratLight1>");
      oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
      oOut.write("<li_numAltGrids>12</li_numAltGrids>");
      oOut.write("<li_numAziGrids>18</li_numAziGrids>");
      oOut.write("</QuadratLight1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}

/**
 * Test class for Behavior.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class BehaviorTester
extends Behavior {

  /**
   * Constructor.
   */
  BehaviorTester(GUIManager oManager) {
    super(oManager, null, "", "", "", "");
  }

  /**
   * Overridden abstract function
   * @param oPop Not used
   * @throws ModelException never
   */
  public void validateData(TreePopulation oPop) throws ModelException {};

  /**
   * Overridden abstract function
   * @param oPop Not used
   * @param out Not used
   * @throws ModelException never
   */
  public void WriteXML(java.io.FileWriter out, TreePopulation oPop) throws
  ModelException {};
}
