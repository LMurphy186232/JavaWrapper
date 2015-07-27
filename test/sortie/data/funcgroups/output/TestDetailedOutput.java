package sortie.data.funcgroups.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.Subplot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.DetailedTreeSettings;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

public class TestDetailedOutput extends ModelTestCase {
  
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
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ArrayList<Behavior> p_oBehs = oOb.getBehaviorByParameterFileTag("Output");
      assertEquals(1, p_oBehs.size());
      DetailedOutput oOutput = oOb.getDetailedOutput();
      DetailedTreeSettings oTreeSettings;
      DetailedGridSettings oGridSettings;
      assertEquals(2, oOutput.getNumberOfDetailedGridSettings());
      assertEquals(9, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(1, oOutput.getNumberOfDetailedDeadTreeSettings());
      
      assertEquals("testoutput.gz.tar", oOutput.m_sDetailedOutputFilename.getValue());
      
      int i = 0;
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(5, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(3).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(4).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals(2, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals(3, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(5, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(3).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(4).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(5, oTreeSettings.getNumberOfFloats());
      assertEquals(0, oTreeSettings.getNumberOfInts());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("Diam10", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(3).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(4).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(i);
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(1, oTreeSettings.getNumberOfInts());
      assertEquals("Tree Age", oTreeSettings.getInt(0).getCodeName());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      i++;
      
      oTreeSettings = oOutput.getDetailedDeadTreeSetting(0);
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals(OutputBehaviors.NATURAL, oTreeSettings.getDeadCode());
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getNumberOfBools());
      assertEquals(0, oTreeSettings.getNumberOfChars());
      assertEquals(4, oTreeSettings.getNumberOfFloats());
      assertEquals(1, oTreeSettings.getNumberOfInts());
      assertEquals("Tree Age", oTreeSettings.getInt(0).getCodeName());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());
      assertEquals("Y", oTreeSettings.getFloat(1).getCodeName());
      assertEquals("DBH", oTreeSettings.getFloat(2).getCodeName());
      assertEquals("Height", oTreeSettings.getFloat(3).getCodeName());
      
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getSaveFrequency());
      assertEquals(0, oGridSettings.getNumberOfBools());
      assertEquals(0, oGridSettings.getNumberOfChars());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals(0, oGridSettings.getNumberOfInts());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(2, oGridSettings.getSaveFrequency());
      assertEquals(0, oGridSettings.getNumberOfBools());
      assertEquals(0, oGridSettings.getNumberOfChars());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals(0, oGridSettings.getNumberOfInts());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals(0, oGridSettings.getNumberOfPackageBools());
      assertEquals(0, oGridSettings.getNumberOfPackageChars());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      
      assertEquals(1, oOutput.getNumberOfDetailedSubplots());
      Subplot oSubplot = oOutput.getDetailedSubplot(0);
      assertEquals("Sub1", oSubplot.getSubplotName());
      assertEquals(5, oSubplot.getNumberOfCells());
      assertEquals(1, oSubplot.getCell(0).getX()); assertEquals(2, oSubplot.getCell(0).getY());
      assertEquals(2, oSubplot.getCell(1).getX()); assertEquals(3, oSubplot.getCell(1).getY());
      assertEquals(3, oSubplot.getCell(2).getX()); assertEquals(4, oSubplot.getCell(2).getY());
      assertEquals(4, oSubplot.getCell(3).getX()); assertEquals(5, oSubplot.getCell(3).getY());
      assertEquals(5, oSubplot.getCell(4).getX()); assertEquals(6, oSubplot.getCell(4).getY());
      
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

  /**
   * Tests doSetup.
   * @throws Exception if there is a problem doing setup.
   */
  /*public void testDoSetUp() throws Exception {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      oOutputBehaviors.doSetup(oManager.getTreePopulation());
      DetailedOutput oOutput = oOutputBehaviors.getDetailedOutput();
      assertEquals(0, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(0, oOutput.getNumberOfDetailedDeadTreeSettings());
      assertEquals(0, oOutput.getNumberOfDetailedGridSettings()); 
    }
    catch (ModelException oErr) {
      throw new Exception(oErr.getMessage());
    }    
  }*/

  /**
   * Tests the AddDetailedGridSettings method.
   */
  public void testAddDetailedGridSettings() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOutputBehaviors.getDetailedOutput();
      DetailedGridSettings oSettings = null, oTest;

      oOutput.addDetailedGridSettings(oSettings);

      //Normal processing

      //First added
      oSettings = new DetailedGridSettings("Grid 1");
      oSettings.addChar("Char1", "Char1");
      oSettings.addInt("Integer 1", "Integer 1");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oSettings);
      assertEquals(1, oOutput.getNumberOfDetailedGridSettings());
      oTest = oOutput.getDetailedGridSetting(0);
      assertTrue(testEquals(oSettings, oTest));

      //Second added
      oSettings = null;
      oSettings = new DetailedGridSettings("Grid 2");
      oSettings.addChar("Char1", "Char1");
      oSettings.addInt("Integer 1", "Integer 1");
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OtherFloat", "OtherFloat");
      oSettings.addFloat("TwoFloat", "TwoFloat");
      oSettings.addChar("OneChar", "OneChar");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oSettings);
      assertEquals(2, oOutput.getNumberOfDetailedGridSettings());
      oTest = oOutput.getDetailedGridSetting(1);
      assertTrue(testEquals(oSettings, oTest));
      assertFalse(testEquals(oSettings, oOutput.getDetailedGridSetting(0)));

      //Same type and species as first - should replace
      oSettings = null;
      oSettings = new DetailedGridSettings("Grid 1");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OtherFloat", "OtherFloat");
      oSettings.setSaveFrequency(0);
      oOutput.addDetailedGridSettings(oSettings);
      assertEquals(2, oOutput.getNumberOfDetailedGridSettings());
      oTest = oOutput.getDetailedGridSetting(1);
      assertTrue(testEquals(oSettings, oTest));
    } catch (ModelException oErr) {
      fail("Output validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    System.out.println(
    "Normal processing succeeded for AddDetailedGridSettings.");
  }

  /**
   * Tests the AddDetailedTreeSettings method.
   */
  public void testAddDetailedTreeSettings() {
    DetailedTreeSettings oSettings = null, oTest;
    DetailedOutput oOutput = null;
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      oOutput = oOutputBehaviors.getDetailedOutput();      
    
      oOutput.addDetailedTreeSettings(oSettings);

      //Normal processing

      //First added
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(1, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(0, oOutput.getNumberOfDetailedDeadTreeSettings());
      oTest = oOutput.getDetailedLiveTreeSetting(0);
      assertTrue(testEquals(oSettings, oTest));

      //No settings - should not be added
      /*      oSettings = null;
            oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1);
            oSettings.SetSaveFrequency(1);
            mp_oOutputBehaviors.AddDetailedTreeSettings(oSettings);
            mp_oOutputBehaviors.AddDetailedTreeSettings(oSettings);
       assertEquals(1, mp_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings());
            oSettings = mp_oOutputBehaviors.getDetailedLiveTreeSetting(0);
            assertTrue(testEquals(oSettings, oTest));*/

      //Second added - specify NOTDEAD
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1, OutputBehaviors.NOTDEAD);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("TwoFloat", "TwoFloat");
      oSettings.addChar("OneChar", "OneChar");
      oSettings.setSaveFrequency(0);
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(2, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(0, oOutput.getNumberOfDetailedDeadTreeSettings());
      oTest = oOutput.getDetailedLiveTreeSetting(1);
      assertTrue(testEquals(oSettings, oTest));
      
      //Same type and species as second but dead - should not replace
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1, OutputBehaviors.NATURAL);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("TwoFloat", "TwoFloat");
      oSettings.addChar("OneChar", "OneChar");
      oSettings.setSaveFrequency(0);
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(2, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(1, oOutput.getNumberOfDetailedDeadTreeSettings());
      oTest = oOutput.getDetailedDeadTreeSetting(0);
      assertTrue(testEquals(oSettings, oTest));

      //Same type and species as second live - should replace
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(2, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(1, oOutput.getNumberOfDetailedDeadTreeSettings());
      oSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertFalse(testEquals(oSettings, oTest)); //oTest is still from before

      //Same type but not same species - should add
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 0);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(3, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(1, oOutput.getNumberOfDetailedDeadTreeSettings());
      
      //Same species but not same type - should add
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 1);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(4, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(1, oOutput.getNumberOfDetailedDeadTreeSettings());
      
      //Dead: Same type and species but different dead code - should add 
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1, OutputBehaviors.HARVEST);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("TwoFloat", "TwoFloat");
      oSettings.addChar("OneChar", "OneChar");
      oSettings.setSaveFrequency(0);
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(4, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(2, oOutput.getNumberOfDetailedDeadTreeSettings());
      oTest = oOutput.getDetailedDeadTreeSetting(1);
      assertTrue(testEquals(oSettings, oTest));
      
      //Dead: Same type and species and dead code as first - should replace 
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 1, OutputBehaviors.NATURAL);
      oSettings.addBool("DeadBool", "OneBool");
      oSettings.addFloat("OneFloat", "DeadFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("TwoFloat", "TwoFloat");
      oSettings.addChar("OneChar", "OneChar");
      oSettings.setSaveFrequency(0);
      oOutput.addDetailedTreeSettings(oSettings);
      assertEquals(4, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(2, oOutput.getNumberOfDetailedDeadTreeSettings());
      oTest = oOutput.getDetailedDeadTreeSetting(1);
      assertTrue(testEquals(oSettings, oTest));
    }
    catch (ModelException oErr) {
      fail("Normal processing failed for AddDetailedTreeSettings with message " +
           oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    System.out.println(
        "Normal processing succeeded for AddDetailedTreeSettings.");

    //Exception processing

    //Invalid species
    try {
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 5);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      fail("AddDetailedTreeSettings failed to catch invalid species");
    }
    catch (ModelException oErr) {
      ;
    }

    //Invalid type
    try {
      oSettings = null;
      oSettings = new DetailedTreeSettings(17, 0);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      fail("AddDetailedTreeSettings failed to catch invalid type");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Invalid dead code
    try {
      oSettings = null;
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, 0, 10);
      oSettings.addBool("OneBool", "OneBool");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("OneFloat", "OneFloat");
      oSettings.addFloat("DifferentFloat", "DifferentFloat");
      oSettings.addChar("OneChar", "OneChar");
      oOutput.addDetailedTreeSettings(oSettings);
      fail("AddDetailedTreeSettings failed to catch invalid dead code");
    }
    catch (ModelException oErr) {
      ;
    }
    System.out.println(
        "Exception processing succeeded for AddDetailedTreeSettings.");
  }
  
  
  /**
   * Tests the ClearDetailedGridSettings method.
   */
  public void testClearDetailedGridSettings() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOutputBehaviors.getDetailedOutput();      
      
      oOutput.clearDetailedGridSettings();
      assertEquals(0, oOutput.getNumberOfDetailedGridSettings());      

      //Make sure only grids are cleared, not trees
      DetailedTreeSettings oTreeSettings = new DetailedTreeSettings(
          TreePopulation.ADULT, 0);
      oTreeSettings.addBool("OneBool", "OneBool");
      oTreeSettings.addFloat("OneFloat", "OneFloat");
      oTreeSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oTreeSettings);

      DetailedGridSettings oGridSettings = new DetailedGridSettings("Grid 1");
      oGridSettings.addBool("OneBool", "OneBool");
      oGridSettings.addFloat("OneFloat", "OneFloat");
      oGridSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oGridSettings);

      oOutput.clearDetailedGridSettings();
      assertEquals(0, oOutput.getNumberOfDetailedGridSettings());
      assertTrue(oOutput.getNumberOfDetailedLiveTreeSettings() > 0);
    
    } catch (ModelException oErr) {
      fail("ClearDetailedGridSettings failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Tests the ClearDetailedLiveTreeSettings method.
   */
  public void testClearDetailedLiveTreeSettings() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOutputBehaviors.getDetailedOutput();
      
      oOutput.clearDetailedLiveTreeSettings();
      assertEquals(0, oOutput.getNumberOfDetailedLiveTreeSettings());
      
      //Make sure only live trees are cleared, not dead trees or grids
      DetailedTreeSettings oTreeSettings = new DetailedTreeSettings(
          TreePopulation.ADULT, 0);
      oTreeSettings.addBool("OneBool", "OneBool");
      oTreeSettings.addFloat("OneFloat", "OneFloat");
      oTreeSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oTreeSettings);
      
      oTreeSettings = new DetailedTreeSettings(
          TreePopulation.ADULT, 0, OutputBehaviors.DISEASE);
      oTreeSettings.addBool("OneBool", "OneBool");
      oTreeSettings.addFloat("OneFloat", "OneFloat");
      oTreeSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oTreeSettings);
      
