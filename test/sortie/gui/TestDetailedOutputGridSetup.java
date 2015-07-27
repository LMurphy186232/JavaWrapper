package sortie.gui;


import javax.swing.JDialog;

import sortie.ModelTestCase;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.ModelException;
import sortie.gui.DetailedOutputGridSetup;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;

import java.io.FileWriter;
import java.io.File;

/**
 * Tests the RundataGridSetup class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class TestDetailedOutputGridSetup
    extends ModelTestCase {

  /**
   * Tests the case where there are no settings in the OutputBehaviors object
   * to load when the window is created
   */
  public void testLoadNoSettings() {
    MainWindow oWindow = new MainWindow();
    oWindow.setVisible(false);
    GUIManager oManager = oWindow.m_oDataManager;
    String sFileName = null;
    try {
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oParent = oManager.getOutputBehaviors();
      DetailedOutput oDetailed = (DetailedOutput) oParent.getDetailedOutput();
      JDialog jFake = new JDialog();
      DetailedOutputGridSetup oTester = new DetailedOutputGridSetup(jFake, oDetailed);

      //Verify that there are no choices in the grid
      assertEquals(1, oTester.m_jGridListCombo.getItemCount());
      String sChoice = (String) oTester.m_jGridListCombo.getItemAt(0);
      assertTrue(sChoice.startsWith("---"));

      //Verify that there is nothing in data members
      assertEquals(0, oTester.m_jDataMemberListModel.getSize());

      //Verify that there is nothing in the "what saved" box
      assertEquals(0, oTester.m_jSaveListModel.getSize());
    }
    catch (ModelException oErr) {
      fail("TestLoadNoSettings failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests the case where there are settings in the OutputBehaviors object
   * to load when the window is created
   */
  public void testLoadSettings() {
    MainWindow oWindow = new MainWindow();
    oWindow.setVisible(false);
    GUIManager oManager = oWindow.m_oDataManager;
    String sFileName = null;
    try {
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oParent = oManager.getOutputBehaviors();
      DetailedOutput oOutput = oParent.getDetailedOutput();
      JDialog jFake = new JDialog();

      //Create some settings to load
      DetailedGridSettings oSettings = new DetailedGridSettings("Test Grid 1");
      oSettings.addChar("Char1", "Test Char 1");
      oSettings.addInt("Integer 1", "Test Integer 1");
      oSettings.addInt("Integer 2", "Test Integer 2");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oSettings);

      oSettings = null;
      oSettings = new DetailedGridSettings("Test Grid 2");
      oSettings.addBool("Bool1", "Test Bool 1");
      oSettings.addChar("Char 1", "Test Char 1");
      oSettings.setSaveFrequency(2);
      oOutput.addDetailedGridSettings(oSettings);

      DetailedOutputGridSetup oTester = new DetailedOutputGridSetup(jFake, oOutput);

      //Verify that there are 2 choices in the grid
      /*    assertEquals(3, oTester.jGridListCombo.getItemCount());
          String sChoice = (String) oTester.jGridListCombo.getItemAt(0);
          assertTrue(sChoice.startsWith("---"));
          sChoice = (String) oTester.jGridListCombo.getItemAt(1);
          assertTrue(sChoice.equals("Test Grid 1"));
          sChoice = (String) oTester.jGridListCombo.getItemAt(2);
          assertTrue(sChoice.equals("Test Grid 2"));
          //Verify that there is nothing in data members
          assertEquals(0, oTester.jDataMemberListModel.getSize());
          //Choose the no-grid line and verify that there's nothing in data members
          oTester.jGridListCombo.setSelectedIndex(0);
          assertEquals(0, oTester.jDataMemberListModel.getSize());
          //Choose the first grid and verify the number of data members
          oTester.jGridListCombo.setSelectedIndex(1);
          assertEquals(3, oTester.jDataMemberListModel.getSize());
          //Choose the second grid and verify the number of data members
          oTester.jGridListCombo.setSelectedIndex(2);
          assertEquals(2, oTester.jDataMemberListModel.getSize()); */

      //Verify that there are two lines in the "what saved" box
      assertEquals(2, oTester.m_jSaveListModel.getSize());
    }
    catch (ModelException oErr) {
      fail("TestLoadSettings failed with message " + oErr.getMessage());
    }
  }

  /**
   * This writes an XML file to test value setting.  This file contains short
   * output settings only, for all (normal) types with a mix of true, false,
   * and absent.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
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
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