      DetailedGridSettings oGridSettings = new DetailedGridSettings("Grid 1");
      oGridSettings.addBool("OneBool", "OneBool");
      oGridSettings.addFloat("OneFloat", "OneFloat");
      oGridSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oGridSettings);

      oOutput.clearDetailedLiveTreeSettings();
      assertEquals(0, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertTrue(oOutput.getNumberOfDetailedGridSettings() > 0);
      assertTrue(oOutput.getNumberOfDetailedDeadTreeSettings() > 0);
    
    } catch (ModelException oErr) {
      fail("ClearDetailedLiveTreeSettings failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests the ClearDetailedDeadTreeSettings method.
   */
  public void testClearDetailedDeadTreeSettings() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOutputBehaviors.getDetailedOutput();
      
      oOutput.clearDetailedDeadTreeSettings();
      assertEquals(0, oOutput.getNumberOfDetailedDeadTreeSettings());

      //Make sure only dead trees are cleared, not live trees or grids
      DetailedTreeSettings oTreeSettings = new DetailedTreeSettings(
          TreePopulation.ADULT, 0);
      oTreeSettings.addBool("OneBool", "OneBool");
      oTreeSettings.addFloat("OneFloat", "OneFloat");
      oTreeSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oTreeSettings);
      
      oTreeSettings = new DetailedTreeSettings(
          TreePopulation.ADULT, 0, OutputBehaviors.DISEASE);
      oTreeSettings.addBool("OneBool", "OneBool");
      oTreeSettings.addFloat("OneFloat", "OneFloat");
      oTreeSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oTreeSettings);
      
      DetailedGridSettings oGridSettings = new DetailedGridSettings("Grid 1");
      oGridSettings.addBool("OneBool", "OneBool");
      oGridSettings.addFloat("OneFloat", "OneFloat");
      oGridSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oGridSettings);

      oOutput.clearDetailedDeadTreeSettings();
      assertEquals(0, oOutput.getNumberOfDetailedDeadTreeSettings());
      assertTrue(oOutput.getNumberOfDetailedGridSettings() > 0);
      assertTrue(oOutput.getNumberOfDetailedLiveTreeSettings() > 0);
    
    } catch (ModelException oErr) {
      fail("ClearDetailedDeadTreeSettings failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

  }

  /**
   * Tests reading of parameter file XML rundata settings. Unfortunately,
   * we can't do the grids this way anymore now that I validate data members
   * against existing grids. But we're doing at least some testing of grid
   * reading with testChangeOfSpecies.
   */
  public void testReadXMLDetailedOutputSettings() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      int i;
      //Force a new behavior group
      
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      DetailedTreeSettings oSettings;
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ArrayList<Behavior> p_oBehs = oOb.getBehaviorByParameterFileTag("Output");
      assertEquals(1, p_oBehs.size());
      DetailedOutput oOutput = oOb.getDetailedOutput();
      assertEquals(10, oOutput.getNumberOfDetailedGridSettings());
      assertEquals(6, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(7, oOutput.getNumberOfDetailedDeadTreeSettings());

      for (i = 0; i < oOutput.getNumberOfDetailedLiveTreeSettings(); i++) {
        oSettings = oOutput.getDetailedLiveTreeSetting(i);
        if (0 == oSettings.getSpecies()) {
          assertEquals(1, oSettings.getSaveFrequency());
          assertEquals(1, oSettings.getNumberOfBools());
          assertEquals(1, oSettings.getNumberOfChars());
          assertEquals(1, oSettings.getNumberOfFloats());
          assertEquals(1, oSettings.getNumberOfInts());
          assertEquals("species 1 test float 1",
                       oSettings.getFloat(0).getCodeName());
          assertEquals("species 1 test char 1",
                       oSettings.getChar(0).getCodeName());
          assertEquals("species 1 test int 1",
                       oSettings.getInt(0).getCodeName());
          assertEquals("species 1 test bool 1",
                       oSettings.getBool(0).getCodeName());

        }
        else if (1 == oSettings.getSpecies()) {

          assertEquals(2, oSettings.getSaveFrequency());
          assertEquals(2, oSettings.getNumberOfBools());
          assertEquals(2, oSettings.getNumberOfChars());
          assertEquals(2, oSettings.getNumberOfFloats());
          assertEquals(2, oSettings.getNumberOfInts());
          assertEquals("species 2 test float 1",
                       oSettings.getFloat(0).getCodeName());
          assertEquals("species 2 test char 1",
                       oSettings.getChar(0).getCodeName());
          assertEquals("species 2 test int 1",
                       oSettings.getInt(0).getCodeName());
          assertEquals("species 2 test bool 1",
                       oSettings.getBool(0).getCodeName());
          assertEquals("species 2 test float 2",
                       oSettings.getFloat(1).getCodeName());
          assertEquals("species 2 test char 2",
                       oSettings.getChar(1).getCodeName());
          assertEquals("species 2 test int 2",
                       oSettings.getInt(1).getCodeName());
          assertEquals("species 2 test bool 2",
                       oSettings.getBool(1).getCodeName());

        }
        else if (2 == oSettings.getSpecies()) {

          assertEquals(3, oSettings.getSaveFrequency());
          assertEquals(0, oSettings.getNumberOfBools());
          assertEquals(1, oSettings.getNumberOfChars());
          assertEquals(0, oSettings.getNumberOfFloats());
          assertEquals(0, oSettings.getNumberOfInts());
          assertEquals("species 3 test char 1",
                       oSettings.getChar(0).getCodeName());

        }
        else if (3 == oSettings.getSpecies()) {

          assertEquals(4, oSettings.getSaveFrequency());
          assertEquals(0, oSettings.getNumberOfBools());
          assertEquals(0, oSettings.getNumberOfChars());
          assertEquals(1, oSettings.getNumberOfFloats());
          assertEquals(0, oSettings.getNumberOfInts());
          assertEquals("species 4 test float 1",
                       oSettings.getFloat(0).getCodeName());

        }
        else if (4 == oSettings.getSpecies()) {

          assertEquals(5, oSettings.getSaveFrequency());
          assertEquals(1, oSettings.getNumberOfBools());
          assertEquals(0, oSettings.getNumberOfChars());
          assertEquals(0, oSettings.getNumberOfFloats());
          assertEquals(0, oSettings.getNumberOfInts());
          assertEquals("species 5 test bool 1",
                       oSettings.getBool(0).getCodeName());

        }
        else if (5 == oSettings.getSpecies()) {

          assertEquals(6, oSettings.getSaveFrequency());
          assertEquals(0, oSettings.getNumberOfBools());
          assertEquals(0, oSettings.getNumberOfChars());
          assertEquals(0, oSettings.getNumberOfFloats());
          assertEquals(1, oSettings.getNumberOfInts());
          assertEquals("species 6 test int 1",
                       oSettings.getInt(0).getCodeName());

        }
        else {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                   "Unrecognized species in tree."));
        }
      }
      
      oSettings = oOutput.getDetailedDeadTreeSetting(0);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.HARVEST, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.HARVEST, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.HARVEST, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.HARVEST, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.HARVEST, oSettings.getBool(0).getCodeName());
      
      //Natural
      oSettings = oOutput.getDetailedDeadTreeSetting(1);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.NATURAL, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.NATURAL, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.NATURAL, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.NATURAL, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.NATURAL, oSettings.getBool(0).getCodeName());
      
      //Insects
      oSettings = oOutput.getDetailedDeadTreeSetting(2);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.INSECTS, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.INSECTS, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.INSECTS, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.INSECTS, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.INSECTS, oSettings.getBool(0).getCodeName());
      
      //Disease
      oSettings = oOutput.getDetailedDeadTreeSetting(3);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.DISEASE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.DISEASE, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.DISEASE, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.DISEASE, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.DISEASE, oSettings.getBool(0).getCodeName());
      
      //Fire
      oSettings = oOutput.getDetailedDeadTreeSetting(4);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.FIRE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.FIRE, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.FIRE, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.FIRE, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.FIRE, oSettings.getBool(0).getCodeName());
      
      //Storm
      oSettings = oOutput.getDetailedDeadTreeSetting(5);
      assertEquals(0, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.STORM, oSettings.getDeadCode());
      assertEquals(1, oSettings.getSaveFrequency());
      assertEquals(1, oSettings.getNumberOfBools());
      assertEquals(1, oSettings.getNumberOfChars());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals(1, oSettings.getNumberOfInts());
      assertEquals("dead species 1 test float 1 c " + OutputBehaviors.STORM, oSettings.getFloat(0).getCodeName());
      assertEquals("dead species 1 test char 1 c " + OutputBehaviors.STORM, oSettings.getChar(0).getCodeName());
      assertEquals("dead species 1 test int 1 c " + OutputBehaviors.STORM, oSettings.getInt(0).getCodeName());
      assertEquals("dead species 1 test bool 1 c " + OutputBehaviors.STORM, oSettings.getBool(0).getCodeName());
      
      //Multiples of each - not grouped together by type
      oSettings = oOutput.getDetailedDeadTreeSetting(6);
      assertEquals(1, oSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(OutputBehaviors.STORM, oSettings.getDeadCode());
      assertEquals(2, oSettings.getSaveFrequency());
      assertEquals(2, oSettings.getNumberOfBools());
      assertEquals(2, oSettings.getNumberOfChars());
      assertEquals(2, oSettings.getNumberOfFloats());
      assertEquals(2, oSettings.getNumberOfInts());

      
      //Subplots
      assertEquals(2, oOutput.mp_oDetailedOutputSubplots.size());

      Subplot oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(0);
      assertEquals("test 1", oSubplot.getSubplotName());
      assertEquals(1, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX());
      assertEquals(12, oSubplot.getCell(0).getY());

      oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(1);
      assertEquals("test 2", oSubplot.getSubplotName());
      assertEquals(5, oSubplot.getNumberOfCells());
      assertEquals(6, oSubplot.getCell(0).getX());
      assertEquals(14, oSubplot.getCell(0).getY());
      assertEquals(7, oSubplot.getCell(1).getX());
      assertEquals(15, oSubplot.getCell(1).getY());
      assertEquals(8, oSubplot.getCell(2).getX());
      assertEquals(16, oSubplot.getCell(2).getY());
      assertEquals(9, oSubplot.getCell(3).getX());
      assertEquals(17, oSubplot.getCell(3).getY());
      assertEquals(10, oSubplot.getCell(4).getX());
      assertEquals(18, oSubplot.getCell(4).getY());

      ShortOutput oOutput2 = oOb.getShortOutput();
      assertEquals(0, oOutput2.mp_oShortOutputSubplots.size());
      System.out.println("XML rundata reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML rundata reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

  }
  
  /**
   * This tests writing and reading output settings, including dead. 
   */
  public void testWriteXMLDetailed() {
    String sFileName = null, sFileName2 = "tester.xml"; 
    DetailedTreeSettings oSettings;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile5();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();
      
      oOutput.setDetailedOutputFileName("figs");
      
      //Try each dead reason code
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.NOTDEAD);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.DISEASE);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.NATURAL);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.FIRE);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.STORM);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.HARVEST);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, 0, OutputBehaviors.INSECTS);
      oSettings.addFloat("X", "X");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.NOTDEAD);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.DISEASE);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.NATURAL);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.FIRE);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.STORM);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.HARVEST);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, 0, OutputBehaviors.INSECTS);
      oSettings.addFloat("Y", "Y");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedTreeSettings(oSettings);
      
           
      oManager.writeParameterFile(sFileName2); 
      oManager.inputXMLParameterFile(sFileName2);
      oOb = oManager.getOutputBehaviors();
      oOutput = oOb.getDetailedOutput();
      
      assertEquals(2, oOutput.getNumberOfDetailedLiveTreeSettings());
      assertEquals(12, oOutput.getNumberOfDetailedDeadTreeSettings());
      
      oSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.NOTDEAD, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
            
      oSettings = oOutput.getDetailedDeadTreeSetting(0);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.DISEASE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(1);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.NATURAL, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(2);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.FIRE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(3);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.STORM, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(4);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.HARVEST, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(5);
      assertEquals(TreePopulation.ADULT, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.INSECTS, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("X", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.NOTDEAD, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(6);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.DISEASE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(7);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.NATURAL, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(8);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.FIRE, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(9);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.STORM, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(10);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.HARVEST, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      oSettings = oOutput.getDetailedDeadTreeSetting(11);
      assertEquals(TreePopulation.SAPLING, oSettings.getType());
      assertEquals(0, oSettings.getSpecies());
      assertEquals(OutputBehaviors.INSECTS, oSettings.getDeadCode());
      assertEquals(1, oSettings.getNumberOfFloats());
      assertEquals("Y", oSettings.getFloat(0).getDisplayName());
      assertEquals(2, oSettings.getSaveFrequency());
      
      
      System.out.println("XML detailed output reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML detailed output reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
      new File(sFileName2).delete();
    }
  }

  
  /**
   * Tests grid settings when species are changed.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      //Verify the current grid settings
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();
      assertEquals(5, oOutput.getNumberOfDetailedGridSettings());
      DetailedGridSettings oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(19, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("ba_4", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("ba_5", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("ba_6", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("ba_7", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("ba_8", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("density_0", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("density_1", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(11).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(12).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(13).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(14).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(15).getCodeName());
      assertEquals("density_7", oGridSettings.getPackageFloat(16).getCodeName());
      assertEquals("density_8", oGridSettings.getPackageFloat(17).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(18).getCodeName());
      assertEquals("Basal Area Dead For Western Hemlock", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Western Redcedar", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Basal Area Dead For Hybrid Spruce", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Basal Area Dead For Lodgepole Pine", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Basal Area Dead For Trembling Aspen", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Basal Area Dead For Black Cottonwood", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Basal Area Dead For Paper Birch", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Density Dead For Western Hemlock", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Density Dead For Western Redcedar", oGridSettings.getPackageFloat(10).getDisplayName());
      assertEquals("Density Dead For Amabilis Fir", oGridSettings.getPackageFloat(11).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(12).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(13).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(14).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(15).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(16).getDisplayName());
      assertEquals("Density Dead For Paper Birch", oGridSettings.getPackageFloat(17).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(18).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(9, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("seeds_7", oGridSettings.getFloat(7).getCodeName());
      assertEquals("seeds_8", oGridSettings.getFloat(8).getCodeName());
      assertEquals("Number of seeds for Western Hemlock", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Western Redcedar", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Subalpine Fir", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(6).getDisplayName());
      assertEquals("Number of seeds for Black Cottonwood", oGridSettings.getFloat(7).getDisplayName());
      assertEquals("Number of seeds for Paper Birch", oGridSettings.getFloat(8).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      assertEquals(9, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("Favorability Index2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("Favorability Index3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("Favorability Index4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Favorability Index5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Favorability Index6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("Favorability Index7", oGridSettings.getFloat(7).getCodeName());
      assertEquals("Favorability Index8", oGridSettings.getFloat(8).getCodeName());
      assertEquals("Favorability Index - Western Hemlock", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Favorability Index - Western Redcedar", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Favorability Index - Subalpine Fir", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Favorability Index - Hybrid Spruce", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Favorability Index - Lodgepole Pine", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Favorability Index - Trembling Aspen", oGridSettings.getFloat(6).getDisplayName());
      assertEquals("Favorability Index - Black Cottonwood", oGridSettings.getFloat(7).getDisplayName());
      assertEquals("Favorability Index - Paper Birch", oGridSettings.getFloat(8).getDisplayName());

      //Verify the current tree settings
      assertEquals(20, oOutput.getNumberOfDetailedLiveTreeSettings());
      DetailedTreeSettings oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(2, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(3, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(7, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(8, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(9, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("DBH", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(9);
      assertEquals(10, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Height", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(10);
      assertEquals(11, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(11);
      assertEquals(12, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("Growth", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(12);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(13);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(14);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(15);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(16);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(17);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(18);
      assertEquals(19, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(19);
      assertEquals(20, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      /////////////////////////////
      //Remove the first species
      //////////////////////////////
      String[] p_sSpeciesList = new String[] {
          "Western Redcedar",
          "Amabilis Fir",
          "Subalpine Fir",
          "Hybrid Spruce",
          "Lodgepole Pine",
          "Trembling Aspen",
          "Black Cottonwood",
          "Paper Birch"
      };
      TreePopulation oPop = oManager.getTreePopulation();
      oPop.setSpeciesNames(p_sSpeciesList);
      assertEquals(5, oOutput.getNumberOfDetailedGridSettings());
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(17, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("ba_4", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("ba_5", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("ba_6", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("ba_7", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("density_0", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("density_1", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(11).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(12).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(13).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(14).getCodeName());
      assertEquals("density_7", oGridSettings.getPackageFloat(15).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(16).getCodeName());
      assertEquals("Basal Area Dead For Western Redcedar", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Hybrid Spruce", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Basal Area Dead For Lodgepole Pine", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Basal Area Dead For Trembling Aspen", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Basal Area Dead For Black Cottonwood", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Basal Area Dead For Paper Birch", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Density Dead For Western Redcedar", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Density Dead For Amabilis Fir", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(10).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(11).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(12).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(13).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(14).getDisplayName());
      assertEquals("Density Dead For Paper Birch", oGridSettings.getPackageFloat(15).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(16).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(8, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("seeds_7", oGridSettings.getFloat(7).getCodeName());
      assertEquals("Number of seeds for Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Subalpine Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Number of seeds for Black Cottonwood", oGridSettings.getFloat(6).getDisplayName());
      assertEquals("Number of seeds for Paper Birch", oGridSettings.getFloat(7).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      assertEquals(8, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("Favorability Index2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("Favorability Index3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("Favorability Index4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Favorability Index5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Favorability Index6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("Favorability Index7", oGridSettings.getFloat(7).getCodeName());
      assertEquals("Favorability Index - Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Favorability Index - Subalpine Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Favorability Index - Hybrid Spruce", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Favorability Index - Lodgepole Pine", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Favorability Index - Trembling Aspen", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Favorability Index - Black Cottonwood", oGridSettings.getFloat(6).getDisplayName());
      assertEquals("Favorability Index - Paper Birch", oGridSettings.getFloat(7).getDisplayName());

      //Verify the tree settings
      assertEquals(17, oOutput.getNumberOfDetailedLiveTreeSettings());
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(7, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(8, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(9, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("DBH", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(10, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Height", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(11, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(12, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("Growth", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(9);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(10);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(11);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(12);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(13);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(14);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(15);
      assertEquals(19, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(16);
      assertEquals(20, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      /////////////////////////////
      // Remove the last species
      /////////////////////////////
      p_sSpeciesList = new String[] {
          "Western Redcedar",
          "Amabilis Fir",
          "Subalpine Fir",
          "Hybrid Spruce",
          "Lodgepole Pine",
          "Trembling Aspen",
          "Black Cottonwood"
      };
      oPop.setSpeciesNames(p_sSpeciesList);
      assertEquals(5, oOutput.getNumberOfDetailedGridSettings());
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(15, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("ba_4", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("ba_5", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("ba_6", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("density_0", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("density_1", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(11).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(12).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(13).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(14).getCodeName());
      assertEquals("Basal Area Dead For Western Redcedar", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Hybrid Spruce", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Basal Area Dead For Lodgepole Pine", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Basal Area Dead For Trembling Aspen", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Basal Area Dead For Black Cottonwood", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Density Dead For Western Redcedar", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Density Dead For Amabilis Fir", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(10).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(11).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(12).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(13).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(14).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(7, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("Number of seeds for Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Subalpine Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Number of seeds for Black Cottonwood", oGridSettings.getFloat(6).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      assertEquals(7, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("Favorability Index2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("Favorability Index3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("Favorability Index4", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Favorability Index5", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Favorability Index6", oGridSettings.getFloat(6).getCodeName());
      assertEquals("Favorability Index - Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Favorability Index - Subalpine Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Favorability Index - Hybrid Spruce", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Favorability Index - Lodgepole Pine", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Favorability Index - Trembling Aspen", oGridSettings.getFloat(5).getDisplayName());
      assertEquals("Favorability Index - Black Cottonwood", oGridSettings.getFloat(6).getDisplayName());

      //Verify the tree settings
      assertEquals(15, oOutput.getNumberOfDetailedLiveTreeSettings());
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(7, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(8, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(9, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("DBH", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(10, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Height", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(11, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(12, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("Growth", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(9);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(10);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(11);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(12);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(13);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(14);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      /////////////////////////////
      // Remove only output settings - don't change species
      /////////////////////////////
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      int i;
      for (i = 0; i < oGridSettings.getNumberOfPackageFloats(); i++) {
        if (oGridSettings.getPackageFloat(i).getCodeName().equals("ba_0")) {
          oGridSettings.removePackageFloat(i);
          break;
        }
      }
      for (i = 0; i < oGridSettings.getNumberOfPackageFloats(); i++) {
        if (oGridSettings.getPackageFloat(i).getCodeName().equals("density_1")) {
          oGridSettings.removePackageFloat(i);
          break;
        }
      }
      assertEquals(13, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_1", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_4", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("ba_5", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("ba_6", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("density_0", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(11).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(12).getCodeName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Hybrid Spruce", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Lodgepole Pine", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Basal Area Dead For Trembling Aspen", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Basal Area Dead For Black Cottonwood", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Density Dead For Western Redcedar", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(10).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(11).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(12).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      oGridSettings.removeFloat(2);
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_3", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Number of seeds for Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Black Cottonwood", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      oGridSettings.removeFloat(3);
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index1", oGridSettings.getFloat(1).getCodeName());
      assertEquals("Favorability Index2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("Favorability Index4", oGridSettings.getFloat(3).getCodeName());
      assertEquals("Favorability Index5", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Favorability Index6", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Favorability Index - Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Favorability Index - Subalpine Fir", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Favorability Index - Lodgepole Pine", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Favorability Index - Trembling Aspen", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Favorability Index - Black Cottonwood", oGridSettings.getFloat(5).getDisplayName());

      /////////////////////////////
      // Remove two middle species and alphabetize the remainder
      /////////////////////////////
      p_sSpeciesList = new String[] {
          "Amabilis Fir",
          "Black Cottonwood",
          "Lodgepole Pine",
          "Trembling Aspen",
          "Western Redcedar"
      };
      oPop.setSpeciesNames(p_sSpeciesList);
      assertEquals(5, oOutput.getNumberOfDetailedGridSettings());
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(9, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("density_1", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Lodgepole Pine", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Trembling Aspen", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Black Cottonwood", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Density Dead For Western Redcedar", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(8).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(5, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_4", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_0", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_1", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Number of seeds for Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Black Cottonwood", oGridSettings.getFloat(4).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());
      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      assertEquals(5, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index4", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index0", oGridSettings.getFloat(1).getCodeName());
      assertEquals("Favorability Index2", oGridSettings.getFloat(2).getCodeName());
      assertEquals("Favorability Index3", oGridSettings.getFloat(3).getCodeName());
      assertEquals("Favorability Index1", oGridSettings.getFloat(4).getCodeName());
      assertEquals("Favorability Index - Western Redcedar", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Favorability Index - Lodgepole Pine", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Favorability Index - Trembling Aspen", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Favorability Index - Black Cottonwood", oGridSettings.getFloat(4).getDisplayName());

      //Verify the tree settings
      assertEquals(9, oOutput.getNumberOfDetailedLiveTreeSettings());
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());


      System.out.println(
        "Normal processing succeeded for testChangeOfSpecies.");
    } catch (ModelException oErr) {
      fail("testChangeOfSpecies failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests output settings when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);

      //Verify the current grid settings
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();      
      assertEquals(5, oOutput.getNumberOfDetailedGridSettings());
      DetailedGridSettings oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(11, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_2", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("density_7", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("density_8", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("Basal Area Dead For Western Hemlock", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Western Redcedar", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Amabilis Fir", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Density Dead For Paper Birch", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(10).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_8", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Number of seeds for Western Hemlock", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Paper Birch", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(4);
      assertEquals("Substrate Favorability", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("Favorability Index2", oGridSettings.getFloat(0).getCodeName());
      assertEquals("Favorability Index - Amabilis Fir", oGridSettings.getFloat(0).getDisplayName());

      //Verify the current tree settings
      assertEquals(20, oOutput.getNumberOfDetailedLiveTreeSettings());
      DetailedTreeSettings oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(2, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(3, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(7, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(8, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(9, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("DBH", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(9);
      assertEquals(10, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Height", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(10);
      assertEquals(11, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(11);
      assertEquals(12, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("Growth", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(12);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(13);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(14);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(15);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(16);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(17);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(18);
      assertEquals(19, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(19);
      assertEquals(20, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      //********************
      // Make Amabilis Fir a copy of Lodgepole Pine
      //********************
      oManager.getTreePopulation().doCopySpecies("Lodgepole_Pine", "Amabilis_Fir");

      assertEquals(4, oOutput.getNumberOfDetailedGridSettings());
      oGridSettings = oOutput.getDetailedGridSetting(0);
      assertEquals("Windstorm Results", oGridSettings.getName());
      assertEquals(11, oGridSettings.getNumberOfPackageFloats());
      assertEquals("ba_0", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("ba_1", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("ba_3", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("density_3", oGridSettings.getPackageFloat(3).getCodeName());
      assertEquals("density_4", oGridSettings.getPackageFloat(4).getCodeName());
      assertEquals("density_5", oGridSettings.getPackageFloat(5).getCodeName());
      assertEquals("density_6", oGridSettings.getPackageFloat(6).getCodeName());
      assertEquals("density_7", oGridSettings.getPackageFloat(7).getCodeName());
      assertEquals("density_8", oGridSettings.getPackageFloat(8).getCodeName());
      assertEquals("severity", oGridSettings.getPackageFloat(9).getCodeName());
      assertEquals("density_2", oGridSettings.getPackageFloat(10).getCodeName());
      assertEquals("Basal Area Dead For Western Hemlock", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Basal Area Dead For Western Redcedar", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Basal Area Dead For Subalpine Fir", oGridSettings.getPackageFloat(2).getDisplayName());
      assertEquals("Density Dead For Subalpine Fir", oGridSettings.getPackageFloat(3).getDisplayName());
      assertEquals("Density Dead For Hybrid Spruce", oGridSettings.getPackageFloat(4).getDisplayName());
      assertEquals("Density Dead For Lodgepole Pine", oGridSettings.getPackageFloat(5).getDisplayName());
      assertEquals("Density Dead For Trembling Aspen", oGridSettings.getPackageFloat(6).getDisplayName());
      assertEquals("Density Dead For Black Cottonwood", oGridSettings.getPackageFloat(7).getDisplayName());
      assertEquals("Density Dead For Paper Birch", oGridSettings.getPackageFloat(8).getDisplayName());
      assertEquals("Storm Severity", oGridSettings.getPackageFloat(9).getDisplayName());
      assertEquals("Density Dead For Amabilis Fir", oGridSettings.getPackageFloat(10).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(1);
      assertEquals("Quadrat GLI", oGridSettings.getName());
      assertEquals(1, oGridSettings.getNumberOfFloats());
      assertEquals("GLI", oGridSettings.getFloat(0).getCodeName());
      assertEquals("GLI", oGridSettings.getFloat(0).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(2);
      assertEquals("Substrate", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("scarsoil", oGridSettings.getFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getFloat(2).getCodeName());
      assertEquals("declog", oGridSettings.getFloat(3).getCodeName());
      assertEquals("ffmoss", oGridSettings.getFloat(4).getCodeName());
      assertEquals("fflitter", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Proportion of scarified soil", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Proportion of tip-up mounds", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Proportion of fresh logs", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Proportion of decayed logs", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Proportion of forest floor moss", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Proportion of forest floor litter", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfPackageInts());
      assertEquals("age", oGridSettings.getPackageInt(0).getCodeName());
      assertEquals("Substrate cohort age", oGridSettings.getPackageInt(0).getDisplayName());
      assertEquals(3, oGridSettings.getNumberOfPackageFloats());
      assertEquals("scarsoil", oGridSettings.getPackageFloat(0).getCodeName());
      assertEquals("tipup", oGridSettings.getPackageFloat(1).getCodeName());
      assertEquals("freshlog", oGridSettings.getPackageFloat(2).getCodeName());
      assertEquals("Substrate cohort new scarified soil substrate", oGridSettings.getPackageFloat(0).getDisplayName());
      assertEquals("Substrate cohort new tip-up mounds substrate", oGridSettings.getPackageFloat(1).getDisplayName());
      assertEquals("Substrate cohort new fresh logs", oGridSettings.getPackageFloat(2).getDisplayName());

      oGridSettings = oOutput.getDetailedGridSetting(3);
      assertEquals("Dispersed Seeds", oGridSettings.getName());
      assertEquals(6, oGridSettings.getNumberOfFloats());
      assertEquals("seeds_0", oGridSettings.getFloat(0).getCodeName());
      assertEquals("seeds_4", oGridSettings.getFloat(1).getCodeName());
      assertEquals("seeds_5", oGridSettings.getFloat(2).getCodeName());
      assertEquals("seeds_6", oGridSettings.getFloat(3).getCodeName());
      assertEquals("seeds_8", oGridSettings.getFloat(4).getCodeName());
      assertEquals("seeds_2", oGridSettings.getFloat(5).getCodeName());
      assertEquals("Number of seeds for Western Hemlock", oGridSettings.getFloat(0).getDisplayName());
      assertEquals("Number of seeds for Hybrid Spruce", oGridSettings.getFloat(1).getDisplayName());
      assertEquals("Number of seeds for Lodgepole Pine", oGridSettings.getFloat(2).getDisplayName());
      assertEquals("Number of seeds for Trembling Aspen", oGridSettings.getFloat(3).getDisplayName());
      assertEquals("Number of seeds for Paper Birch", oGridSettings.getFloat(4).getDisplayName());
      assertEquals("Number of seeds for Amabilis Fir", oGridSettings.getFloat(5).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfInts());
      assertEquals("count", oGridSettings.getInt(0).getCodeName());
      assertEquals("Adult tree count", oGridSettings.getInt(0).getDisplayName());
      assertEquals(1, oGridSettings.getNumberOfBools());
      assertEquals("Is Gap", oGridSettings.getBool(0).getCodeName());
      assertEquals("Gap status", oGridSettings.getBool(0).getDisplayName());

      //Verify the current tree settings
      assertEquals(23, oOutput.getNumberOfDetailedLiveTreeSettings());
      oTreeSettings = oOutput.getDetailedLiveTreeSetting(0);
      assertEquals(1, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(1);
      assertEquals(2, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(2);
      assertEquals(3, oTreeSettings.getSaveFrequency());
      assertEquals(0, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(3);
      assertEquals(4, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(4);
      assertEquals(5, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(5);
      assertEquals(6, oTreeSettings.getSaveFrequency());
      assertEquals(1, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(6);
      assertEquals(7, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(7);
      assertEquals(8, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(8);
      assertEquals(9, oTreeSettings.getSaveFrequency());
      assertEquals(3, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("DBH", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(9);
      assertEquals(10, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Height", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(10);
      assertEquals(11, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(11);
      assertEquals(12, oTreeSettings.getSaveFrequency());
      assertEquals(4, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("Growth", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(12);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(13);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(14);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(5, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(15);
      assertEquals(16, oTreeSettings.getSaveFrequency());
      assertEquals(6, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("Diam10", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(16);
      assertEquals(17, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Light", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(17);
      assertEquals(18, oTreeSettings.getSaveFrequency());
      assertEquals(7, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(18);
      assertEquals(19, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("Y", oTreeSettings.getFloat(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(19);
      assertEquals(20, oTreeSettings.getSaveFrequency());
      assertEquals(8, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(20);
      assertEquals(13, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SEEDLING, oTreeSettings.getType());
      assertEquals("dead", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(21);
      assertEquals(14, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.SAPLING, oTreeSettings.getType());
      assertEquals("yls", oTreeSettings.getInt(0).getCodeName());

      oTreeSettings = oOutput.getDetailedLiveTreeSetting(22);
      assertEquals(15, oTreeSettings.getSaveFrequency());
      assertEquals(2, oTreeSettings.getSpecies());
      assertEquals(TreePopulation.ADULT, oTreeSettings.getType());
      assertEquals("X", oTreeSettings.getFloat(0).getCodeName());

      System.out.println(
          "Normal processing succeeded for testCopySpecies.");
    }
    catch (ModelException oErr) {
      fail("testChangeOfSpecies failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests shrinking of subplots when the plot resolution is changed.
   */
  public void testChangeOfPlotResolution() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);

      sFileName = writeXMLFile4();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();

      //*****************************
      //Verify the initial data
      //*****************************
      assertEquals(3, oOutput.mp_oDetailedOutputSubplots.size());

      //Detailed output subplots
      Subplot oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(0);
      assertEquals("plot 1", oSubplot.getSubplotName());
      assertEquals(25, oSubplot.getNumberOfCells());
      assertEquals(20, oSubplot.getCell(0).getX()); assertEquals(20, oSubplot.getCell(0).getY());
      assertEquals(20, oSubplot.getCell(1).getX()); assertEquals(21, oSubplot.getCell(1).getY());
      assertEquals(20, oSubplot.getCell(2).getX()); assertEquals(22, oSubplot.getCell(2).getY());
      assertEquals(20, oSubplot.getCell(3).getX()); assertEquals(23, oSubplot.getCell(3).getY());
      assertEquals(20, oSubplot.getCell(4).getX()); assertEquals(24, oSubplot.getCell(4).getY());
      assertEquals(21, oSubplot.getCell(5).getX()); assertEquals(20, oSubplot.getCell(5).getY());
      assertEquals(21, oSubplot.getCell(6).getX()); assertEquals(21, oSubplot.getCell(6).getY());
      assertEquals(21, oSubplot.getCell(7).getX()); assertEquals(22, oSubplot.getCell(7).getY());
      assertEquals(21, oSubplot.getCell(8).getX()); assertEquals(23, oSubplot.getCell(8).getY());
      assertEquals(21, oSubplot.getCell(9).getX()); assertEquals(24, oSubplot.getCell(9).getY());
      assertEquals(22, oSubplot.getCell(10).getX()); assertEquals(20, oSubplot.getCell(10).getY());
      assertEquals(22, oSubplot.getCell(11).getX()); assertEquals(21, oSubplot.getCell(11).getY());
      assertEquals(22, oSubplot.getCell(12).getX()); assertEquals(22, oSubplot.getCell(12).getY());
      assertEquals(22, oSubplot.getCell(13).getX()); assertEquals(23, oSubplot.getCell(13).getY());
      assertEquals(22, oSubplot.getCell(14).getX()); assertEquals(24, oSubplot.getCell(14).getY());
      assertEquals(23, oSubplot.getCell(15).getX()); assertEquals(20, oSubplot.getCell(15).getY());
      assertEquals(23, oSubplot.getCell(16).getX()); assertEquals(21, oSubplot.getCell(16).getY());
      assertEquals(23, oSubplot.getCell(17).getX()); assertEquals(22, oSubplot.getCell(17).getY());
      assertEquals(23, oSubplot.getCell(18).getX()); assertEquals(23, oSubplot.getCell(18).getY());
      assertEquals(23, oSubplot.getCell(19).getX()); assertEquals(24, oSubplot.getCell(19).getY());
      assertEquals(24, oSubplot.getCell(20).getX()); assertEquals(20, oSubplot.getCell(20).getY());
      assertEquals(24, oSubplot.getCell(21).getX()); assertEquals(21, oSubplot.getCell(21).getY());
      assertEquals(24, oSubplot.getCell(22).getX()); assertEquals(22, oSubplot.getCell(22).getY());
      assertEquals(24, oSubplot.getCell(23).getX()); assertEquals(23, oSubplot.getCell(23).getY());

      oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(1);
      assertEquals("plot 2", oSubplot.getSubplotName());
      assertEquals(48, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(0, oSubplot.getCell(6).getX()); assertEquals(6, oSubplot.getCell(6).getY());
      assertEquals(0, oSubplot.getCell(7).getX()); assertEquals(7, oSubplot.getCell(7).getY());
      assertEquals(0, oSubplot.getCell(8).getX()); assertEquals(8, oSubplot.getCell(8).getY());
      assertEquals(0, oSubplot.getCell(9).getX()); assertEquals(9, oSubplot.getCell(9).getY());
      assertEquals(0, oSubplot.getCell(10).getX()); assertEquals(10, oSubplot.getCell(10).getY());
      assertEquals(0, oSubplot.getCell(11).getX()); assertEquals(11, oSubplot.getCell(11).getY());
      assertEquals(0, oSubplot.getCell(12).getX()); assertEquals(12, oSubplot.getCell(12).getY());
      assertEquals(0, oSubplot.getCell(13).getX()); assertEquals(13, oSubplot.getCell(13).getY());
      assertEquals(0, oSubplot.getCell(14).getX()); assertEquals(14, oSubplot.getCell(14).getY());
      assertEquals(0, oSubplot.getCell(15).getX()); assertEquals(15, oSubplot.getCell(15).getY());
      assertEquals(0, oSubplot.getCell(16).getX()); assertEquals(16, oSubplot.getCell(16).getY());
      assertEquals(0, oSubplot.getCell(17).getX()); assertEquals(17, oSubplot.getCell(17).getY());
      assertEquals(0, oSubplot.getCell(18).getX()); assertEquals(24, oSubplot.getCell(18).getY());
      assertEquals(1, oSubplot.getCell(19).getX()); assertEquals(24, oSubplot.getCell(19).getY());
      assertEquals(2, oSubplot.getCell(20).getX()); assertEquals(0, oSubplot.getCell(20).getY());
      assertEquals(2, oSubplot.getCell(21).getX()); assertEquals(1, oSubplot.getCell(21).getY());
      assertEquals(2, oSubplot.getCell(22).getX()); assertEquals(2, oSubplot.getCell(22).getY());
      assertEquals(2, oSubplot.getCell(23).getX()); assertEquals(3, oSubplot.getCell(23).getY());
      assertEquals(2, oSubplot.getCell(24).getX()); assertEquals(4, oSubplot.getCell(24).getY());
      assertEquals(2, oSubplot.getCell(25).getX()); assertEquals(5, oSubplot.getCell(25).getY());
      assertEquals(2, oSubplot.getCell(26).getX()); assertEquals(6, oSubplot.getCell(26).getY());
      assertEquals(2, oSubplot.getCell(27).getX()); assertEquals(7, oSubplot.getCell(27).getY());
      assertEquals(2, oSubplot.getCell(28).getX()); assertEquals(8, oSubplot.getCell(28).getY());
      assertEquals(2, oSubplot.getCell(29).getX()); assertEquals(9, oSubplot.getCell(29).getY());
      assertEquals(2, oSubplot.getCell(30).getX()); assertEquals(10, oSubplot.getCell(30).getY());
      assertEquals(2, oSubplot.getCell(31).getX()); assertEquals(11, oSubplot.getCell(31).getY());
      assertEquals(2, oSubplot.getCell(32).getX()); assertEquals(12, oSubplot.getCell(32).getY());
      assertEquals(2, oSubplot.getCell(33).getX()); assertEquals(13, oSubplot.getCell(33).getY());
      assertEquals(2, oSubplot.getCell(34).getX()); assertEquals(14, oSubplot.getCell(34).getY());
      assertEquals(2, oSubplot.getCell(35).getX()); assertEquals(15, oSubplot.getCell(35).getY());
      assertEquals(2, oSubplot.getCell(36).getX()); assertEquals(16, oSubplot.getCell(36).getY());
      assertEquals(2, oSubplot.getCell(37).getX()); assertEquals(17, oSubplot.getCell(37).getY());
      assertEquals(2, oSubplot.getCell(38).getX()); assertEquals(24, oSubplot.getCell(38).getY());
      assertEquals(3, oSubplot.getCell(39).getX()); assertEquals(24, oSubplot.getCell(39).getY());
      assertEquals(4, oSubplot.getCell(40).getX()); assertEquals(24, oSubplot.getCell(40).getY());
      assertEquals(5, oSubplot.getCell(41).getX()); assertEquals(24, oSubplot.getCell(41).getY());
      assertEquals(24, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(24, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(24, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(24, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(24, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(24, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());

      oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(2);
      assertEquals("plot 3", oSubplot.getSubplotName());
      assertEquals(72, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(1, oSubplot.getCell(6).getX()); assertEquals(0, oSubplot.getCell(6).getY());
      assertEquals(1, oSubplot.getCell(7).getX()); assertEquals(1, oSubplot.getCell(7).getY());
      assertEquals(1, oSubplot.getCell(8).getX()); assertEquals(2, oSubplot.getCell(8).getY());
      assertEquals(1, oSubplot.getCell(9).getX()); assertEquals(3, oSubplot.getCell(9).getY());
      assertEquals(1, oSubplot.getCell(10).getX()); assertEquals(4, oSubplot.getCell(10).getY());
      assertEquals(1, oSubplot.getCell(11).getX()); assertEquals(5, oSubplot.getCell(11).getY());
      assertEquals(2, oSubplot.getCell(12).getX()); assertEquals(0, oSubplot.getCell(12).getY());
      assertEquals(2, oSubplot.getCell(13).getX()); assertEquals(1, oSubplot.getCell(13).getY());
      assertEquals(2, oSubplot.getCell(14).getX()); assertEquals(2, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(3, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(4, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(5, oSubplot.getCell(17).getY());
      assertEquals(3, oSubplot.getCell(18).getX()); assertEquals(0, oSubplot.getCell(18).getY());
      assertEquals(3, oSubplot.getCell(19).getX()); assertEquals(1, oSubplot.getCell(19).getY());
      assertEquals(3, oSubplot.getCell(20).getX()); assertEquals(2, oSubplot.getCell(20).getY());
      assertEquals(3, oSubplot.getCell(21).getX()); assertEquals(3, oSubplot.getCell(21).getY());
      assertEquals(3, oSubplot.getCell(22).getX()); assertEquals(4, oSubplot.getCell(22).getY());
      assertEquals(3, oSubplot.getCell(23).getX()); assertEquals(5, oSubplot.getCell(23).getY());
      assertEquals(4, oSubplot.getCell(24).getX()); assertEquals(0, oSubplot.getCell(24).getY());
      assertEquals(4, oSubplot.getCell(25).getX()); assertEquals(1, oSubplot.getCell(25).getY());
      assertEquals(4, oSubplot.getCell(26).getX()); assertEquals(2, oSubplot.getCell(26).getY());
      assertEquals(4, oSubplot.getCell(27).getX()); assertEquals(3, oSubplot.getCell(27).getY());
      assertEquals(4, oSubplot.getCell(28).getX()); assertEquals(4, oSubplot.getCell(28).getY());
      assertEquals(4, oSubplot.getCell(29).getX()); assertEquals(5, oSubplot.getCell(29).getY());
      assertEquals(5, oSubplot.getCell(30).getX()); assertEquals(0, oSubplot.getCell(30).getY());
      assertEquals(5, oSubplot.getCell(31).getX()); assertEquals(1, oSubplot.getCell(31).getY());
      assertEquals(5, oSubplot.getCell(32).getX()); assertEquals(2, oSubplot.getCell(32).getY());
      assertEquals(5, oSubplot.getCell(33).getX()); assertEquals(3, oSubplot.getCell(33).getY());
      assertEquals(5, oSubplot.getCell(34).getX()); assertEquals(4, oSubplot.getCell(34).getY());
      assertEquals(5, oSubplot.getCell(35).getX()); assertEquals(5, oSubplot.getCell(35).getY());
      assertEquals(6, oSubplot.getCell(36).getX()); assertEquals(0, oSubplot.getCell(36).getY());
      assertEquals(6, oSubplot.getCell(37).getX()); assertEquals(1, oSubplot.getCell(37).getY());
      assertEquals(6, oSubplot.getCell(38).getX()); assertEquals(2, oSubplot.getCell(38).getY());
      assertEquals(6, oSubplot.getCell(39).getX()); assertEquals(3, oSubplot.getCell(39).getY());
      assertEquals(6, oSubplot.getCell(40).getX()); assertEquals(4, oSubplot.getCell(40).getY());
      assertEquals(6, oSubplot.getCell(41).getX()); assertEquals(5, oSubplot.getCell(41).getY());
      assertEquals(7, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(7, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(7, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(7, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(7, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(7, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());
      assertEquals(8, oSubplot.getCell(48).getX()); assertEquals(0, oSubplot.getCell(48).getY());
      assertEquals(8, oSubplot.getCell(49).getX()); assertEquals(1, oSubplot.getCell(49).getY());
      assertEquals(8, oSubplot.getCell(50).getX()); assertEquals(2, oSubplot.getCell(50).getY());
      assertEquals(8, oSubplot.getCell(51).getX()); assertEquals(3, oSubplot.getCell(51).getY());
      assertEquals(8, oSubplot.getCell(52).getX()); assertEquals(4, oSubplot.getCell(52).getY());
      assertEquals(8, oSubplot.getCell(53).getX()); assertEquals(5, oSubplot.getCell(53).getY());
      assertEquals(9, oSubplot.getCell(54).getX()); assertEquals(0, oSubplot.getCell(54).getY());
      assertEquals(9, oSubplot.getCell(55).getX()); assertEquals(1, oSubplot.getCell(55).getY());
      assertEquals(9, oSubplot.getCell(56).getX()); assertEquals(2, oSubplot.getCell(56).getY());
      assertEquals(9, oSubplot.getCell(57).getX()); assertEquals(3, oSubplot.getCell(57).getY());
      assertEquals(9, oSubplot.getCell(58).getX()); assertEquals(4, oSubplot.getCell(58).getY());
      assertEquals(9, oSubplot.getCell(59).getX()); assertEquals(5, oSubplot.getCell(59).getY());
      assertEquals(10, oSubplot.getCell(60).getX()); assertEquals(0, oSubplot.getCell(60).getY());
      assertEquals(10, oSubplot.getCell(61).getX()); assertEquals(1, oSubplot.getCell(61).getY());
      assertEquals(10, oSubplot.getCell(62).getX()); assertEquals(2, oSubplot.getCell(62).getY());
      assertEquals(10, oSubplot.getCell(63).getX()); assertEquals(3, oSubplot.getCell(63).getY());
      assertEquals(10, oSubplot.getCell(64).getX()); assertEquals(4, oSubplot.getCell(64).getY());
      assertEquals(10, oSubplot.getCell(65).getX()); assertEquals(5, oSubplot.getCell(65).getY());
      assertEquals(11, oSubplot.getCell(66).getX()); assertEquals(0, oSubplot.getCell(66).getY());
      assertEquals(11, oSubplot.getCell(67).getX()); assertEquals(1, oSubplot.getCell(67).getY());
      assertEquals(11, oSubplot.getCell(68).getX()); assertEquals(2, oSubplot.getCell(68).getY());
      assertEquals(11, oSubplot.getCell(69).getX()); assertEquals(3, oSubplot.getCell(69).getY());
      assertEquals(11, oSubplot.getCell(70).getX()); assertEquals(4, oSubplot.getCell(70).getY());
      assertEquals(11, oSubplot.getCell(71).getX()); assertEquals(5, oSubplot.getCell(71).getY());
      
      //*****************************
      //Change the plot resolution
      //*****************************
      oManager.changeOfPlotResolution(200, 200, 100, 120);

      assertEquals(2, oOutput.mp_oDetailedOutputSubplots.size());

      //Detailed output subplots
      oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(0);
      assertEquals("plot 2", oSubplot.getSubplotName());
      assertEquals(30, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(0, oSubplot.getCell(6).getX()); assertEquals(6, oSubplot.getCell(6).getY());
      assertEquals(0, oSubplot.getCell(7).getX()); assertEquals(7, oSubplot.getCell(7).getY());
      assertEquals(0, oSubplot.getCell(8).getX()); assertEquals(8, oSubplot.getCell(8).getY());
      assertEquals(0, oSubplot.getCell(9).getX()); assertEquals(9, oSubplot.getCell(9).getY());
      assertEquals(0, oSubplot.getCell(10).getX()); assertEquals(10, oSubplot.getCell(10).getY());
      assertEquals(0, oSubplot.getCell(11).getX()); assertEquals(11, oSubplot.getCell(11).getY());
      assertEquals(0, oSubplot.getCell(12).getX()); assertEquals(12, oSubplot.getCell(12).getY());
      assertEquals(0, oSubplot.getCell(13).getX()); assertEquals(13, oSubplot.getCell(13).getY());
      assertEquals(0, oSubplot.getCell(14).getX()); assertEquals(14, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(0, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(1, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(2, oSubplot.getCell(17).getY());
      assertEquals(2, oSubplot.getCell(18).getX()); assertEquals(3, oSubplot.getCell(18).getY());
      assertEquals(2, oSubplot.getCell(19).getX()); assertEquals(4, oSubplot.getCell(19).getY());
      assertEquals(2, oSubplot.getCell(20).getX()); assertEquals(5, oSubplot.getCell(20).getY());
      assertEquals(2, oSubplot.getCell(21).getX()); assertEquals(6, oSubplot.getCell(21).getY());
      assertEquals(2, oSubplot.getCell(22).getX()); assertEquals(7, oSubplot.getCell(22).getY());
      assertEquals(2, oSubplot.getCell(23).getX()); assertEquals(8, oSubplot.getCell(23).getY());
      assertEquals(2, oSubplot.getCell(24).getX()); assertEquals(9, oSubplot.getCell(24).getY());
      assertEquals(2, oSubplot.getCell(25).getX()); assertEquals(10, oSubplot.getCell(25).getY());
      assertEquals(2, oSubplot.getCell(26).getX()); assertEquals(11, oSubplot.getCell(26).getY());
      assertEquals(2, oSubplot.getCell(27).getX()); assertEquals(12, oSubplot.getCell(27).getY());
      assertEquals(2, oSubplot.getCell(28).getX()); assertEquals(13, oSubplot.getCell(28).getY());
      assertEquals(2, oSubplot.getCell(29).getX()); assertEquals(14, oSubplot.getCell(29).getY());

      oSubplot = (Subplot) oOutput.mp_oDetailedOutputSubplots.get(1);
      assertEquals("plot 3", oSubplot.getSubplotName());
      assertEquals(72, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(1, oSubplot.getCell(6).getX()); assertEquals(0, oSubplot.getCell(6).getY());
      assertEquals(1, oSubplot.getCell(7).getX()); assertEquals(1, oSubplot.getCell(7).getY());
      assertEquals(1, oSubplot.getCell(8).getX()); assertEquals(2, oSubplot.getCell(8).getY());
      assertEquals(1, oSubplot.getCell(9).getX()); assertEquals(3, oSubplot.getCell(9).getY());
      assertEquals(1, oSubplot.getCell(10).getX()); assertEquals(4, oSubplot.getCell(10).getY());
      assertEquals(1, oSubplot.getCell(11).getX()); assertEquals(5, oSubplot.getCell(11).getY());
      assertEquals(2, oSubplot.getCell(12).getX()); assertEquals(0, oSubplot.getCell(12).getY());
      assertEquals(2, oSubplot.getCell(13).getX()); assertEquals(1, oSubplot.getCell(13).getY());
      assertEquals(2, oSubplot.getCell(14).getX()); assertEquals(2, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(3, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(4, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(5, oSubplot.getCell(17).getY());
      assertEquals(3, oSubplot.getCell(18).getX()); assertEquals(0, oSubplot.getCell(18).getY());
      assertEquals(3, oSubplot.getCell(19).getX()); assertEquals(1, oSubplot.getCell(19).getY());
      assertEquals(3, oSubplot.getCell(20).getX()); assertEquals(2, oSubplot.getCell(20).getY());
      assertEquals(3, oSubplot.getCell(21).getX()); assertEquals(3, oSubplot.getCell(21).getY());
      assertEquals(3, oSubplot.getCell(22).getX()); assertEquals(4, oSubplot.getCell(22).getY());
      assertEquals(3, oSubplot.getCell(23).getX()); assertEquals(5, oSubplot.getCell(23).getY());
      assertEquals(4, oSubplot.getCell(24).getX()); assertEquals(0, oSubplot.getCell(24).getY());
      assertEquals(4, oSubplot.getCell(25).getX()); assertEquals(1, oSubplot.getCell(25).getY());
      assertEquals(4, oSubplot.getCell(26).getX()); assertEquals(2, oSubplot.getCell(26).getY());
      assertEquals(4, oSubplot.getCell(27).getX()); assertEquals(3, oSubplot.getCell(27).getY());
      assertEquals(4, oSubplot.getCell(28).getX()); assertEquals(4, oSubplot.getCell(28).getY());
      assertEquals(4, oSubplot.getCell(29).getX()); assertEquals(5, oSubplot.getCell(29).getY());
      assertEquals(5, oSubplot.getCell(30).getX()); assertEquals(0, oSubplot.getCell(30).getY());
      assertEquals(5, oSubplot.getCell(31).getX()); assertEquals(1, oSubplot.getCell(31).getY());
      assertEquals(5, oSubplot.getCell(32).getX()); assertEquals(2, oSubplot.getCell(32).getY());
      assertEquals(5, oSubplot.getCell(33).getX()); assertEquals(3, oSubplot.getCell(33).getY());
      assertEquals(5, oSubplot.getCell(34).getX()); assertEquals(4, oSubplot.getCell(34).getY());
      assertEquals(5, oSubplot.getCell(35).getX()); assertEquals(5, oSubplot.getCell(35).getY());
      assertEquals(6, oSubplot.getCell(36).getX()); assertEquals(0, oSubplot.getCell(36).getY());
      assertEquals(6, oSubplot.getCell(37).getX()); assertEquals(1, oSubplot.getCell(37).getY());
      assertEquals(6, oSubplot.getCell(38).getX()); assertEquals(2, oSubplot.getCell(38).getY());
      assertEquals(6, oSubplot.getCell(39).getX()); assertEquals(3, oSubplot.getCell(39).getY());
      assertEquals(6, oSubplot.getCell(40).getX()); assertEquals(4, oSubplot.getCell(40).getY());
      assertEquals(6, oSubplot.getCell(41).getX()); assertEquals(5, oSubplot.getCell(41).getY());
      assertEquals(7, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(7, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(7, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(7, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(7, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(7, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());
      assertEquals(8, oSubplot.getCell(48).getX()); assertEquals(0, oSubplot.getCell(48).getY());
      assertEquals(8, oSubplot.getCell(49).getX()); assertEquals(1, oSubplot.getCell(49).getY());
      assertEquals(8, oSubplot.getCell(50).getX()); assertEquals(2, oSubplot.getCell(50).getY());
      assertEquals(8, oSubplot.getCell(51).getX()); assertEquals(3, oSubplot.getCell(51).getY());
      assertEquals(8, oSubplot.getCell(52).getX()); assertEquals(4, oSubplot.getCell(52).getY());
      assertEquals(8, oSubplot.getCell(53).getX()); assertEquals(5, oSubplot.getCell(53).getY());
      assertEquals(9, oSubplot.getCell(54).getX()); assertEquals(0, oSubplot.getCell(54).getY());
      assertEquals(9, oSubplot.getCell(55).getX()); assertEquals(1, oSubplot.getCell(55).getY());
      assertEquals(9, oSubplot.getCell(56).getX()); assertEquals(2, oSubplot.getCell(56).getY());
      assertEquals(9, oSubplot.getCell(57).getX()); assertEquals(3, oSubplot.getCell(57).getY());
      assertEquals(9, oSubplot.getCell(58).getX()); assertEquals(4, oSubplot.getCell(58).getY());
      assertEquals(9, oSubplot.getCell(59).getX()); assertEquals(5, oSubplot.getCell(59).getY());
      assertEquals(10, oSubplot.getCell(60).getX()); assertEquals(0, oSubplot.getCell(60).getY());
      assertEquals(10, oSubplot.getCell(61).getX()); assertEquals(1, oSubplot.getCell(61).getY());
      assertEquals(10, oSubplot.getCell(62).getX()); assertEquals(2, oSubplot.getCell(62).getY());
      assertEquals(10, oSubplot.getCell(63).getX()); assertEquals(3, oSubplot.getCell(63).getY());
      assertEquals(10, oSubplot.getCell(64).getX()); assertEquals(4, oSubplot.getCell(64).getY());
      assertEquals(10, oSubplot.getCell(65).getX()); assertEquals(5, oSubplot.getCell(65).getY());
      assertEquals(11, oSubplot.getCell(66).getX()); assertEquals(0, oSubplot.getCell(66).getY());
      assertEquals(11, oSubplot.getCell(67).getX()); assertEquals(1, oSubplot.getCell(67).getY());
      assertEquals(11, oSubplot.getCell(68).getX()); assertEquals(2, oSubplot.getCell(68).getY());
      assertEquals(11, oSubplot.getCell(69).getX()); assertEquals(3, oSubplot.getCell(69).getY());
      assertEquals(11, oSubplot.getCell(70).getX()); assertEquals(4, oSubplot.getCell(70).getY());
      assertEquals(11, oSubplot.getCell(71).getX()); assertEquals(5, oSubplot.getCell(71).getY());

      
      System.out.println("ChangeOfPlotResolution test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML rundata reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  
  /**
   * Test two rundata settings for equality.
   * @param o1 First settings to test.
   * @param o2 Second settings to test.
   * @return True if all settings are the same, false if not.
   */
  private boolean testEquals(DetailedGridSettings o1, DetailedGridSettings o2) {
    if (o1.getNumberOfBools() != o2.getNumberOfBools() ||
        o1.getNumberOfFloats() != o2.getNumberOfFloats() ||
        o1.getNumberOfChars() != o2.getNumberOfChars() ||
        o1.getNumberOfInts() != o2.getNumberOfInts() ||
        o1.getNumberOfPackageBools() != o2.getNumberOfPackageBools() ||
        o1.getNumberOfPackageFloats() != o2.getNumberOfPackageFloats() ||
        o1.getNumberOfPackageChars() != o2.getNumberOfPackageChars() ||
        o1.getNumberOfPackageInts() != o2.getNumberOfPackageInts() ||
        o1.getSaveFrequency() != o2.getSaveFrequency() ||
        o1.getName() != o2.getName()) {
      return false;
    }

    int i;
    for (i = 0; i < o1.getNumberOfBools(); i++) {
      if (o1.getBool(i) != o2.getBool(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfChars(); i++) {
      if (o1.getChar(i) != o2.getChar(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfFloats(); i++) {
      if (o1.getFloat(i) != o2.getFloat(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfInts(); i++) {
      if (o1.getInt(i) != o2.getInt(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfPackageBools(); i++) {
      if (o1.getPackageBool(i) != o2.getPackageBool(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfPackageChars(); i++) {
      if (o1.getPackageChar(i) != o2.getPackageChar(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfPackageFloats(); i++) {
      if (o1.getPackageFloat(i) != o2.getPackageFloat(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfPackageInts(); i++) {
      if (o1.getPackageInt(i) != o2.getPackageInt(i)) {
        return false;
      }
    }

    return true;
  }
  
  /**
   * Test two rundata settings for equality.
   * @param o1 First settings to test.
   * @param o2 Second settings to test.
   * @return True if all settings are the same, false if not.
   */
  private boolean testEquals(DetailedTreeSettings o1, DetailedTreeSettings o2) {
    if (o1.getNumberOfBools() != o2.getNumberOfBools() ||
        o1.getNumberOfFloats() != o2.getNumberOfFloats() ||
        o1.getNumberOfChars() != o2.getNumberOfChars() ||
        o1.getNumberOfInts() != o2.getNumberOfInts() ||
        o1.getSaveFrequency() != o2.getSaveFrequency() ||
        o1.getSpecies() != o2.getSpecies() ||
        o1.getType() != o2.getType()) {
      return false;
    }

    int i;
    for (i = 0; i < o1.getNumberOfBools(); i++) {
      if (o1.getBool(i) != o2.getBool(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfChars(); i++) {
      if (o1.getChar(i) != o2.getChar(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfFloats(); i++) {
      if (o1.getFloat(i) != o2.getFloat(i)) {
        return false;
      }
    }

    for (i = 0; i < o1.getNumberOfInts(); i++) {
      if (o1.getInt(i) != o2.getInt(i)) {
        return false;
      }
    }

    return true;
  }


  /**
   * This writes an XML file to test value setting.  This file contains
   * detailed output only.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\testfile1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot - so the subplot cells can be set up
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");

      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Hybrid_Spruce\"/>");
      oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
      oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
      oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
      oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("</trees>");

      //Include an empty behavior list - this triggers setup
      oOut.write("<behaviorList>");
      oOut.write("</behaviorList>");

      oOut.write("<Output>");

      //Straight-up - one per data type
      oOut.write("<ou_filename>OutputSizeTest</ou_filename>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>species 1 test float 1</ou_float>");
      oOut.write("<ou_char>species 1 test char 1</ou_char>");
      oOut.write("<ou_int>species 1 test int 1</ou_int>");
      oOut.write("<ou_bool>species 1 test bool 1</ou_bool>");
      oOut.write("</ou_treeInfo>");

      //Multiples of each - not grouped together by type
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>2</ou_saveFreq>");
      oOut.write("<ou_float>species 2 test float 1</ou_float>");
      oOut.write("<ou_char>species 2 test char 1</ou_char>");
      oOut.write("<ou_int>species 2 test int 1</ou_int>");
      oOut.write("<ou_bool>species 2 test bool 1</ou_bool>");
      oOut.write("<ou_float>species 2 test float 2</ou_float>");
      oOut.write("<ou_char>species 2 test char 2</ou_char>");
      oOut.write("<ou_int>species 2 test int 2</ou_int>");
      oOut.write("<ou_bool>species 2 test bool 2</ou_bool>");
      oOut.write("</ou_treeInfo>");

      //One char only
      oOut.write("<ou_treeInfo species=\"Amabilis_Fir\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>3</ou_saveFreq>");
      oOut.write("<ou_char>species 3 test char 1</ou_char>");
      oOut.write("</ou_treeInfo>");

      //One float only
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>4</ou_saveFreq>");
      oOut.write("<ou_float>species 4 test float 1</ou_float>");
      oOut.write("</ou_treeInfo>");

      //One bool only
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>5</ou_saveFreq>");
      oOut.write("<ou_bool>species 5 test bool 1</ou_bool>");
      oOut.write("</ou_treeInfo>");

      //One int only
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"sapling\">");
      oOut.write("<ou_saveFreq>6</ou_saveFreq>");
      oOut.write("<ou_int>species 6 test int 1</ou_int>");
      oOut.write("</ou_treeInfo>");
      
      //Dead trees
      //One per - do for each dead reason code
      //Harvest
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"harvest\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.HARVEST + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.HARVEST + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.HARVEST + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.HARVEST + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Natural
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"natural\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.NATURAL + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.NATURAL + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.NATURAL + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.NATURAL + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Insects
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"insects\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.INSECTS + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.INSECTS + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.INSECTS + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.INSECTS + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Disease
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"disease\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.DISEASE + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.DISEASE + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.DISEASE + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.DISEASE + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Fire
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"fire\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.FIRE + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.FIRE + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.FIRE + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.FIRE + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Storm
      oOut.write("<ou_deadTreeInfo species=\"Western_Hemlock\" type=\"sapling\" code=\"storm\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>dead species 1 test float 1 c " + OutputBehaviors.STORM + "</ou_float>");
      oOut.write("<ou_char>dead species 1 test char 1 c " + OutputBehaviors.STORM + "</ou_char>");
      oOut.write("<ou_int>dead species 1 test int 1 c " + OutputBehaviors.STORM + "</ou_int>");
      oOut.write("<ou_bool>dead species 1 test bool 1 c " + OutputBehaviors.STORM + "</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");
      
      //Multiples of each - not grouped together by type
      oOut.write("<ou_deadTreeInfo species=\"Western_Redcedar\" type=\"sapling\" code=\"storm\">");
      oOut.write("<ou_saveFreq>2</ou_saveFreq>");
      oOut.write("<ou_float>dead species 2 test float 1</ou_float>");
      oOut.write("<ou_char>dead species 2 test char 1</ou_char>");
      oOut.write("<ou_int>dead species 2 test int 1</ou_int>");
      oOut.write("<ou_bool>dead species 2 test bool 1</ou_bool>");
      oOut.write("<ou_float>dead species 2 test float 2</ou_float>");
      oOut.write("<ou_char>dead species 2 test char 2</ou_char>");
      oOut.write("<ou_int>dead species 2 test int 2</ou_int>");
      oOut.write("<ou_bool>dead species 2 test bool 2</ou_bool>");
      oOut.write("</ou_deadTreeInfo>");

      //Now grids - same for all
      //Straight-up - one per data type - no package
      oOut.write("<ou_gridInfo gridName=\"grid 1\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>grid 1 test float 1</ou_float>");
      oOut.write("<ou_char>grid 1 test char 1</ou_char>");
      oOut.write("<ou_int>grid 1 test int 1</ou_int>");
      oOut.write("<ou_bool>grid 1 test bool 1</ou_bool>");
      oOut.write("</ou_gridInfo>");

      //Multiples of each - not grouped together by type
      oOut.write("<ou_gridInfo gridName=\"grid 2\">");
      oOut.write("<ou_saveFreq>2</ou_saveFreq>");
      oOut.write("<ou_float>grid 2 test float 1</ou_float>");
      oOut.write("<ou_char>grid 2 test char 1</ou_char>");
      oOut.write("<ou_int>grid 2 test int 1</ou_int>");
      oOut.write("<ou_bool>grid 2 test bool 1</ou_bool>");
      oOut.write("<ou_float>grid 2 test float 2</ou_float>");
      oOut.write("<ou_char>grid 2 test char 2</ou_char>");
      oOut.write("<ou_int>grid 2 test int 2</ou_int>");
      oOut.write("<ou_bool>grid 2 test bool 2</ou_bool>");
      oOut.write("</ou_gridInfo>");

      //One char only
      oOut.write("<ou_gridInfo gridName=\"grid 3\">");
      oOut.write("<ou_saveFreq>3</ou_saveFreq>");
      oOut.write("<ou_char>grid 3 test char 1</ou_char>");
      oOut.write("</ou_gridInfo>");

      //One float only
      oOut.write("<ou_gridInfo gridName=\"grid 4\">");
      oOut.write("<ou_saveFreq>4</ou_saveFreq>");
      oOut.write("<ou_float>grid 4 test float 1</ou_float>");
      oOut.write("</ou_gridInfo>");

      //One bool only
      oOut.write("<ou_gridInfo gridName=\"grid 5\">");
      oOut.write("<ou_saveFreq>5</ou_saveFreq>");
      oOut.write("<ou_bool>grid 5 test bool 1</ou_bool>");
      oOut.write("</ou_gridInfo>");

      //One int only
      oOut.write("<ou_gridInfo gridName=\"grid 6\">");
      oOut.write("<ou_saveFreq>6</ou_saveFreq>");
      oOut.write("<ou_int>grid 6 test int 1</ou_int>");
      oOut.write("</ou_gridInfo>");

      //One per data type, both package and regular
      oOut.write("<ou_gridInfo gridName=\"grid 7\">");
      oOut.write("<ou_saveFreq>7</ou_saveFreq>");
      oOut.write("<ou_float>grid 7 test float 1</ou_float>");
      oOut.write("<ou_char>grid 7 test char 1</ou_char>");
      oOut.write("<ou_int>grid 7 test int 1</ou_int>");
      oOut.write("<ou_bool>grid 7 test bool 1</ou_bool>");
      oOut.write("<ou_packageInt>grid 7 test package int 1</ou_packageInt>");
      oOut.write(
          "<ou_packageFloat>grid 7 test package float 1</ou_packageFloat>");
      oOut.write("<ou_packageChar>grid 7 test package char 1</ou_packageChar>");
      oOut.write("<ou_packageBool>grid 7 test package bool 1</ou_packageBool>");
      oOut.write("</ou_gridInfo>");

      //Multiples of each, both package and regular
      oOut.write("<ou_gridInfo gridName=\"grid 8\">");
      oOut.write("<ou_saveFreq>8</ou_saveFreq>");
      oOut.write("<ou_float>grid 8 test float 1</ou_float>");
      oOut.write("<ou_char>grid 8 test char 1</ou_char>");
      oOut.write("<ou_int>grid 8 test int 1</ou_int>");
      oOut.write("<ou_bool>grid 8 test bool 1</ou_bool>");
      oOut.write("<ou_float>grid 8 test float 2</ou_float>");
      oOut.write("<ou_char>grid 8 test char 2</ou_char>");
      oOut.write("<ou_int>grid 8 test int 2</ou_int>");
      oOut.write("<ou_bool>grid 8 test bool 2</ou_bool>");
      oOut.write("<ou_packageInt>grid 8 test package int 1</ou_packageInt>");
      oOut.write("<ou_packageInt>grid 8 test package int 2</ou_packageInt>");
      oOut.write(
          "<ou_packageFloat>grid 8 test package float 1</ou_packageFloat>");
      oOut.write(
          "<ou_packageFloat>grid 8 test package float 2</ou_packageFloat>");
      oOut.write(
          "<ou_packageFloat>grid 8 test package float 3</ou_packageFloat>");
      oOut.write("<ou_packageChar>grid 8 test package char 1</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 8 test package char 2</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 8 test package char 3</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 8 test package char 4</ou_packageChar>");
      oOut.write("<ou_packageBool>grid 8 test package bool 1</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 8 test package bool 2</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 8 test package bool 3</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 8 test package bool 4</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 8 test package bool 5</ou_packageBool>");
      oOut.write("</ou_gridInfo>");

      //One per data type, package only
      oOut.write("<ou_gridInfo gridName=\"grid 9\">");
      oOut.write("<ou_saveFreq>9</ou_saveFreq>");
      oOut.write("<ou_packageInt>grid 9 test package int 1</ou_packageInt>");
      oOut.write(
          "<ou_packageFloat>grid 9 test package float 1</ou_packageFloat>");
      oOut.write("<ou_packageChar>grid 9 test package char 1</ou_packageChar>");
      oOut.write("<ou_packageBool>grid 9 test package bool 1</ou_packageBool>");
      oOut.write("</ou_gridInfo>");

      //Multiples of each, package only
      oOut.write("<ou_gridInfo gridName=\"grid 10\">");
      oOut.write("<ou_saveFreq>10</ou_saveFreq>");
      oOut.write("<ou_packageInt>grid 10 test package int 1</ou_packageInt>");
      oOut.write("<ou_packageInt>grid 10 test package int 2</ou_packageInt>");
      oOut.write(
          "<ou_packageFloat>grid 10 test package float 1</ou_packageFloat>");
      oOut.write(
          "<ou_packageFloat>grid 10 test package float 2</ou_packageFloat>");
      oOut.write(
          "<ou_packageFloat>grid 10 test package float 3</ou_packageFloat>");
      oOut.write("<ou_packageChar>grid 10 test package char 1</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 10 test package char 2</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 10 test package char 3</ou_packageChar>");
      oOut.write("<ou_packageChar>grid 10 test package char 4</ou_packageChar>");
      oOut.write("<ou_packageBool>grid 10 test package bool 1</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 10 test package bool 2</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 10 test package bool 3</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 10 test package bool 4</ou_packageBool>");
      oOut.write("<ou_packageBool>grid 10 test package bool 5</ou_packageBool>");
      oOut.write("</ou_gridInfo>");

      //Now subplots
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>test 1</ou_subplotName>");
      //One point
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");

      //5 points
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>test 2</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"6\" y=\"14\"/>");
      oOut.write("<po_point x=\"7\" y=\"15\"/>");
      oOut.write("<po_point x=\"8\" y=\"16\"/>");
      oOut.write("<po_point x=\"9\" y=\"17\"/>");
      oOut.write("<po_point x=\"10\" y=\"18\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");

      oOut.write("</Output>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.  This file contains
   * detailed output only.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile4() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>100</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("<plot_title>Default parameter file</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"Western_Hemlock\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Western_Redcedar\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstantGLI</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Output</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("</allometry>");
      oOut.write("<ConstantGLI1>");
      oOut.write("<li_constGLI>100.0</li_constGLI>");
      oOut.write("</ConstantGLI1>");
      oOut.write("<ShortOutput>");
      oOut.write("<so_filename>C:\\lkjh.out</so_filename>");
      oOut.write("<so_treeTypeInfo type=\"Sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"false\"/>");
      oOut.write("<so_saveRDN save=\"false\"/>");
      oOut.write("<so_saveADN save=\"false\"/>");
      oOut.write("</so_treeTypeInfo>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 1</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"20\" y=\"20\"/>");
      oOut.write("<po_point x=\"20\" y=\"21\"/>");
      oOut.write("<po_point x=\"20\" y=\"22\"/>");
      oOut.write("<po_point x=\"20\" y=\"23\"/>");
      oOut.write("<po_point x=\"20\" y=\"24\"/>");
      oOut.write("<po_point x=\"21\" y=\"20\"/>");
      oOut.write("<po_point x=\"21\" y=\"21\"/>");
      oOut.write("<po_point x=\"21\" y=\"22\"/>");
      oOut.write("<po_point x=\"21\" y=\"23\"/>");
      oOut.write("<po_point x=\"21\" y=\"24\"/>");
      oOut.write("<po_point x=\"22\" y=\"20\"/>");
      oOut.write("<po_point x=\"22\" y=\"21\"/>");
      oOut.write("<po_point x=\"22\" y=\"22\"/>");
      oOut.write("<po_point x=\"22\" y=\"23\"/>");
      oOut.write("<po_point x=\"22\" y=\"24\"/>");
      oOut.write("<po_point x=\"23\" y=\"20\"/>");
      oOut.write("<po_point x=\"23\" y=\"21\"/>");
      oOut.write("<po_point x=\"23\" y=\"22\"/>");
      oOut.write("<po_point x=\"23\" y=\"23\"/>");
      oOut.write("<po_point x=\"23\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"20\"/>");
      oOut.write("<po_point x=\"24\" y=\"21\"/>");
      oOut.write("<po_point x=\"24\" y=\"22\"/>");
      oOut.write("<po_point x=\"24\" y=\"23\"/>");
      oOut.write("<po_point x=\"24\" y=\"24\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 2</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"0\" y=\"6\"/>");
      oOut.write("<po_point x=\"0\" y=\"7\"/>");
      oOut.write("<po_point x=\"0\" y=\"8\"/>");
      oOut.write("<po_point x=\"0\" y=\"9\"/>");
      oOut.write("<po_point x=\"0\" y=\"10\"/>");
      oOut.write("<po_point x=\"0\" y=\"11\"/>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("<po_point x=\"0\" y=\"13\"/>");
      oOut.write("<po_point x=\"0\" y=\"14\"/>");
      oOut.write("<po_point x=\"0\" y=\"15\"/>");
      oOut.write("<po_point x=\"0\" y=\"16\"/>");
      oOut.write("<po_point x=\"0\" y=\"17\"/>");
      oOut.write("<po_point x=\"0\" y=\"24\"/>");
      oOut.write("<po_point x=\"1\" y=\"24\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"6\"/>");
      oOut.write("<po_point x=\"2\" y=\"7\"/>");
      oOut.write("<po_point x=\"2\" y=\"8\"/>");
      oOut.write("<po_point x=\"2\" y=\"9\"/>");
      oOut.write("<po_point x=\"2\" y=\"10\"/>");
      oOut.write("<po_point x=\"2\" y=\"11\"/>");
      oOut.write("<po_point x=\"2\" y=\"12\"/>");
      oOut.write("<po_point x=\"2\" y=\"13\"/>");
      oOut.write("<po_point x=\"2\" y=\"14\"/>");
      oOut.write("<po_point x=\"2\" y=\"15\"/>");
      oOut.write("<po_point x=\"2\" y=\"16\"/>");
      oOut.write("<po_point x=\"2\" y=\"17\"/>");
      oOut.write("<po_point x=\"2\" y=\"24\"/>");
      oOut.write("<po_point x=\"3\" y=\"24\"/>");
      oOut.write("<po_point x=\"4\" y=\"24\"/>");
      oOut.write("<po_point x=\"5\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"0\"/>");
      oOut.write("<po_point x=\"24\" y=\"1\"/>");
      oOut.write("<po_point x=\"24\" y=\"2\"/>");
      oOut.write("<po_point x=\"24\" y=\"3\"/>");
      oOut.write("<po_point x=\"24\" y=\"4\"/>");
      oOut.write("<po_point x=\"24\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 3</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"1\" y=\"0\"/>");
      oOut.write("<po_point x=\"1\" y=\"1\"/>");
      oOut.write("<po_point x=\"1\" y=\"2\"/>");
      oOut.write("<po_point x=\"1\" y=\"3\"/>");
      oOut.write("<po_point x=\"1\" y=\"4\"/>");
      oOut.write("<po_point x=\"1\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"3\" y=\"0\"/>");
      oOut.write("<po_point x=\"3\" y=\"1\"/>");
      oOut.write("<po_point x=\"3\" y=\"2\"/>");
      oOut.write("<po_point x=\"3\" y=\"3\"/>");
      oOut.write("<po_point x=\"3\" y=\"4\"/>");
      oOut.write("<po_point x=\"3\" y=\"5\"/>");
      oOut.write("<po_point x=\"4\" y=\"0\"/>");
      oOut.write("<po_point x=\"4\" y=\"1\"/>");
      oOut.write("<po_point x=\"4\" y=\"2\"/>");
      oOut.write("<po_point x=\"4\" y=\"3\"/>");
      oOut.write("<po_point x=\"4\" y=\"4\"/>");
      oOut.write("<po_point x=\"4\" y=\"5\"/>");
      oOut.write("<po_point x=\"5\" y=\"0\"/>");
      oOut.write("<po_point x=\"5\" y=\"1\"/>");
      oOut.write("<po_point x=\"5\" y=\"2\"/>");
      oOut.write("<po_point x=\"5\" y=\"3\"/>");
      oOut.write("<po_point x=\"5\" y=\"4\"/>");
      oOut.write("<po_point x=\"5\" y=\"5\"/>");
      oOut.write("<po_point x=\"6\" y=\"0\"/>");
      oOut.write("<po_point x=\"6\" y=\"1\"/>");
      oOut.write("<po_point x=\"6\" y=\"2\"/>");
      oOut.write("<po_point x=\"6\" y=\"3\"/>");
      oOut.write("<po_point x=\"6\" y=\"4\"/>");
      oOut.write("<po_point x=\"6\" y=\"5\"/>");
      oOut.write("<po_point x=\"7\" y=\"0\"/>");
      oOut.write("<po_point x=\"7\" y=\"1\"/>");
      oOut.write("<po_point x=\"7\" y=\"2\"/>");
      oOut.write("<po_point x=\"7\" y=\"3\"/>");
      oOut.write("<po_point x=\"7\" y=\"4\"/>");
      oOut.write("<po_point x=\"7\" y=\"5\"/>");
      oOut.write("<po_point x=\"8\" y=\"0\"/>");
      oOut.write("<po_point x=\"8\" y=\"1\"/>");
      oOut.write("<po_point x=\"8\" y=\"2\"/>");
      oOut.write("<po_point x=\"8\" y=\"3\"/>");
      oOut.write("<po_point x=\"8\" y=\"4\"/>");
      oOut.write("<po_point x=\"8\" y=\"5\"/>");
      oOut.write("<po_point x=\"9\" y=\"0\"/>");
      oOut.write("<po_point x=\"9\" y=\"1\"/>");
      oOut.write("<po_point x=\"9\" y=\"2\"/>");
      oOut.write("<po_point x=\"9\" y=\"3\"/>");
      oOut.write("<po_point x=\"9\" y=\"4\"/>");
      oOut.write("<po_point x=\"9\" y=\"5\"/>");
      oOut.write("<po_point x=\"10\" y=\"0\"/>");
      oOut.write("<po_point x=\"10\" y=\"1\"/>");
      oOut.write("<po_point x=\"10\" y=\"2\"/>");
      oOut.write("<po_point x=\"10\" y=\"3\"/>");
      oOut.write("<po_point x=\"10\" y=\"4\"/>");
      oOut.write("<po_point x=\"10\" y=\"5\"/>");
      oOut.write("<po_point x=\"11\" y=\"0\"/>");
      oOut.write("<po_point x=\"11\" y=\"1\"/>");
      oOut.write("<po_point x=\"11\" y=\"2\"/>");
      oOut.write("<po_point x=\"11\" y=\"3\"/>");
      oOut.write("<po_point x=\"11\" y=\"4\"/>");
      oOut.write("<po_point x=\"11\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("</ShortOutput>");
      oOut.write("<Output>");
      oOut.write("<ou_filename>C:\\lij.gz.tar</ou_filename>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 1</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"20\" y=\"20\"/>");
      oOut.write("<po_point x=\"20\" y=\"21\"/>");
      oOut.write("<po_point x=\"20\" y=\"22\"/>");
      oOut.write("<po_point x=\"20\" y=\"23\"/>");
      oOut.write("<po_point x=\"20\" y=\"24\"/>");
      oOut.write("<po_point x=\"21\" y=\"20\"/>");
      oOut.write("<po_point x=\"21\" y=\"21\"/>");
      oOut.write("<po_point x=\"21\" y=\"22\"/>");
      oOut.write("<po_point x=\"21\" y=\"23\"/>");
      oOut.write("<po_point x=\"21\" y=\"24\"/>");
      oOut.write("<po_point x=\"22\" y=\"20\"/>");
      oOut.write("<po_point x=\"22\" y=\"21\"/>");
      oOut.write("<po_point x=\"22\" y=\"22\"/>");
      oOut.write("<po_point x=\"22\" y=\"23\"/>");
      oOut.write("<po_point x=\"22\" y=\"24\"/>");
      oOut.write("<po_point x=\"23\" y=\"20\"/>");
      oOut.write("<po_point x=\"23\" y=\"21\"/>");
      oOut.write("<po_point x=\"23\" y=\"22\"/>");
      oOut.write("<po_point x=\"23\" y=\"23\"/>");
      oOut.write("<po_point x=\"23\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"20\"/>");
      oOut.write("<po_point x=\"24\" y=\"21\"/>");
      oOut.write("<po_point x=\"24\" y=\"22\"/>");
      oOut.write("<po_point x=\"24\" y=\"23\"/>");
      oOut.write("<po_point x=\"24\" y=\"24\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 2</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"0\" y=\"6\"/>");
      oOut.write("<po_point x=\"0\" y=\"7\"/>");
      oOut.write("<po_point x=\"0\" y=\"8\"/>");
      oOut.write("<po_point x=\"0\" y=\"9\"/>");
      oOut.write("<po_point x=\"0\" y=\"10\"/>");
      oOut.write("<po_point x=\"0\" y=\"11\"/>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("<po_point x=\"0\" y=\"13\"/>");
      oOut.write("<po_point x=\"0\" y=\"14\"/>");
      oOut.write("<po_point x=\"0\" y=\"15\"/>");
      oOut.write("<po_point x=\"0\" y=\"16\"/>");
      oOut.write("<po_point x=\"0\" y=\"17\"/>");
      oOut.write("<po_point x=\"0\" y=\"24\"/>");
      oOut.write("<po_point x=\"1\" y=\"24\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"6\"/>");
      oOut.write("<po_point x=\"2\" y=\"7\"/>");
      oOut.write("<po_point x=\"2\" y=\"8\"/>");
      oOut.write("<po_point x=\"2\" y=\"9\"/>");
      oOut.write("<po_point x=\"2\" y=\"10\"/>");
      oOut.write("<po_point x=\"2\" y=\"11\"/>");
      oOut.write("<po_point x=\"2\" y=\"12\"/>");
      oOut.write("<po_point x=\"2\" y=\"13\"/>");
      oOut.write("<po_point x=\"2\" y=\"14\"/>");
      oOut.write("<po_point x=\"2\" y=\"15\"/>");
      oOut.write("<po_point x=\"2\" y=\"16\"/>");
      oOut.write("<po_point x=\"2\" y=\"17\"/>");
      oOut.write("<po_point x=\"2\" y=\"24\"/>");
      oOut.write("<po_point x=\"3\" y=\"24\"/>");
      oOut.write("<po_point x=\"4\" y=\"24\"/>");
      oOut.write("<po_point x=\"5\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"0\"/>");
      oOut.write("<po_point x=\"24\" y=\"1\"/>");
      oOut.write("<po_point x=\"24\" y=\"2\"/>");
      oOut.write("<po_point x=\"24\" y=\"3\"/>");
      oOut.write("<po_point x=\"24\" y=\"4\"/>");
      oOut.write("<po_point x=\"24\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 3</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"1\" y=\"0\"/>");
      oOut.write("<po_point x=\"1\" y=\"1\"/>");
      oOut.write("<po_point x=\"1\" y=\"2\"/>");
      oOut.write("<po_point x=\"1\" y=\"3\"/>");
      oOut.write("<po_point x=\"1\" y=\"4\"/>");
      oOut.write("<po_point x=\"1\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"3\" y=\"0\"/>");
      oOut.write("<po_point x=\"3\" y=\"1\"/>");
      oOut.write("<po_point x=\"3\" y=\"2\"/>");
      oOut.write("<po_point x=\"3\" y=\"3\"/>");
      oOut.write("<po_point x=\"3\" y=\"4\"/>");
      oOut.write("<po_point x=\"3\" y=\"5\"/>");
      oOut.write("<po_point x=\"4\" y=\"0\"/>");
      oOut.write("<po_point x=\"4\" y=\"1\"/>");
      oOut.write("<po_point x=\"4\" y=\"2\"/>");
      oOut.write("<po_point x=\"4\" y=\"3\"/>");
      oOut.write("<po_point x=\"4\" y=\"4\"/>");
      oOut.write("<po_point x=\"4\" y=\"5\"/>");
      oOut.write("<po_point x=\"5\" y=\"0\"/>");
      oOut.write("<po_point x=\"5\" y=\"1\"/>");
      oOut.write("<po_point x=\"5\" y=\"2\"/>");
      oOut.write("<po_point x=\"5\" y=\"3\"/>");
      oOut.write("<po_point x=\"5\" y=\"4\"/>");
      oOut.write("<po_point x=\"5\" y=\"5\"/>");
      oOut.write("<po_point x=\"6\" y=\"0\"/>");
      oOut.write("<po_point x=\"6\" y=\"1\"/>");
      oOut.write("<po_point x=\"6\" y=\"2\"/>");
      oOut.write("<po_point x=\"6\" y=\"3\"/>");
      oOut.write("<po_point x=\"6\" y=\"4\"/>");
      oOut.write("<po_point x=\"6\" y=\"5\"/>");
      oOut.write("<po_point x=\"7\" y=\"0\"/>");
      oOut.write("<po_point x=\"7\" y=\"1\"/>");
      oOut.write("<po_point x=\"7\" y=\"2\"/>");
      oOut.write("<po_point x=\"7\" y=\"3\"/>");
      oOut.write("<po_point x=\"7\" y=\"4\"/>");
      oOut.write("<po_point x=\"7\" y=\"5\"/>");
      oOut.write("<po_point x=\"8\" y=\"0\"/>");
      oOut.write("<po_point x=\"8\" y=\"1\"/>");
      oOut.write("<po_point x=\"8\" y=\"2\"/>");
      oOut.write("<po_point x=\"8\" y=\"3\"/>");
      oOut.write("<po_point x=\"8\" y=\"4\"/>");
      oOut.write("<po_point x=\"8\" y=\"5\"/>");
      oOut.write("<po_point x=\"9\" y=\"0\"/>");
      oOut.write("<po_point x=\"9\" y=\"1\"/>");
      oOut.write("<po_point x=\"9\" y=\"2\"/>");
      oOut.write("<po_point x=\"9\" y=\"3\"/>");
      oOut.write("<po_point x=\"9\" y=\"4\"/>");
      oOut.write("<po_point x=\"9\" y=\"5\"/>");
      oOut.write("<po_point x=\"10\" y=\"0\"/>");
      oOut.write("<po_point x=\"10\" y=\"1\"/>");
      oOut.write("<po_point x=\"10\" y=\"2\"/>");
      oOut.write("<po_point x=\"10\" y=\"3\"/>");
      oOut.write("<po_point x=\"10\" y=\"4\"/>");
      oOut.write("<po_point x=\"10\" y=\"5\"/>");
      oOut.write("<po_point x=\"11\" y=\"0\"/>");
      oOut.write("<po_point x=\"11\" y=\"1\"/>");
      oOut.write("<po_point x=\"11\" y=\"2\"/>");
      oOut.write("<po_point x=\"11\" y=\"3\"/>");
      oOut.write("<po_point x=\"11\" y=\"4\"/>");
      oOut.write("<po_point x=\"11\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("</Output>");
      oOut.write("</paramFile>");


      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.  This file contains short
   * output settings only, for all (normal) types with a mix of true, false,
   * and absent.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile5() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>500</timesteps>");
      oOut.write("<rt_timestep>0</rt_timestep>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>42.5</plot_latitude>");
      oOut.write("<plot_precip_mm_yr>1000.0</plot_precip_mm_yr>");
      oOut.write("<plot_temp_C>8.0</plot_temp_C>");
      oOut.write("<plot_title>Default Location</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ABBA\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ABBA\">12.7</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"ABBA\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ABBA\" type=\"Seedling\" />");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ABBA\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("<tr_whatSaplingHeightDiam>");
      oOut.write("<tr_wsahdVal species=\"ABBA\">0</tr_wsahdVal>");
      oOut.write("</tr_whatSaplingHeightDiam>");
      oOut.write("<tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_wsehdVal species=\"ABBA\">0</tr_wsehdVal>");
      oOut.write("</tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_wacrdVal species=\"ABBA\">0</tr_wacrdVal>");
      oOut.write("</tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_wscrdVal species=\"ABBA\">0</tr_wscrdVal>");
      oOut.write("</tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_wachhVal species=\"ABBA\">0</tr_wachhVal>");
      oOut.write("</tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_wschhVal species=\"ABBA\">0</tr_wschhVal>");
      oOut.write("</tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ABBA\">30.0</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ABBA\">0.1</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ABBA\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ABBA\">0.4</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ABBA\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ABBA\">1.0</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_interceptDiam10ToDBH>");
      oOut.write("<tr_idtdVal species=\"ABBA\">0.0</tr_idtdVal>");
      oOut.write("</tr_interceptDiam10ToDBH>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ABBA\">0.063</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ABBA\">0.03</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("</allometry>");

      oOut.write("<ShortOutput>");

      //Set all save options to true for all recognized types
      oOut.write("<so_filename>testfile</so_filename>");

      oOut.write("<so_treeTypeInfo type=\"sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"false\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"adult\">");
      oOut.write("<so_saveRBA save=\"false\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("</ShortOutput>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }


  
  /**
   * Writes a file with no output settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileNoOutput() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

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
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
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
   * Writes a real-type file, so we can work with actual grids.
   * @throws ModelException if there is a problem writing the file.
   * @return String Filename just written.
   */
  private String writeXMLFile2() throws ModelException {
    try {
      String sFileName = "\\testfile1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>100</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Hybrid_Spruce\"/>");
      oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
      oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
      oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
      oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"Western_Hemlock\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Western_Redcedar\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Amabilis_Fir\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Subalpine_Fir\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Hybrid_Spruce\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Lodgepole_Pine\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Trembling_Aspen\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Black_Cottonwood\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Paper_Birch\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Amabilis_Fir\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Subalpine_Fir\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Hybrid_Spruce\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Lodgepole_Pine\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Black_Cottonwood\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Paper_Birch\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Windstorm</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>StochasticMortality</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>4</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Substrate</behaviorName>");
      oOut.write("<version>2.1</version>");
      oOut.write("<listPosition>5</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>6</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>GapDisperse</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>7</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>GapSubstrateSeedSurvival</behaviorName>");
      oOut.write("<version>1.1</version>");
      oOut.write("<listPosition>8</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seed\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Establishment</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>9</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seed\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Output</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>10</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Amabilis_Fir\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Subalpine_Fir\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Hybrid_Spruce\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Lodgepole_Pine\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Trembling_Aspen\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Black_Cottonwood\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Paper_Birch\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("<tr_whatSaplingHeightDiam>");
      oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Amabilis_Fir\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Subalpine_Fir\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Hybrid_Spruce\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Lodgepole_Pine\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Trembling_Aspen\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Black_Cottonwood\">0</tr_wsahdVal>");
      oOut.write("<tr_wsahdVal species=\"Paper_Birch\">0</tr_wsahdVal>");
      oOut.write("</tr_whatSaplingHeightDiam>");
      oOut.write("<tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Amabilis_Fir\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Subalpine_Fir\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Hybrid_Spruce\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Lodgepole_Pine\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Trembling_Aspen\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Black_Cottonwood\">0</tr_wsehdVal>");
      oOut.write("<tr_wsehdVal species=\"Paper_Birch\">0</tr_wsehdVal>");
      oOut.write("</tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Amabilis_Fir\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Subalpine_Fir\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Hybrid_Spruce\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Lodgepole_Pine\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Trembling_Aspen\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Black_Cottonwood\">0</tr_wacrdVal>");
      oOut.write("<tr_wacrdVal species=\"Paper_Birch\">0</tr_wacrdVal>");
      oOut.write("</tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Amabilis_Fir\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Subalpine_Fir\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Hybrid_Spruce\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Lodgepole_Pine\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Trembling_Aspen\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Black_Cottonwood\">0</tr_wscrdVal>");
      oOut.write("<tr_wscrdVal species=\"Paper_Birch\">0</tr_wscrdVal>");
      oOut.write("</tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_wachhVal species=\"Western_Hemlock\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Amabilis_Fir\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Subalpine_Fir\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Hybrid_Spruce\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Lodgepole_Pine\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Trembling_Aspen\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Black_Cottonwood\">0</tr_wachhVal>");
      oOut.write("<tr_wachhVal species=\"Paper_Birch\">0</tr_wachhVal>");
      oOut.write("</tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_wschhVal species=\"Western_Hemlock\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Western_Redcedar\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Amabilis_Fir\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Subalpine_Fir\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Hybrid_Spruce\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Lodgepole_Pine\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Trembling_Aspen\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Black_Cottonwood\">0</tr_wschhVal>");
      oOut.write("<tr_wschhVal species=\"Paper_Birch\">0</tr_wschhVal>");
      oOut.write("</tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"Western_Hemlock\">39.48</tr_chVal>");
      oOut.write("<tr_chVal species=\"Western_Redcedar\">39.54</tr_chVal>");
      oOut.write("<tr_chVal species=\"Amabilis_Fir\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"Subalpine_Fir\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"Hybrid_Spruce\">45.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"Lodgepole_Pine\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"Trembling_Aspen\">39.14</tr_chVal>");
      oOut.write("<tr_chVal species=\"Black_Cottonwood\">39.47</tr_chVal>");
      oOut.write("<tr_chVal species=\"Paper_Birch\">33.18</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.0549</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0614</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Amabilis_Fir\">0.0242</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Subalpine_Fir\">0.0251</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Hybrid_Spruce\">0.0239</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Lodgepole_Pine\">0.0303</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Trembling_Aspen\">0.0328</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Black_Cottonwood\">0.0247</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"Paper_Birch\">0.0484</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"Western_Hemlock\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Western_Redcedar\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Amabilis_Fir\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Subalpine_Fir\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Hybrid_Spruce\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Lodgepole_Pine\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Trembling_Aspen\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Black_Cottonwood\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"Paper_Birch\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.389</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.368</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Amabilis_Fir\">0.464</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Subalpine_Fir\">0.454</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Hybrid_Spruce\">0.405</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Lodgepole_Pine\">0.201</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Trembling_Aspen\">0.301</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Black_Cottonwood\">0.42</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"Paper_Birch\">0.315</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"Western_Hemlock\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Amabilis_Fir\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Subalpine_Fir\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Hybrid_Spruce\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Lodgepole_Pine\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Trembling_Aspen\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Black_Cottonwood\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"Paper_Birch\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.8008</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.5944</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Amabilis_Fir\">0.7059</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Subalpine_Fir\">0.7776</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Hybrid_Spruce\">0.6933</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Lodgepole_Pine\">0.8014</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Trembling_Aspen\">0.7992</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Black_Cottonwood\">0.7926</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"Paper_Birch\">0.7803</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_interceptDiam10ToDBH>");
      oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Western_Redcedar\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Amabilis_Fir\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Subalpine_Fir\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Hybrid_Spruce\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Lodgepole_Pine\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Trembling_Aspen\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Black_Cottonwood\">0.0</tr_idtdVal>");
      oOut.write("<tr_idtdVal species=\"Paper_Birch\">0.0</tr_idtdVal>");
      oOut.write("</tr_interceptDiam10ToDBH>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0299</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0241</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Amabilis_Fir\">0.0263</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Subalpine_Fir\">0.0264</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Hybrid_Spruce\">0.0264</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Lodgepole_Pine\">0.0333</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Trembling_Aspen\">0.0352</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Black_Cottonwood\">0.0347</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"Paper_Birch\">0.0454</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.0269</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Amabilis_Fir\">0.02871</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Subalpine_Fir\">0.02815</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Hybrid_Spruce\">0.02871</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Lodgepole_Pine\">0.03224</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Trembling_Aspen\">0.04796</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Black_Cottonwood\">0.04681</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"Paper_Birch\">0.04101</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("</allometry>");
      oOut.write("<Windstorm1>");
      oOut.write("<ws_severityReturnInterval1>0.0</ws_severityReturnInterval1>");
      oOut.write("<ws_severityReturnInterval5>0.0</ws_severityReturnInterval5>");
      oOut.write("<ws_severityReturnInterval10>0.0</ws_severityReturnInterval10>");
      oOut.write("<ws_severityReturnInterval20>0.0</ws_severityReturnInterval20>");
      oOut.write("<ws_severityReturnInterval40>0.0</ws_severityReturnInterval40>");
      oOut.write("<ws_severityReturnInterval80>0.0</ws_severityReturnInterval80>");
      oOut.write("<ws_severityReturnInterval160>0.0</ws_severityReturnInterval160>");
      oOut.write("<ws_severityReturnInterval320>0.0</ws_severityReturnInterval320>");
      oOut.write("<ws_severityReturnInterval640>0.0</ws_severityReturnInterval640>");
      oOut.write("<ws_severityReturnInterval1280>0.0</ws_severityReturnInterval1280>");
      oOut.write("<ws_severityReturnInterval2560>0.0</ws_severityReturnInterval2560>");
      oOut.write("<ws_stmSSTPeriod>1.0</ws_stmSSTPeriod>");
      oOut.write("<ws_stmSineD>0.0</ws_stmSineD>");
      oOut.write("<ws_stmSineF>1.0</ws_stmSineF>");
      oOut.write("<ws_stmSineG>1.0</ws_stmSineG>");
      oOut.write("<ws_stmTrendSlopeM>0.0</ws_stmTrendSlopeM>");
      oOut.write("<ws_stmTrendInterceptI>1.0</ws_stmTrendInterceptI>");
      oOut.write("<ws_stmTSToStartStorms>0</ws_stmTSToStartStorms>");
      oOut.write("</Windstorm1>");
      oOut.write("<GeneralLight>");
      oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
      oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
      oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
      oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
      oOut.write("<li_lightExtinctionCoefficient>");
      oOut.write("<li_lecVal species=\"Western_Hemlock\">0.08</li_lecVal>");
      oOut.write("<li_lecVal species=\"Western_Redcedar\">0.091</li_lecVal>");
      oOut.write("<li_lecVal species=\"Amabilis_Fir\">0.059</li_lecVal>");
      oOut.write("<li_lecVal species=\"Subalpine_Fir\">0.092</li_lecVal>");
      oOut.write("<li_lecVal species=\"Hybrid_Spruce\">0.114</li_lecVal>");
      oOut.write("<li_lecVal species=\"Lodgepole_Pine\">0.135</li_lecVal>");
      oOut.write("<li_lecVal species=\"Trembling_Aspen\">0.206</li_lecVal>");
      oOut.write("<li_lecVal species=\"Black_Cottonwood\">0.143</li_lecVal>");
      oOut.write("<li_lecVal species=\"Paper_Birch\">0.058</li_lecVal>");
      oOut.write("</li_lightExtinctionCoefficient>");
      oOut.write("<li_snagAgeClass1>0</li_snagAgeClass1>");
      oOut.write("<li_snagAgeClass2>0</li_snagAgeClass2>");
      oOut.write("</GeneralLight>");
      oOut.write("<QuadratLight2>");
      oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
      oOut.write("<li_numAltGrids>12</li_numAltGrids>");
      oOut.write("<li_numAziGrids>18</li_numAziGrids>");
      oOut.write("<li_quadratLightHeight>0.675</li_quadratLightHeight>");
      oOut.write("</QuadratLight2>");
      oOut.write("<ConstRadialGrowth3>");
      oOut.write("<gr_adultConstRadialInc>");
      oOut.write("<gr_acriVal species=\"Trembling_Aspen\">1.14</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"Black_Cottonwood\">2.47</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"Paper_Birch\">0.72</gr_acriVal>");
      oOut.write("</gr_adultConstRadialInc>");
      oOut.write("</ConstRadialGrowth3>");
      oOut.write("<StochasticMortality4>");
      oOut.write("<mo_stochasticMortRate>");
      oOut.write("<mo_smrVal species=\"Western_Hemlock\">0.0050</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Western_Redcedar\">0.0050</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Amabilis_Fir\">0.0050</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Subalpine_Fir\">0.01</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Hybrid_Spruce\">0.01</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Lodgepole_Pine\">0.01</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Trembling_Aspen\">0.015</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Black_Cottonwood\">0.01</mo_smrVal>");
      oOut.write("<mo_smrVal species=\"Paper_Birch\">0.015</mo_smrVal>");
      oOut.write("</mo_stochasticMortRate>");
      oOut.write("</StochasticMortality4>");
      oOut.write("<Substrate5>");
      oOut.write("<su_scarSoilDecayAlpha>-5.1E-4</su_scarSoilDecayAlpha>");
      oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
      oOut.write("<su_tipupDecayAlpha>-7.0E-4</su_tipupDecayAlpha>");
      oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
      oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
      oOut.write("<su_freshLogDecayBeta>6.5</su_freshLogDecayBeta>");
      oOut.write("<su_decayedLogDecayAlpha>-0.79850775</su_decayedLogDecayAlpha>");
      oOut.write("<su_decayedLogDecayBeta>1.0</su_decayedLogDecayBeta>");
      oOut.write("<su_maxNumberDecayYears>50</su_maxNumberDecayYears>");
      oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
      oOut.write("<su_initialTipup>0.0</su_initialTipup>");
      oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
      oOut.write("<su_initialDecayedLog>0.11</su_initialDecayedLog>");
      oOut.write("<su_partialCutScarSoil>0.17</su_partialCutScarSoil>");
      oOut.write("<su_partialCutTipup>0.15</su_partialCutTipup>");
      oOut.write("<su_partialCutFreshLog>0.06</su_partialCutFreshLog>");
      oOut.write("<su_partialCutDecayedLog>0.04</su_partialCutDecayedLog>");
      oOut.write("<su_gapCutScarSoil>0.45</su_gapCutScarSoil>");
      oOut.write("<su_gapCutTipup>0.34</su_gapCutTipup>");
      oOut.write("<su_gapCutFreshLog>0.09</su_gapCutFreshLog>");
      oOut.write("<su_gapCutDecayedLog>0.0</su_gapCutDecayedLog>");
      oOut.write("<su_clearCutScarSoil>0.36</su_clearCutScarSoil>");
      oOut.write("<su_clearCutTipup>0.34</su_clearCutTipup>");
      oOut.write("<su_clearCutFreshLog>0.17</su_clearCutFreshLog>");
      oOut.write("<su_clearCutDecayedLog>0.01</su_clearCutDecayedLog>");
      oOut.write("<su_propOfDeadFall>");
      oOut.write("<su_podfVal species=\"Western_Hemlock\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Western_Redcedar\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Amabilis_Fir\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Subalpine_Fir\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Hybrid_Spruce\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Lodgepole_Pine\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Trembling_Aspen\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Black_Cottonwood\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Paper_Birch\">0.7</su_podfVal>");
      oOut.write("</su_propOfDeadFall>");
      oOut.write("<su_propOfFallUproot>");
      oOut.write("<su_pofuVal species=\"Western_Hemlock\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Western_Redcedar\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Amabilis_Fir\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Subalpine_Fir\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Hybrid_Spruce\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Lodgepole_Pine\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Trembling_Aspen\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Black_Cottonwood\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Paper_Birch\">0.5</su_pofuVal>");
      oOut.write("</su_propOfFallUproot>");
      oOut.write("<su_propOfSnagsUproot>");
      oOut.write("<su_posuVal species=\"Western_Hemlock\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Western_Redcedar\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Amabilis_Fir\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Subalpine_Fir\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Hybrid_Spruce\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Lodgepole_Pine\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Trembling_Aspen\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Black_Cottonwood\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Paper_Birch\">0.0</su_posuVal>");
      oOut.write("</su_propOfSnagsUproot>");
      oOut.write("<su_rootTipupFactor>3.1</su_rootTipupFactor>");
      oOut.write("<su_mossProportion>0.0</su_mossProportion>");
      oOut.write("<su_directionalTreeFall>0</su_directionalTreeFall>");
      oOut.write("</Substrate5>");
      oOut.write("<GapDisperse7>");
      oOut.write("<di_weibullCanopySTR>");
      oOut.write("<di_wcsVal species=\"Western_Hemlock\">3.8650002</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Western_Redcedar\">2.4514</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Amabilis_Fir\">1.3255999</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Subalpine_Fir\">0.48839998</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Hybrid_Spruce\">3.4411998</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Lodgepole_Pine\">0.6114</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Trembling_Aspen\">5.9544</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Black_Cottonwood\">0.3178</di_wcsVal>");
      oOut.write("<di_wcsVal species=\"Paper_Birch\">3.2012</di_wcsVal>");
      oOut.write("</di_weibullCanopySTR>");
      oOut.write("<di_weibullCanopyBeta>");
      oOut.write("<di_wcbVal species=\"Western_Hemlock\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Western_Redcedar\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Amabilis_Fir\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Subalpine_Fir\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Hybrid_Spruce\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Lodgepole_Pine\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Trembling_Aspen\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Black_Cottonwood\">2.0</di_wcbVal>");
      oOut.write("<di_wcbVal species=\"Paper_Birch\">2.0</di_wcbVal>");
      oOut.write("</di_weibullCanopyBeta>");
      oOut.write("<di_weibullCanopyDispersal>");
      oOut.write("<di_wcdVal species=\"Western_Hemlock\">1.76E-4</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Western_Redcedar\">1.82E-4</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Amabilis_Fir\">9.61E-5</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Subalpine_Fir\">1.32E-4</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Hybrid_Spruce\">3.0E-5</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Lodgepole_Pine\">2.8E-4</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Trembling_Aspen\">3.8E-5</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Black_Cottonwood\">1.52E-4</di_wcdVal>");
      oOut.write("<di_wcdVal species=\"Paper_Birch\">2.33E-4</di_wcdVal>");
      oOut.write("</di_weibullCanopyDispersal>");
      oOut.write("<di_weibullCanopyTheta>");
      oOut.write("<di_wctVal species=\"Western_Hemlock\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Western_Redcedar\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Amabilis_Fir\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Subalpine_Fir\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Hybrid_Spruce\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Lodgepole_Pine\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Trembling_Aspen\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Black_Cottonwood\">3.0</di_wctVal>");
      oOut.write("<di_wctVal species=\"Paper_Birch\">3.0</di_wctVal>");
      oOut.write("</di_weibullCanopyTheta>");
      oOut.write("<di_maxGapDensity>0</di_maxGapDensity>");
      oOut.write("<di_canopyFunction>");
      oOut.write("<di_cfVal species=\"Western_Hemlock\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Western_Redcedar\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Amabilis_Fir\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Subalpine_Fir\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Hybrid_Spruce\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Lodgepole_Pine\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Trembling_Aspen\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Black_Cottonwood\">0</di_cfVal>");
      oOut.write("<di_cfVal species=\"Paper_Birch\">0</di_cfVal>");
      oOut.write("</di_canopyFunction>");
      oOut.write("<di_minDbhForReproduction>");
      oOut.write("<di_mdfrVal species=\"Western_Hemlock\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Western_Redcedar\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Amabilis_Fir\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Subalpine_Fir\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Hybrid_Spruce\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Lodgepole_Pine\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Trembling_Aspen\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Black_Cottonwood\">15.0</di_mdfrVal>");
      oOut.write("<di_mdfrVal species=\"Paper_Birch\">15.0</di_mdfrVal>");
      oOut.write("</di_minDbhForReproduction>");
      oOut.write("</GapDisperse7>");
      oOut.write("<GeneralDisperse>");
      oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
      oOut.write("</GeneralDisperse>");
      oOut.write("<SubstrateDependentSeedSurvival8>");
      oOut.write("<es_scarifiedSoilCanopyFav>");
      oOut.write("<es_sscfVal species=\"Western_Hemlock\">0.129</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Western_Redcedar\">0.48</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Amabilis_Fir\">0.064</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Subalpine_Fir\">0.017</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Hybrid_Spruce\">0.391</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Lodgepole_Pine\">0.462</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Trembling_Aspen\">0.781</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Black_Cottonwood\">0.95</es_sscfVal>");
      oOut.write("<es_sscfVal species=\"Paper_Birch\">0.687</es_sscfVal>");
      oOut.write("</es_scarifiedSoilCanopyFav>");
      oOut.write("<es_tipUpCanopyFav>");
      oOut.write("<es_tucfVal species=\"Western_Hemlock\">0.983</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Western_Redcedar\">0.391</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Amabilis_Fir\">0.274</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Subalpine_Fir\">0.278</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Hybrid_Spruce\">0.778</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Lodgepole_Pine\">0.155</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Trembling_Aspen\">0.013</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Black_Cottonwood\">0.31</es_tucfVal>");
      oOut.write("<es_tucfVal species=\"Paper_Birch\">0.0010</es_tucfVal>");
      oOut.write("</es_tipUpCanopyFav>");
      oOut.write("<es_freshLogCanopyFav>");
      oOut.write("<es_flcfVal species=\"Western_Hemlock\">0.353</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Western_Redcedar\">0.424</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Amabilis_Fir\">0.0010</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Subalpine_Fir\">0.028</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Hybrid_Spruce\">0.07</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Lodgepole_Pine\">0.566</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Trembling_Aspen\">0.017</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Black_Cottonwood\">0.413</es_flcfVal>");
      oOut.write("<es_flcfVal species=\"Paper_Birch\">0.381</es_flcfVal>");
      oOut.write("</es_freshLogCanopyFav>");
      oOut.write("<es_decayedLogCanopyFav>");
      oOut.write("<es_dlcfVal species=\"Western_Hemlock\">0.058</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Western_Redcedar\">0.024</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Amabilis_Fir\">0.305</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Subalpine_Fir\">0.55</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Hybrid_Spruce\">0.054</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Lodgepole_Pine\">0.0080</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Trembling_Aspen\">0.18</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Black_Cottonwood\">0.771</es_dlcfVal>");
      oOut.write("<es_dlcfVal species=\"Paper_Birch\">0.8</es_dlcfVal>");
      oOut.write("</es_decayedLogCanopyFav>");
      oOut.write("<es_forestFloorLitterCanopyFav>");
      oOut.write("<es_fflcfVal species=\"Western_Hemlock\">0.0090</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Western_Redcedar\">0.01</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Amabilis_Fir\">0.815</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Subalpine_Fir\">0.019</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Hybrid_Spruce\">0.0090</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Lodgepole_Pine\">0.0040</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Trembling_Aspen\">0.01</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Black_Cottonwood\">0.315</es_fflcfVal>");
      oOut.write("<es_fflcfVal species=\"Paper_Birch\">0.12</es_fflcfVal>");
      oOut.write("</es_forestFloorLitterCanopyFav>");
      oOut.write("<es_forestFloorMossCanopyFav>");
      oOut.write("<es_ffmcfVal species=\"Western_Hemlock\">0.0090</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Western_Redcedar\">0.01</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Amabilis_Fir\">0.815</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Subalpine_Fir\">0.019</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Hybrid_Spruce\">0.0090</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Lodgepole_Pine\">0.0040</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Trembling_Aspen\">0.01</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Black_Cottonwood\">0.315</es_ffmcfVal>");
      oOut.write("<es_ffmcfVal species=\"Paper_Birch\">0.12</es_ffmcfVal>");
      oOut.write("</es_forestFloorMossCanopyFav>");
      oOut.write("</SubstrateDependentSeedSurvival8>");
      oOut.write("<Output>");
      oOut.write("<ou_filename>zxc.gz.tar</ou_filename>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>2</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>3</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>4</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>5</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>6</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>7</ou_saveFreq>");
      oOut.write("<ou_float>Y</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>8</ou_saveFreq>");
      oOut.write("<ou_float>Diam10</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>9</ou_saveFreq>");
      oOut.write("<ou_float>DBH</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>10</ou_saveFreq>");
      oOut.write("<ou_float>Height</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>11</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("<ou_float>Light</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>12</ou_saveFreq>");
      oOut.write("<ou_float>Growth</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>13</ou_saveFreq>");
      oOut.write("<ou_bool>dead</ou_bool>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>14</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>15</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Trembling_Aspen\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>16</ou_saveFreq>");
      oOut.write("<ou_float>Diam10</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Black_Cottonwood\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>17</ou_saveFreq>");
      oOut.write("<ou_float>Light</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Black_Cottonwood\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>18</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Paper_Birch\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>19</ou_saveFreq>");
      oOut.write("<ou_float>Y</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Paper_Birch\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>20</ou_saveFreq>");
      oOut.write("<ou_bool>dead</ou_bool>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_gridInfo gridName=\"Windstorm Results\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_packageFloat>ba_0</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_1</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_2</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_3</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_4</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_5</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_6</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_7</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_8</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_0</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_1</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_2</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_3</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_4</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_5</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_6</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_7</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_8</ou_packageFloat>");
      oOut.write("<ou_packageFloat>severity</ou_packageFloat>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Quadrat GLI\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>GLI</ou_float>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Substrate\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>scarsoil</ou_float>");
      oOut.write("<ou_float>tipup</ou_float>");
      oOut.write("<ou_float>freshlog</ou_float>");
      oOut.write("<ou_float>declog</ou_float>");
      oOut.write("<ou_float>ffmoss</ou_float>");
      oOut.write("<ou_float>fflitter</ou_float>");
      oOut.write("<ou_packageInt>age</ou_packageInt>");
      oOut.write("<ou_packageFloat>scarsoil</ou_packageFloat>");
      oOut.write("<ou_packageFloat>tipup</ou_packageFloat>");
      oOut.write("<ou_packageFloat>freshlog</ou_packageFloat>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Dispersed Seeds\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_int>count</ou_int>");
      oOut.write("<ou_float>seeds_0</ou_float>");
      oOut.write("<ou_float>seeds_1</ou_float>");
      oOut.write("<ou_float>seeds_2</ou_float>");
      oOut.write("<ou_float>seeds_3</ou_float>");
      oOut.write("<ou_float>seeds_4</ou_float>");
      oOut.write("<ou_float>seeds_5</ou_float>");
      oOut.write("<ou_float>seeds_6</ou_float>");
      oOut.write("<ou_float>seeds_7</ou_float>");
      oOut.write("<ou_float>seeds_8</ou_float>");
      oOut.write("<ou_bool>Is Gap</ou_bool>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Substrate Favorability\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>Favorability Index0</ou_float>");
      oOut.write("<ou_float>Favorability Index1</ou_float>");
      oOut.write("<ou_float>Favorability Index2</ou_float>");
      oOut.write("<ou_float>Favorability Index3</ou_float>");
      oOut.write("<ou_float>Favorability Index4</ou_float>");
      oOut.write("<ou_float>Favorability Index5</ou_float>");
      oOut.write("<ou_float>Favorability Index6</ou_float>");
      oOut.write("<ou_float>Favorability Index7</ou_float>");
      oOut.write("<ou_float>Favorability Index8</ou_float>");
      oOut.write("</ou_gridInfo>");
      oOut.write("</Output>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a real-type file, so we can work with actual grids.
   * @throws ModelException if there is a problem writing the file.
   * @return String Filename just written.
   */
  private String writeXMLFile3() throws ModelException {
    try {
      String sFileName = "\\testfile1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>100</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Hybrid_Spruce\"/>");
      oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
      oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
      oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
      oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"Western_Hemlock\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Western_Redcedar\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Amabilis_Fir\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Subalpine_Fir\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Hybrid_Spruce\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Lodgepole_Pine\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Trembling_Aspen\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Black_Cottonwood\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Paper_Birch\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Amabilis_Fir\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Subalpine_Fir\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Hybrid_Spruce\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Lodgepole_Pine\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Black_Cottonwood\">1.35</tr_mshVal>");
      oOut.write("<tr_mshVal species=\"Paper_Birch\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Windstorm</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>StochasticMortality</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>4</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Substrate</behaviorName>");
      oOut.write("<version>2.1</version>");
      oOut.write("<listPosition>5</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>6</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>GapDisperse</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>7</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>GapSubstrateSeedSurvival</behaviorName>");
      oOut.write("<version>1.1</version>");
      oOut.write("<listPosition>8</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seed\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Establishment</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>9</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Seed\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Seed\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Output</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>10</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Substrate5>");
      oOut.write("<su_scarSoilDecayAlpha>-5.1E-4</su_scarSoilDecayAlpha>");
      oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
      oOut.write("<su_tipupDecayAlpha>-7.0E-4</su_tipupDecayAlpha>");
      oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
      oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
      oOut.write("<su_freshLogDecayBeta>6.5</su_freshLogDecayBeta>");
      oOut.write("<su_decayedLogDecayAlpha>-0.79850775</su_decayedLogDecayAlpha>");
      oOut.write("<su_decayedLogDecayBeta>1.0</su_decayedLogDecayBeta>");
      oOut.write("<su_maxNumberDecayYears>50</su_maxNumberDecayYears>");
      oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
      oOut.write("<su_initialTipup>0.0</su_initialTipup>");
      oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
      oOut.write("<su_initialDecayedLog>0.11</su_initialDecayedLog>");
      oOut.write("<su_partialCutScarSoil>0.17</su_partialCutScarSoil>");
      oOut.write("<su_partialCutTipup>0.15</su_partialCutTipup>");
      oOut.write("<su_partialCutFreshLog>0.06</su_partialCutFreshLog>");
      oOut.write("<su_partialCutDecayedLog>0.04</su_partialCutDecayedLog>");
      oOut.write("<su_gapCutScarSoil>0.45</su_gapCutScarSoil>");
      oOut.write("<su_gapCutTipup>0.34</su_gapCutTipup>");
      oOut.write("<su_gapCutFreshLog>0.09</su_gapCutFreshLog>");
      oOut.write("<su_gapCutDecayedLog>0.0</su_gapCutDecayedLog>");
      oOut.write("<su_clearCutScarSoil>0.36</su_clearCutScarSoil>");
      oOut.write("<su_clearCutTipup>0.34</su_clearCutTipup>");
      oOut.write("<su_clearCutFreshLog>0.17</su_clearCutFreshLog>");
      oOut.write("<su_clearCutDecayedLog>0.01</su_clearCutDecayedLog>");
      oOut.write("<su_propOfDeadFall>");
      oOut.write("<su_podfVal species=\"Western_Hemlock\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Western_Redcedar\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Amabilis_Fir\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Subalpine_Fir\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Hybrid_Spruce\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Lodgepole_Pine\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Trembling_Aspen\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Black_Cottonwood\">0.7</su_podfVal>");
      oOut.write("<su_podfVal species=\"Paper_Birch\">0.7</su_podfVal>");
      oOut.write("</su_propOfDeadFall>");
      oOut.write("<su_propOfFallUproot>");
      oOut.write("<su_pofuVal species=\"Western_Hemlock\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Western_Redcedar\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Amabilis_Fir\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Subalpine_Fir\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Hybrid_Spruce\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Lodgepole_Pine\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Trembling_Aspen\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Black_Cottonwood\">0.5</su_pofuVal>");
      oOut.write("<su_pofuVal species=\"Paper_Birch\">0.5</su_pofuVal>");
      oOut.write("</su_propOfFallUproot>");
      oOut.write("<su_propOfSnagsUproot>");
      oOut.write("<su_posuVal species=\"Western_Hemlock\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Western_Redcedar\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Amabilis_Fir\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Subalpine_Fir\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Hybrid_Spruce\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Lodgepole_Pine\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Trembling_Aspen\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Black_Cottonwood\">0.0</su_posuVal>");
      oOut.write("<su_posuVal species=\"Paper_Birch\">0.0</su_posuVal>");
      oOut.write("</su_propOfSnagsUproot>");
      oOut.write("<su_rootTipupFactor>3.1</su_rootTipupFactor>");
      oOut.write("<su_mossProportion>0.0</su_mossProportion>");
      oOut.write("<su_directionalTreeFall>0</su_directionalTreeFall>");
      oOut.write("</Substrate5>");
      oOut.write("<Output>");
      oOut.write("<ou_filename>zxc.gz.tar</ou_filename>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>2</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>3</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>4</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>5</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Western_Redcedar\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>6</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>7</ou_saveFreq>");
      oOut.write("<ou_float>Y</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>8</ou_saveFreq>");
      oOut.write("<ou_float>Diam10</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Subalpine_Fir\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>9</ou_saveFreq>");
      oOut.write("<ou_float>DBH</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>10</ou_saveFreq>");
      oOut.write("<ou_float>Height</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>11</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("<ou_float>Light</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Hybrid_Spruce\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>12</ou_saveFreq>");
      oOut.write("<ou_float>Growth</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>13</ou_saveFreq>");
      oOut.write("<ou_bool>dead</ou_bool>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>14</ou_saveFreq>");
      oOut.write("<ou_int>yls</ou_int>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Lodgepole_Pine\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>15</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Trembling_Aspen\" type=\"Sapling\">");
      oOut.write("<ou_saveFreq>16</ou_saveFreq>");
      oOut.write("<ou_float>Diam10</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Black_Cottonwood\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>17</ou_saveFreq>");
      oOut.write("<ou_float>Light</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Black_Cottonwood\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>18</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Paper_Birch\" type=\"Seedling\">");
      oOut.write("<ou_saveFreq>19</ou_saveFreq>");
      oOut.write("<ou_float>Y</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_treeInfo species=\"Paper_Birch\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>20</ou_saveFreq>");
      oOut.write("<ou_bool>dead</ou_bool>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_gridInfo gridName=\"Windstorm Results\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_packageFloat>ba_0</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_1</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_2</ou_packageFloat>");
      oOut.write("<ou_packageFloat>ba_3</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_3</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_4</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_5</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_6</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_7</ou_packageFloat>");
      oOut.write("<ou_packageFloat>density_8</ou_packageFloat>");
      oOut.write("<ou_packageFloat>severity</ou_packageFloat>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Quadrat GLI\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>GLI</ou_float>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Substrate\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>scarsoil</ou_float>");
      oOut.write("<ou_float>tipup</ou_float>");
      oOut.write("<ou_float>freshlog</ou_float>");
      oOut.write("<ou_float>declog</ou_float>");
      oOut.write("<ou_float>ffmoss</ou_float>");
      oOut.write("<ou_float>fflitter</ou_float>");
      oOut.write("<ou_packageInt>age</ou_packageInt>");
      oOut.write("<ou_packageFloat>scarsoil</ou_packageFloat>");
      oOut.write("<ou_packageFloat>tipup</ou_packageFloat>");
      oOut.write("<ou_packageFloat>freshlog</ou_packageFloat>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Dispersed Seeds\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_int>count</ou_int>");
      oOut.write("<ou_float>seeds_0</ou_float>");
      oOut.write("<ou_float>seeds_2</ou_float>");
      oOut.write("<ou_float>seeds_4</ou_float>");
      oOut.write("<ou_float>seeds_5</ou_float>");
      oOut.write("<ou_float>seeds_6</ou_float>");
      oOut.write("<ou_float>seeds_8</ou_float>");
      oOut.write("<ou_bool>Is Gap</ou_bool>");
      oOut.write("</ou_gridInfo>");
      oOut.write("<ou_gridInfo gridName=\"Substrate Favorability\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>Favorability Index2</ou_float>");
      oOut.write("</ou_gridInfo>");
      oOut.write("</Output>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
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
    oOut.write("<paramFile fileCode=\"06080101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>400</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>500.0</plot_lenX>");
    oOut.write("<plot_lenY>500.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
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
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"ACRU\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">25.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"ACSA\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">25.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"BEAL\">");
    oOut.write("<tr_initialDensity sizeClass=\"s1.0\">25.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
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
    oOut.write("<behaviorName>quadratlight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>substrate</behaviorName>");
    oOut.write("<version>2.1</version>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>output</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Tree Age Calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
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
    oOut.write("<tr_chVal species=\"ACRU\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"ACSA\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"BEAL\">30.0</tr_chVal>");
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
    oOut.write("<gliLight>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"ACRU\">0.399</li_lecVal>");
    oOut.write("<li_lecVal species=\"ACSA\">0.399</li_lecVal>");
    oOut.write("<li_lecVal species=\"BEAL\">0.399</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_snagAgeClass1>0</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>0</li_snagAgeClass2>");
    oOut.write("</gliLight>");
    oOut.write("<quadratGliLight>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_quadratLightHeight>0.675</li_quadratLightHeight>");
    oOut.write("</quadratGliLight>");
    oOut.write("<substrate>");
    oOut.write("<su_scarSoilDecayAlpha>-5.1E-4</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-7.0E-4</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
    oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
    oOut.write("<su_freshLogDecayBeta>4.4</su_freshLogDecayBeta>");
    oOut.write("<su_decayedLogDecayAlpha>-0.7985</su_decayedLogDecayAlpha>");
    oOut.write("<su_decayedLogDecayBeta>1.1</su_decayedLogDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>10</su_maxNumberDecayYears>");
    oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.2</su_initialTipup>");
    oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
    oOut.write("<su_initialDecayedLog>0.01</su_initialDecayedLog>");
    oOut.write("<su_partialCutScarSoil>0.17</su_partialCutScarSoil>");
    oOut.write("<su_partialCutTipup>0.15</su_partialCutTipup>");
    oOut.write("<su_partialCutFreshLog>0.06</su_partialCutFreshLog>");
    oOut.write("<su_partialCutDecayedLog>0.04</su_partialCutDecayedLog>");
    oOut.write("<su_gapCutScarSoil>0.45</su_gapCutScarSoil>");
    oOut.write("<su_gapCutTipup>0.34</su_gapCutTipup>");
    oOut.write("<su_gapCutFreshLog>0.09</su_gapCutFreshLog>");
    oOut.write("<su_gapCutDecayedLog>0.0</su_gapCutDecayedLog>");
    oOut.write("<su_clearCutScarSoil>0.36</su_clearCutScarSoil>");
    oOut.write("<su_clearCutTipup>0.34</su_clearCutTipup>");
    oOut.write("<su_clearCutFreshLog>0.17</su_clearCutFreshLog>");
    oOut.write("<su_clearCutDecayedLog>0.01</su_clearCutDecayedLog>");
    oOut.write("<su_propOfDeadFall>");
    oOut.write("<su_podfVal species=\"ACSA\">0.2</su_podfVal>");
    oOut.write("<su_podfVal species=\"ACRU\">0.5</su_podfVal>");
    oOut.write("<su_podfVal species=\"BEAL\">0.0</su_podfVal>");
    oOut.write("</su_propOfDeadFall>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"ACSA\">1</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"ACRU\">1</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"BEAL\">1</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"ACSA\">1</su_posuVal>");
    oOut.write("<su_posuVal species=\"ACRU\">0.7</su_posuVal>");
    oOut.write("<su_posuVal species=\"BEAL\">0.15</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3.1</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.3</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>1</su_directionalTreeFall>");
    oOut.write("</substrate>");
    
    oOut.write("<output>");
    oOut.write("<ou_filename>testoutput.gz.tar</ou_filename>");
    oOut.write("<ou_treeInfo species=\"ACRU\" type=\"Seedling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"ACRU\" type=\"Sapling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"ACRU\" type=\"Adult\">");
    oOut.write("<ou_saveFreq>2</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"ACSA\" type=\"Seedling\">");
    oOut.write("<ou_saveFreq>3</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"ACSA\" type=\"Sapling\">");
    oOut.write("<ou_saveFreq>4</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"ACSA\" type=\"Adult\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"BEAL\" type=\"Seedling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"BEAL\" type=\"Sapling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_treeInfo species=\"BEAL\" type=\"Adult\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_int>Tree Age</ou_int>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_deadTreeInfo species=\"BEAL\" type=\"Adult\" code=\"natural\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_int>Tree Age</ou_int>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>DBH</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("</ou_deadTreeInfo>");
    oOut.write("<ou_gridInfo gridName=\"Quadrat GLI\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_float>GLI</ou_float>");
    oOut.write("</ou_gridInfo>");
    oOut.write("<ou_gridInfo gridName=\"Substrate\">");
    oOut.write("<ou_saveFreq>2</ou_saveFreq>");
    oOut.write("<ou_float>scarsoil</ou_float>");
    oOut.write("<ou_float>tipup</ou_float>");
    oOut.write("<ou_float>freshlog</ou_float>");
    oOut.write("<ou_float>declog</ou_float>");
    oOut.write("<ou_float>ffmoss</ou_float>");
    oOut.write("<ou_float>fflitter</ou_float>");
    oOut.write("<ou_packageInt>age</ou_packageInt>");
    oOut.write("<ou_packageFloat>scarsoil</ou_packageFloat>");
    oOut.write("<ou_packageFloat>tipup</ou_packageFloat>");
    oOut.write("<ou_packageFloat>freshlog</ou_packageFloat>");
    oOut.write("</ou_gridInfo>");
    oOut.write("<ou_subplot>");
    oOut.write("<ou_subplotName>Sub1</ou_subplotName>");
    oOut.write("<pointSet>");
    oOut.write("<po_point x=\"1\" y=\"2\"/>");
    oOut.write("<po_point x=\"2\" y=\"3\"/>");
    oOut.write("<po_point x=\"3\" y=\"4\"/>");
    oOut.write("<po_point x=\"4\" y=\"5\"/>");
    oOut.write("<po_point x=\"5\" y=\"6\"/>");
    oOut.write("</pointSet>");
    oOut.write("</ou_subplot>");
    oOut.write("</output>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
