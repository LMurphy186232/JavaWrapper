package sortie.gui.behaviorsetup;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.StateChangeBehaviors;
import sortie.data.funcgroups.statechange.ClimateImporter;
import sortie.data.funcgroups.statechange.ClimateImporterTest;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;

/**
 * Tests the ClimateImportEditor interface.
 * <p>Copyright: Copyright (c) Charles D. Canham 20017</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestClimateImporterEditor extends ModelTestCase {
  
  /**
   * Tests that values changed in the window are correctly passed back to the behavior.
   * This is in response to a bug report.
   */
  public void testValueChange() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = ClimateImporterTest.writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Call the interface
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      ClimateImporter oClim = (ClimateImporter) p_oBehs.get(0);
      ClimateImporterEditor oWindow = new ClimateImporterEditor(null, oManager, oMainWindow, oClim);
      
      oWindow.readDataFile(new File(writePrecipFile1()), "ReadPptData");
      oWindow.readDataFile(new File(writeTempFile1()), "ReadTempData");
      
      // Change values in the radiation and water storage 
      assertEquals(1, oWindow.mp_oAllTables.size());
      assertEquals(1, oWindow.mp_oAllTables.get(0).size());
      Object[][] oTable = oWindow.mp_oAllTables.get(0).get(0).getData().mp_oTableData;
      assertEquals(16, oTable.length);
      assertEquals(2, oTable[0].length);
      
      //Set February solar
      oWindow.mp_oAllTables.get(0).get(0).setValueAt(1001.1, 2, 1);
      
      //Set AWS
      oWindow.mp_oAllTables.get(0).get(0).setValueAt(70.1, 13, 1);
      boolean bIsPrecip = true, bIsCalendarYear = true;
      int iNumTimesteps = 6, iPreRunTimesteps = 0;
      
      //Set timestep 2 precip - remember indexes have to account for row and 
      //column names at the zero index
      ClimateImporterValuesEditor oChild = new ClimateImporterValuesEditor(oWindow, bIsPrecip, 
          bIsCalendarYear, iNumTimesteps, iPreRunTimesteps);
      oChild.m_oDataTable.setValueAt(100.0, 2, 1);
      oChild.m_oDataTable.setValueAt(200.0, 2, 2);
      oChild.m_oDataTable.setValueAt(300.0, 2, 3);
      oChild.m_oDataTable.setValueAt(400.0, 2, 4);
      oChild.m_oDataTable.setValueAt(500.0, 2, 5);
      oChild.m_oDataTable.setValueAt(600.0, 2, 6);
      oChild.m_oDataTable.setValueAt(700.0, 2, 7);
      oChild.m_oDataTable.setValueAt(800.0, 2, 8);
      oChild.m_oDataTable.setValueAt(900.0, 2, 9);
      oChild.m_oDataTable.setValueAt(1000.0, 2, 10);
      oChild.m_oDataTable.setValueAt(1100.0, 2, 11);
      oChild.m_oDataTable.setValueAt(1200.0, 2, 12);
      oChild.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Set timestep 3 temp
      bIsPrecip = false;
      oChild = new ClimateImporterValuesEditor(oWindow, bIsPrecip, 
          bIsCalendarYear, iNumTimesteps, iPreRunTimesteps);
      oChild.m_oDataTable.setValueAt(10, 3, 1);
      oChild.m_oDataTable.setValueAt(11, 3, 2);
      oChild.m_oDataTable.setValueAt(12, 3, 3);
      oChild.m_oDataTable.setValueAt(13, 3, 4);
      oChild.m_oDataTable.setValueAt(14, 3, 5);
      oChild.m_oDataTable.setValueAt(15, 3, 6);
      oChild.m_oDataTable.setValueAt(16, 3, 7);
      oChild.m_oDataTable.setValueAt(17, 3, 8);
      oChild.m_oDataTable.setValueAt(18, 3, 9);
      oChild.m_oDataTable.setValueAt(19, 3, 10);
      oChild.m_oDataTable.setValueAt(20, 3, 11);
      oChild.m_oDataTable.setValueAt(21, 3, 12);
      oChild.actionPerformed(new ActionEvent(this, 0, "OK"));
      oWindow.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      // Test read February solar and AWS
      assertEquals(1001.1, oClim.getFebruarySolar(), 0.001);
      assertEquals(70.1, oClim.getAWS(), 0.001);
      
      // Test that timestep 2 precip got set
      assertEquals(100.0, oClim.getPptData(2, 1), 0.001);
      assertEquals(200.0, oClim.getPptData(2, 2), 0.001);
      assertEquals(300.0, oClim.getPptData(2, 3), 0.001);
      assertEquals(400.0, oClim.getPptData(2, 4), 0.001);
      assertEquals(500.0, oClim.getPptData(2, 5), 0.001);
      assertEquals(600.0, oClim.getPptData(2, 6), 0.001);
      assertEquals(700.0, oClim.getPptData(2, 7), 0.001);
      assertEquals(800.0, oClim.getPptData(2, 8), 0.001);
      assertEquals(900.0, oClim.getPptData(2, 9), 0.001);
      assertEquals(1000.0, oClim.getPptData(2, 10), 0.001);
      assertEquals(1100.0, oClim.getPptData(2, 11), 0.001);
      assertEquals(1200.0, oClim.getPptData(2, 12), 0.001);
      
      // Test that timestep 3 temp got set
      assertEquals(10.0, oClim.getTempData(3, 1), 0.001);
      assertEquals(11.0, oClim.getTempData(3, 2), 0.001);
      assertEquals(12.0, oClim.getTempData(3, 3), 0.001);
      assertEquals(13.0, oClim.getTempData(3, 4), 0.001);
      assertEquals(14.0, oClim.getTempData(3, 5), 0.001);
      assertEquals(15.0, oClim.getTempData(3, 6), 0.001);
      assertEquals(16.0, oClim.getTempData(3, 7), 0.001);
      assertEquals(17.0, oClim.getTempData(3, 8), 0.001);
      assertEquals(18.0, oClim.getTempData(3, 9), 0.001);
      assertEquals(19.0, oClim.getTempData(3, 10), 0.001);
      assertEquals(20.0, oClim.getTempData(3, 11), 0.001);
      assertEquals(21.0, oClim.getTempData(3, 12), 0.001);
      
    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  

  /**
   * Tests that the window correctly loads on setup
   */
  public void testFileSetup() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = ClimateImporterTest.writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Call the interface
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      ClimateImporter oClim = (ClimateImporter) p_oBehs.get(0);
      ClimateImporterEditor oWindow = new ClimateImporterEditor(null, oManager, oMainWindow, oClim);
      
      // Check the data that can be treated normally - water storage and radiation 
      assertEquals(1, oWindow.mp_oAllTables.size());
      assertEquals(1, oWindow.mp_oAllTables.get(0).size());
      Object[][] oTable = oWindow.mp_oAllTables.get(0).get(0).getData().mp_oTableData;
      assertEquals(16, oTable.length);
      assertEquals(2, oTable[0].length);
      assertEquals(7468.475, Double.parseDouble(oTable[1][1].toString()), 0.001);
      assertEquals(10353.32, Double.parseDouble(oTable[2][1].toString()), 0.001);
      assertEquals(17453.07, Double.parseDouble(oTable[3][1].toString()), 0.001);
      assertEquals(22721.85, Double.parseDouble(oTable[4][1].toString()), 0.001);
      assertEquals(27901.37, Double.parseDouble(oTable[5][1].toString()), 0.001);
      assertEquals(28677.54, Double.parseDouble(oTable[6][1].toString()), 0.001);
      assertEquals(28764.65, Double.parseDouble(oTable[7][1].toString()), 0.001);
      assertEquals(25075.4, Double.parseDouble(oTable[8][1].toString()), 0.001);
      assertEquals(19259.27, Double.parseDouble(oTable[9][1].toString()), 0.001);
      assertEquals(12609.6, Double.parseDouble(oTable[10][1].toString()), 0.001);
      assertEquals(7988.013, Double.parseDouble(oTable[11][1].toString()), 0.001);
      assertEquals(6307.151, Double.parseDouble(oTable[12][1].toString()), 0.001);
      assertEquals(60.9, Double.parseDouble(oTable[13][1].toString()), 0.00001);
      String sTemp = oTable[14][1].toString();
      assertEquals(sTemp.compareTo("&&Calendar year (Jan-Dec)|Calendar year (Jan-Dec),Growing season (Oct-Sep)"), 0);
      assertEquals(0, Double.parseDouble(oTable[15][1].toString()), 0.001);

      
      
      // Check the temperature and precipitation data
      boolean bIsPrecip = false, bIsCalendarYear = true;
      int iNumTimesteps = 6, iPreRunTimesteps = 0;
      ClimateImporterValuesEditor oChild = new ClimateImporterValuesEditor(oWindow, bIsPrecip, 
          bIsCalendarYear, iNumTimesteps, iPreRunTimesteps);
      oTable = oChild.m_oDataTable.getData().mp_oTableData;
      assertEquals(-2.57, Double.parseDouble(oTable[1][1].toString()), 0.001);
      assertEquals(-3.46, Double.parseDouble(oTable[2][1].toString()), 0.001);
      assertEquals(-6.93, Double.parseDouble(oTable[3][1].toString()), 0.001);
      assertEquals(-7.76, Double.parseDouble(oTable[4][1].toString()), 0.001);
      assertEquals(-0.72, Double.parseDouble(oTable[5][1].toString()), 0.001);
      assertEquals(1.3, Double.parseDouble(oTable[6][1].toString()), 0.001);
      
      assertEquals(2.75, Double.parseDouble(oTable[1][2].toString()),0.001);
      assertEquals(-8.57, Double.parseDouble(oTable[2][2].toString()),0.001);
      assertEquals(1.98, Double.parseDouble(oTable[3][2].toString()),0.001);
      assertEquals(-7.5, Double.parseDouble(oTable[4][2].toString()),0.001);
      assertEquals(-2.44, Double.parseDouble(oTable[5][2].toString()),0.001);
      assertEquals(-9.36, Double.parseDouble(oTable[6][2].toString()),0.001);
      
      assertEquals(2.94, Double.parseDouble(oTable[1][3].toString()),0.001);
      assertEquals(0.28, Double.parseDouble(oTable[2][3].toString()),0.001);
      assertEquals(0.3, Double.parseDouble(oTable[3][3].toString()),0.001);
      assertEquals(-0.78, Double.parseDouble(oTable[4][3].toString()),0.001);
      assertEquals(4.85, Double.parseDouble(oTable[5][3].toString()),0.001);
      assertEquals(3.78, Double.parseDouble(oTable[6][3].toString()),0.001);
      
      assertEquals(2.17, Double.parseDouble(oTable[1][4].toString()),0.001);
      assertEquals(5.9, Double.parseDouble(oTable[2][4].toString()),0.001);
      assertEquals(7.17, Double.parseDouble(oTable[3][4].toString()),0.001);
      assertEquals(8.67, Double.parseDouble(oTable[4][4].toString()),0.001);
      assertEquals(5.61, Double.parseDouble(oTable[5][4].toString()),0.001);
      assertEquals(4.56, Double.parseDouble(oTable[6][4].toString()),0.001);
      
      assertEquals(7.25, Double.parseDouble(oTable[1][5].toString()),0.001);
      assertEquals(7.6, Double.parseDouble(oTable[2][5].toString()),0.001);
      assertEquals(9.46, Double.parseDouble(oTable[3][5].toString()),0.001);
      assertEquals(11.26, Double.parseDouble(oTable[4][5].toString()),0.001);
      assertEquals(7.6, Double.parseDouble(oTable[5][5].toString()),0.001);
      assertEquals(6.9, Double.parseDouble(oTable[6][5].toString()),0.001);
      
      assertEquals(11.05, Double.parseDouble(oTable[1][6].toString()),0.001);
      assertEquals(17.16, Double.parseDouble(oTable[2][6].toString()),0.001);
      assertEquals(11.06, Double.parseDouble(oTable[3][6].toString()),0.001);
      assertEquals(17.72, Double.parseDouble(oTable[4][6].toString()),0.001);
      assertEquals(14.05, Double.parseDouble(oTable[5][6].toString()),0.001);
      assertEquals(13.52, Double.parseDouble(oTable[6][6].toString()),0.001);
      
      assertEquals(16.82, Double.parseDouble(oTable[1][7].toString()),0.001);
      assertEquals(22, Double.parseDouble(oTable[2][7].toString()),0.001);
      assertEquals(18.34, Double.parseDouble(oTable[3][7].toString()),0.001);
      assertEquals(20.99, Double.parseDouble(oTable[4][7].toString()),0.001);
      assertEquals(15.46, Double.parseDouble(oTable[5][7].toString()),0.001);
      assertEquals(15.74, Double.parseDouble(oTable[6][7].toString()),0.001);
      
      assertEquals(18.32, Double.parseDouble(oTable[1][8].toString()),0.001);
      assertEquals(15.97, Double.parseDouble(oTable[2][8].toString()),0.001);
      assertEquals(17.54, Double.parseDouble(oTable[3][8].toString()),0.001);
      assertEquals(17.46, Double.parseDouble(oTable[4][8].toString()),0.001);
      assertEquals(16.69, Double.parseDouble(oTable[5][8].toString()),0.001);
      assertEquals(17.48, Double.parseDouble(oTable[6][8].toString()),0.001);
      
      assertEquals(22.73, Double.parseDouble(oTable[1][9].toString()),0.001);
      assertEquals(20.94, Double.parseDouble(oTable[2][9].toString()),0.001);
      assertEquals(15.94, Double.parseDouble(oTable[3][9].toString()),0.001);
      assertEquals(18.87, Double.parseDouble(oTable[4][9].toString()),0.001);
      assertEquals(22.85, Double.parseDouble(oTable[5][9].toString()),0.001);
      assertEquals(19.53, Double.parseDouble(oTable[6][9].toString()),0.001);
      
      assertEquals(7.92, Double.parseDouble(oTable[1][10].toString()),0.001);
      assertEquals(8.18, Double.parseDouble(oTable[2][10].toString()),0.001);
      assertEquals(8.43, Double.parseDouble(oTable[3][10].toString()),0.001);
      assertEquals(8.68, Double.parseDouble(oTable[4][10].toString()),0.001);
      assertEquals(8.94, Double.parseDouble(oTable[5][10].toString()),0.001);
      assertEquals(9.19, Double.parseDouble(oTable[6][10].toString()),0.001);
      
      assertEquals(2.48, Double.parseDouble(oTable[1][11].toString()),0.001);
      assertEquals(2.56, Double.parseDouble(oTable[2][11].toString()),0.001);
      assertEquals(2.64, Double.parseDouble(oTable[3][11].toString()),0.001);
      assertEquals(2.72, Double.parseDouble(oTable[4][11].toString()),0.001);
      assertEquals(2.8, Double.parseDouble(oTable[5][11].toString()),0.001);
      assertEquals(2.88, Double.parseDouble(oTable[6][11].toString()),0.001);
      
      assertEquals(-9.6, Double.parseDouble(oTable[1][12].toString()),0.001);
      assertEquals(-6.94, Double.parseDouble(oTable[2][12].toString()),0.001);
      assertEquals(-6.51, Double.parseDouble(oTable[3][12].toString()),0.001);
      assertEquals(-1.59, Double.parseDouble(oTable[4][12].toString()),0.001);
      assertEquals(0.19, Double.parseDouble(oTable[5][12].toString()),0.001);
      assertEquals(2.49, Double.parseDouble(oTable[6][12].toString()),0.001);
      
      
      
      bIsPrecip = true;
      oChild = new ClimateImporterValuesEditor(oWindow, bIsPrecip, 
          bIsCalendarYear, iNumTimesteps, iPreRunTimesteps);
      oTable = oChild.m_oDataTable.getData().mp_oTableData;
      assertEquals(161.48, Double.parseDouble(oTable[1][1].toString()),0.001);
      assertEquals(152.09, Double.parseDouble(oTable[2][1].toString()),0.001);
      assertEquals(152.5 , Double.parseDouble(oTable[3][1].toString()),0.001);
      assertEquals(152.71, Double.parseDouble(oTable[4][1].toString()),0.001);
      assertEquals(78.44 , Double.parseDouble(oTable[5][1].toString()),0.001);
      assertEquals(123.19, Double.parseDouble(oTable[6][1].toString()),0.001);
      
      assertEquals(199.32, Double.parseDouble(oTable[1][2].toString()),0.001);
      assertEquals(192.94, Double.parseDouble(oTable[2][2].toString()),0.001);
      assertEquals(188.1 , Double.parseDouble(oTable[3][2].toString()),0.001);
      assertEquals(153.95, Double.parseDouble(oTable[4][2].toString()),0.001);
      assertEquals(89.79 , Double.parseDouble(oTable[5][2].toString()),0.001);
      assertEquals(189.67, Double.parseDouble(oTable[6][2].toString()),0.001);
      
      assertEquals(169.99, Double.parseDouble(oTable[1][3].toString()),0.001);
      assertEquals(105.49, Double.parseDouble(oTable[2][3].toString()),0.001);
      assertEquals(141.34, Double.parseDouble(oTable[3][3].toString()),0.001);
      assertEquals(84.71 , Double.parseDouble(oTable[4][3].toString()),0.001);
      assertEquals(188.92, Double.parseDouble(oTable[5][3].toString()),0.001);
      assertEquals(93.47 , Double.parseDouble(oTable[6][3].toString()),0.001);
      
      assertEquals(82.02 , Double.parseDouble(oTable[1][4].toString()),0.001);
      assertEquals(77.49 , Double.parseDouble(oTable[2][4].toString()),0.001);
      assertEquals(186.32, Double.parseDouble(oTable[3][4].toString()),0.001);
      assertEquals(174.4 , Double.parseDouble(oTable[4][4].toString()),0.001);
      assertEquals(161.25, Double.parseDouble(oTable[5][4].toString()),0.001);
      assertEquals(99.76 , Double.parseDouble(oTable[6][4].toString()),0.001);
      
      assertEquals(132.54, Double.parseDouble(oTable[1][5].toString()),0.001);
      assertEquals(121.74, Double.parseDouble(oTable[2][5].toString()),0.001);
      assertEquals(178.18, Double.parseDouble(oTable[3][5].toString()),0.001);
      assertEquals(102.86, Double.parseDouble(oTable[4][5].toString()),0.001);
      assertEquals(102.84, Double.parseDouble(oTable[5][5].toString()),0.001);
      assertEquals(172.41, Double.parseDouble(oTable[6][5].toString()),0.001);
      
      assertEquals(157.67, Double.parseDouble(oTable[1][6].toString()),0.001);
      assertEquals(133.89, Double.parseDouble(oTable[2][6].toString()),0.001);
      assertEquals(147.29, Double.parseDouble(oTable[3][6].toString()),0.001);
      assertEquals(123.74, Double.parseDouble(oTable[4][6].toString()),0.001);
      assertEquals(126.66, Double.parseDouble(oTable[5][6].toString()),0.001);
      assertEquals(134.35, Double.parseDouble(oTable[6][6].toString()),0.001);
      
      assertEquals(97.63 , Double.parseDouble(oTable[1][7].toString()),0.001);
      assertEquals(141.94, Double.parseDouble(oTable[2][7].toString()),0.001);
      assertEquals(173.88, Double.parseDouble(oTable[3][7].toString()),0.001);
      assertEquals(166.53, Double.parseDouble(oTable[4][7].toString()),0.001);
      assertEquals(140.41, Double.parseDouble(oTable[5][7].toString()),0.001);
      assertEquals(128.89, Double.parseDouble(oTable[6][7].toString()),0.001);
      
      assertEquals(145.57, Double.parseDouble(oTable[1][8].toString()),0.001);
      assertEquals(156.69, Double.parseDouble(oTable[2][8].toString()),0.001);
      assertEquals(146.55, Double.parseDouble(oTable[3][8].toString()),0.001);
      assertEquals(167.73, Double.parseDouble(oTable[4][8].toString()),0.001);
      assertEquals(147.67, Double.parseDouble(oTable[5][8].toString()),0.001);
      assertEquals(119.52, Double.parseDouble(oTable[6][8].toString()),0.001);
      
      assertEquals(134.38, Double.parseDouble(oTable[1][9].toString()),0.001);
      assertEquals(89.78 , Double.parseDouble(oTable[2][9].toString()),0.001);
      assertEquals(173.37, Double.parseDouble(oTable[3][9].toString()),0.001);
      assertEquals(146.16, Double.parseDouble(oTable[4][9].toString()),0.001);
      assertEquals(108.71, Double.parseDouble(oTable[5][9].toString()),0.001);
      assertEquals(90.68 , Double.parseDouble(oTable[6][9].toString()),0.001);
      
      assertEquals(100.17, Double.parseDouble(oTable[1][10].toString()),0.001);
      assertEquals(178.11, Double.parseDouble(oTable[2][10].toString()),0.001);
      assertEquals(120.16, Double.parseDouble(oTable[3][10].toString()),0.001);
      assertEquals(198.43, Double.parseDouble(oTable[4][10].toString()),0.001);
      assertEquals(75.22 , Double.parseDouble(oTable[5][10].toString()),0.001);
      assertEquals(198.3 , Double.parseDouble(oTable[6][10].toString()),0.001);

      assertEquals(112.34, Double.parseDouble(oTable[1][11].toString()),0.001);
      assertEquals(191.22, Double.parseDouble(oTable[2][11].toString()),0.001);
      assertEquals(125.47, Double.parseDouble(oTable[3][11].toString()),0.001);
      assertEquals(79.8  , Double.parseDouble(oTable[4][11].toString()),0.001);
      assertEquals(116.29, Double.parseDouble(oTable[5][11].toString()),0.001);
      assertEquals(146.75, Double.parseDouble(oTable[6][11].toString()),0.001);

      assertEquals(137.22, Double.parseDouble(oTable[1][12].toString()),0.001);
      assertEquals(101.29, Double.parseDouble(oTable[2][12].toString()),0.001);
      assertEquals(159.21, Double.parseDouble(oTable[3][12].toString()),0.001);
      assertEquals(171.75, Double.parseDouble(oTable[4][12].toString()),0.001);
      assertEquals(191.54, Double.parseDouble(oTable[5][12].toString()),0.001);
      assertEquals(131.83, Double.parseDouble(oTable[6][12].toString()),0.001);
      
    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
    
  /**
   * Tests that the setting of values works using files.
   */
  public void testReadingFiles() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = ClimateImporterTest.writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Call the interface
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      ClimateImporter oClim = (ClimateImporter) p_oBehs.get(0);
      ClimateImporterEditor oWindow = new ClimateImporterEditor(null, oManager, oMainWindow, oClim);
      
      oWindow.readDataFile(new File(writePrecipFile1()), "ReadPptData");
      oWindow.readDataFile(new File(writeTempFile1()), "ReadTempData");
      oWindow.readNitrogenDataFile(new File(writeNDepFile1()));
      oWindow.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Check to see what data is in the behavior
      assertEquals(4.95 , oClim.getTempData(1, 1), 0.001);
      assertEquals(0.72 , oClim.getTempData(2, 1), 0.001);
      assertEquals(6.33 , oClim.getTempData(3, 1), 0.001);
      assertEquals(9.2  , oClim.getTempData(4, 1), 0.001);
      assertEquals(15.94, oClim.getTempData(5, 1), 0.001);
      assertEquals(9.79 , oClim.getTempData(6, 1), 0.001);
      
      assertEquals(-1.55, oClim.getTempData(1, 2), 0.001);
      assertEquals(-5.03, oClim.getTempData(2, 2), 0.001);
      assertEquals(6.01, oClim.getTempData(3, 2), 0.001);
      assertEquals(4.71, oClim.getTempData(4, 2), 0.001);
      assertEquals(-1.89, oClim.getTempData(5, 2), 0.001);
      assertEquals(8.4, oClim.getTempData(6, 2), 0.001);

      assertEquals(0.67, oClim.getTempData(1, 3), 0.001);
      assertEquals(7.74, oClim.getTempData(2, 3), 0.001);
      assertEquals(-0.58, oClim.getTempData(3, 3), 0.001);
      assertEquals(6.45, oClim.getTempData(4, 3), 0.001);
      assertEquals(15.16, oClim.getTempData(5, 3), 0.001);
      assertEquals(-2.36, oClim.getTempData(6, 3), 0.001);

      assertEquals(-1.34, oClim.getTempData(1, 4), 0.001);
      assertEquals(13.97, oClim.getTempData(2, 4), 0.001);
      assertEquals(21.15, oClim.getTempData(3, 4), 0.001);
      assertEquals(1.3, oClim.getTempData(4, 4), 0.001);
      assertEquals(7.67, oClim.getTempData(5, 4), 0.001);
      assertEquals(-5.62, oClim.getTempData(6, 4), 0.001);

      assertEquals(11.15, oClim.getTempData(1, 5), 0.001);
      assertEquals(-4.27, oClim.getTempData(2, 5), 0.001);
      assertEquals(10.61, oClim.getTempData(3, 5), 0.001);
      assertEquals(3.4, oClim.getTempData(4, 5), 0.001);
      assertEquals(-7.03, oClim.getTempData(5, 5), 0.001);
      assertEquals(-0.92, oClim.getTempData(6, 5), 0.001);

      assertEquals(9.6, oClim.getTempData(1, 6), 0.001);
      assertEquals(22.7, oClim.getTempData(2, 6), 0.001);
      assertEquals(5.71, oClim.getTempData(3, 6), 0.001);
      assertEquals(-6.58, oClim.getTempData(4, 6), 0.001);
      assertEquals(4.4, oClim.getTempData(5, 6), 0.001);
      assertEquals(13.81, oClim.getTempData(6, 6), 0.001);

      assertEquals(6.27, oClim.getTempData(1, 7), 0.001);
      assertEquals(7.96, oClim.getTempData(2, 7), 0.001);
      assertEquals(10.91, oClim.getTempData(3, 7), 0.001);
      assertEquals(-8.75, oClim.getTempData(4, 7), 0.001);
      assertEquals(16.89, oClim.getTempData(5, 7), 0.001);
      assertEquals(21.19, oClim.getTempData(6, 7), 0.001);

      assertEquals(20.21, oClim.getTempData(1, 8), 0.001);
      assertEquals(17.39, oClim.getTempData(2, 8), 0.001);
      assertEquals(2.94, oClim.getTempData(3, 8), 0.001);
      assertEquals(15.73, oClim.getTempData(4, 8), 0.001);
      assertEquals(-1.44, oClim.getTempData(5, 8), 0.001);
      assertEquals(-5.15, oClim.getTempData(6, 8), 0.001);

      assertEquals(-3.34, oClim.getTempData(1, 9), 0.001);
      assertEquals(21.2, oClim.getTempData(2, 9), 0.001);
      assertEquals(-3.89, oClim.getTempData(3, 9), 0.001);
      assertEquals(1.05, oClim.getTempData(4, 9), 0.001);
      assertEquals(20.06, oClim.getTempData(5, 9), 0.001);
      assertEquals(-6.95, oClim.getTempData(6, 9), 0.001);

      assertEquals(-2.12, oClim.getTempData(1, 10), 0.001);
      assertEquals(-7.14, oClim.getTempData(2, 10), 0.001);
      assertEquals(-6.97, oClim.getTempData(3, 10), 0.001);
      assertEquals(22.49, oClim.getTempData(4, 10), 0.001);
      assertEquals(17.71, oClim.getTempData(5, 10), 0.001);
      assertEquals(6.22, oClim.getTempData(6, 10), 0.001);

      assertEquals(-9.9, oClim.getTempData(1, 11), 0.001);
      assertEquals(11.75, oClim.getTempData(2, 11), 0.001);
      assertEquals(-9.49, oClim.getTempData(3, 11), 0.001);
      assertEquals(9.39, oClim.getTempData(4, 11), 0.001);
      assertEquals(9.48, oClim.getTempData(5, 11), 0.001);
      assertEquals(20.23, oClim.getTempData(6, 11), 0.001);

      assertEquals(20.79, oClim.getTempData(1, 12), 0.001);
      assertEquals(-2.93, oClim.getTempData(2, 12), 0.001);
      assertEquals(-6.06, oClim.getTempData(3, 12), 0.001);
      assertEquals(11.18, oClim.getTempData(4, 12), 0.001);
      assertEquals(0.47, oClim.getTempData(5, 12), 0.001);
      assertEquals(9.6, oClim.getTempData(6, 12), 0.001);

            

      assertEquals(858, oClim.getPptData(1, 1), 0.001);
      assertEquals(1221, oClim.getPptData(2, 1), 0.001);
      assertEquals(1103, oClim.getPptData(3, 1), 0.001);
      assertEquals(1052, oClim.getPptData(4, 1), 0.001);
      assertEquals(1029, oClim.getPptData(5, 1), 0.001);
      assertEquals(1500, oClim.getPptData(6, 1), 0.001);

      assertEquals(1346, oClim.getPptData(1, 2), 0.001);
      assertEquals(798, oClim.getPptData(2, 2), 0.001);
      assertEquals(502, oClim.getPptData(3, 2), 0.001);
      assertEquals(1347, oClim.getPptData(4, 2), 0.001);
      assertEquals(1027, oClim.getPptData(5, 2), 0.001);
      assertEquals(851, oClim.getPptData(6, 2), 0.001);

      assertEquals(570, oClim.getPptData(1, 3), 0.001);
      assertEquals(1223, oClim.getPptData(2, 3), 0.001);
      assertEquals(857, oClim.getPptData(3, 3), 0.001);
      assertEquals(725, oClim.getPptData(4, 3), 0.001);
      assertEquals(1471, oClim.getPptData(5, 3), 0.001);
      assertEquals(1116, oClim.getPptData(6, 3), 0.001);

      assertEquals(1109, oClim.getPptData(1, 4), 0.001);
      assertEquals(1079, oClim.getPptData(2, 4), 0.001);
      assertEquals(644, oClim.getPptData(3, 4), 0.001);
      assertEquals(1246, oClim.getPptData(4, 4), 0.001);
      assertEquals(778, oClim.getPptData(5, 4), 0.001);
      assertEquals(1487, oClim.getPptData(6, 4), 0.001);

      assertEquals(934, oClim.getPptData(1, 5), 0.001);
      assertEquals(1342, oClim.getPptData(2, 5), 0.001);
      assertEquals(804, oClim.getPptData(3, 5), 0.001);
      assertEquals(971, oClim.getPptData(4, 5), 0.001);
      assertEquals(1365, oClim.getPptData(5, 5), 0.001);
      assertEquals(1375, oClim.getPptData(6, 5), 0.001);

      assertEquals(784, oClim.getPptData(1, 6), 0.001);
      assertEquals(754, oClim.getPptData(2, 6), 0.001);
      assertEquals(732, oClim.getPptData(3, 6), 0.001);
      assertEquals(1440, oClim.getPptData(4, 6), 0.001);
      assertEquals(976, oClim.getPptData(5, 6), 0.001);
      assertEquals(767, oClim.getPptData(6, 6), 0.001);

      assertEquals(1139, oClim.getPptData(1, 7), 0.001);
      assertEquals(1143, oClim.getPptData(2, 7), 0.001);
      assertEquals(1456, oClim.getPptData(3, 7), 0.001);
      assertEquals(924, oClim.getPptData(4, 7), 0.001);
      assertEquals(1287, oClim.getPptData(5, 7), 0.001);
      assertEquals(654, oClim.getPptData(6, 7), 0.001);

      assertEquals(1328, oClim.getPptData(1, 8), 0.001);
      assertEquals(656, oClim.getPptData(2, 8), 0.001);
      assertEquals(633, oClim.getPptData(3, 8), 0.001);
      assertEquals(790, oClim.getPptData(4, 8), 0.001);
      assertEquals(636, oClim.getPptData(5, 8), 0.001);
      assertEquals(786, oClim.getPptData(6, 8), 0.001);

      assertEquals(1483, oClim.getPptData(1, 9), 0.001);
      assertEquals(924, oClim.getPptData(2, 9), 0.001);
      assertEquals(548, oClim.getPptData(3, 9), 0.001);
      assertEquals(948, oClim.getPptData(4, 9), 0.001);
      assertEquals(951, oClim.getPptData(5, 9), 0.001);
      assertEquals(1042, oClim.getPptData(6, 9), 0.001);

      assertEquals(513, oClim.getPptData(1, 10), 0.001);
      assertEquals(1237, oClim.getPptData(2, 10), 0.001);
      assertEquals(922, oClim.getPptData(3, 10), 0.001);
      assertEquals(540, oClim.getPptData(4, 10), 0.001);
      assertEquals(565, oClim.getPptData(5, 10), 0.001);
      assertEquals(521, oClim.getPptData(6, 10), 0.001);

      assertEquals(531, oClim.getPptData(1, 11), 0.001);
      assertEquals(1300, oClim.getPptData(2, 11), 0.001);
      assertEquals(1425, oClim.getPptData(3, 11), 0.001);
      assertEquals(1361, oClim.getPptData(4, 11), 0.001);
      assertEquals(993, oClim.getPptData(5, 11), 0.001);
      assertEquals(981, oClim.getPptData(6, 11), 0.001);

      assertEquals(1183, oClim.getPptData(1, 12), 0.001);
      assertEquals(758, oClim.getPptData(2, 12), 0.001);
      assertEquals(1107, oClim.getPptData(3, 12), 0.001);
      assertEquals(1437, oClim.getPptData(4, 12), 0.001);
      assertEquals(928, oClim.getPptData(5, 12), 0.001);
      assertEquals(553, oClim.getPptData(6, 12), 0.001);
      
      
      
      //----- Nitrogen data -------------------------------------------------//
      assertEquals(4.39, oClim.getNDepData(1), 0.001);
      assertEquals(6.81, oClim.getNDepData(2), 0.001);
      assertEquals(2.92, oClim.getNDepData(3), 0.001);
      assertEquals(5.07, oClim.getNDepData(4), 0.001);
      assertEquals(6.71, oClim.getNDepData(5), 0.001);
      assertEquals(6.34, oClim.getNDepData(6), 0.001);


    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();      
    }
  }
  
  /**
   * Tests that the setting of values works using files.
   */
  public void testReadingFiles2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = ClimateImporterTest.writeValidXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      
      //Call the interface
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      ClimateImporter oClim = (ClimateImporter) p_oBehs.get(0);
      ClimateImporterEditor oWindow = new ClimateImporterEditor(null, oManager, oMainWindow, oClim);
      
      oWindow.readDataFile(new File(writePrecipFile2()), "ReadPptData");
      oWindow.readDataFile(new File(writeTempFile2()), "ReadTempData");
      oWindow.readNitrogenDataFile(new File(writeNDepFile1()));
      oWindow.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Check to see what data is in the behavior
      double fVal;
      int i, j;
      for (i = -3; i < 7; i++) {
        if (i != 0) {
          for (j = 1; j < 13; j++) {
            fVal = oClim.getTempData(i, j);
            assertEquals(i + (j*0.1), fVal, 0.001);
          }
        }
      }
      
      assertEquals(2.34, oClim.getPptData(-3, 1), 0.001);
      assertEquals(2.39, oClim.getPptData(-3, 2), 0.001);
      assertEquals(3.06, oClim.getPptData(-3, 3), 0.001);
      assertEquals(4.04, oClim.getPptData(-3, 4), 0.001);
      assertEquals(2.97, oClim.getPptData(-3, 5), 0.001);
      assertEquals(4.93, oClim.getPptData(-3, 6), 0.001);
      assertEquals(3.15, oClim.getPptData(-3, 7), 0.001);
      assertEquals(1.41, oClim.getPptData(-3, 8), 0.001);
      assertEquals(2.05, oClim.getPptData(-3, 9), 0.001);
      assertEquals(3.07, oClim.getPptData(-3, 10), 0.001);
      assertEquals(3.94, oClim.getPptData(-3, 11), 0.001);
      assertEquals(3.15, oClim.getPptData(-3, 12), 0.001);
      
      assertEquals(2.53, oClim.getPptData(-2, 1), 0.001);
      assertEquals(2.27, oClim.getPptData(-2, 2), 0.001);
      assertEquals(4.99, oClim.getPptData(-2, 3), 0.001);
      assertEquals(1.22, oClim.getPptData(-2, 4), 0.001);
      assertEquals(4.53, oClim.getPptData(-2, 5), 0.001);
      assertEquals(1.07, oClim.getPptData(-2, 6), 0.001);
      assertEquals(4   , oClim.getPptData(-2, 7), 0.001);
      assertEquals(2.41, oClim.getPptData(-2, 8), 0.001);
      assertEquals(1.94, oClim.getPptData(-2, 9), 0.001);
      assertEquals(3.05, oClim.getPptData(-2, 10), 0.001);
      assertEquals(2.3, oClim.getPptData(-2, 11), 0.001);
      assertEquals(4.29, oClim.getPptData(-2, 12), 0.001);
      
      assertEquals(3.25, oClim.getPptData(-1, 1), 0.001);
      assertEquals(4.85, oClim.getPptData(-1, 2), 0.001);
      assertEquals(3.22, oClim.getPptData(-1, 3), 0.001);
      assertEquals(2.59, oClim.getPptData(-1, 4), 0.001);
      assertEquals(4.9 , oClim.getPptData(-1, 5), 0.001);
      assertEquals(2.84, oClim.getPptData(-1, 6), 0.001);
      assertEquals(1.95, oClim.getPptData(-1, 7), 0.001);
      assertEquals(3.68, oClim.getPptData(-1, 8), 0.001);
      assertEquals(3.65, oClim.getPptData(-1, 9), 0.001);
      assertEquals(1.02, oClim.getPptData(-1, 10), 0.001);
      assertEquals(1.77, oClim.getPptData(-1, 11), 0.001);
      assertEquals(3.76, oClim.getPptData(-1, 12), 0.001);
      
      assertEquals(4.48, oClim.getPptData(1, 1), 0.001);
      assertEquals(4.29, oClim.getPptData(1, 2), 0.001);
      assertEquals(3.55, oClim.getPptData(1, 3), 0.001);
      assertEquals(2.41, oClim.getPptData(1, 4), 0.001);
      assertEquals(1.16, oClim.getPptData(1, 5), 0.001);
      assertEquals(3.01, oClim.getPptData(1, 6), 0.001);
      assertEquals(3.95, oClim.getPptData(1, 7), 0.001);
      assertEquals(2.38, oClim.getPptData(1, 8), 0.001);
      assertEquals(2.5 , oClim.getPptData(1, 9), 0.001);
      assertEquals(3.26, oClim.getPptData(1, 10), 0.001);
      assertEquals(2.55, oClim.getPptData(1, 11), 0.001);
      assertEquals(3.19, oClim.getPptData(1, 12), 0.001);
      
      assertEquals(3.37, oClim.getPptData(2, 1), 0.001);
      assertEquals(3.24, oClim.getPptData(2, 2), 0.001);
      assertEquals(4.9 , oClim.getPptData(2, 3), 0.001);
      assertEquals(1.32, oClim.getPptData(2, 4), 0.001);
      assertEquals(2.11, oClim.getPptData(2, 5), 0.001);
      assertEquals(3.56, oClim.getPptData(2, 6), 0.001);
      assertEquals(1.43, oClim.getPptData(2, 7), 0.001);
      assertEquals(3.24, oClim.getPptData(2, 8), 0.001);
      assertEquals(3.33, oClim.getPptData(2, 9), 0.001);
      assertEquals(3.18, oClim.getPptData(2, 10), 0.001);
      assertEquals(1.25, oClim.getPptData(2, 11), 0.001);
      assertEquals(2.26, oClim.getPptData(2, 12), 0.001);
      
      assertEquals(1.53, oClim.getPptData(3, 1), 0.001);
      assertEquals(4.47, oClim.getPptData(3, 2), 0.001);
      assertEquals(1.4 , oClim.getPptData(3, 3), 0.001);
      assertEquals(3.09, oClim.getPptData(3, 4), 0.001);
      assertEquals(2.26, oClim.getPptData(3, 5), 0.001);
      assertEquals(3.39, oClim.getPptData(3, 6), 0.001);
      assertEquals(1.99, oClim.getPptData(3, 7), 0.001);
      assertEquals(2.57, oClim.getPptData(3, 8), 0.001);
      assertEquals(2.57, oClim.getPptData(3, 9), 0.001);
      assertEquals(4.04, oClim.getPptData(3, 10), 0.001);
      assertEquals(1.52, oClim.getPptData(3, 11), 0.001);
      assertEquals(1.22, oClim.getPptData(3, 12), 0.001);
      
      assertEquals(4.44, oClim.getPptData(4, 1), 0.001);
      assertEquals(4.55, oClim.getPptData(4, 2), 0.001);
      assertEquals(1.47, oClim.getPptData(4, 3), 0.001);
      assertEquals(2.34, oClim.getPptData(4, 4), 0.001);
      assertEquals(1.71, oClim.getPptData(4, 5), 0.001);
      assertEquals(1.3 , oClim.getPptData(4, 6), 0.001);
      assertEquals(2.15, oClim.getPptData(4, 7), 0.001);
      assertEquals(1.31, oClim.getPptData(4, 8), 0.001);
      assertEquals(4.07, oClim.getPptData(4, 9), 0.001);
      assertEquals(2.46, oClim.getPptData(4, 10), 0.001);
      assertEquals(1.4 , oClim.getPptData(4, 11), 0.001);
      assertEquals(4.7 , oClim.getPptData(4, 12), 0.001);
      
      assertEquals(1.95, oClim.getPptData(5, 1), 0.001);
      assertEquals(3.43, oClim.getPptData(5, 2), 0.001);
      assertEquals(1.16, oClim.getPptData(5, 3), 0.001);
      assertEquals(3.19, oClim.getPptData(5, 4), 0.001);
      assertEquals(4.87, oClim.getPptData(5, 5), 0.001);
      assertEquals(3.04, oClim.getPptData(5, 6), 0.001);
      assertEquals(4.65, oClim.getPptData(5, 7), 0.001);
      assertEquals(3.8 , oClim.getPptData(5, 8), 0.001);
      assertEquals(1.99, oClim.getPptData(5, 9), 0.001);
      assertEquals(1.67, oClim.getPptData(5, 10), 0.001);
      assertEquals(3.65, oClim.getPptData(5, 11), 0.001);
      assertEquals(2.57, oClim.getPptData(5, 12), 0.001);
      
      assertEquals(4.79, oClim.getPptData(6, 1), 0.001);
      assertEquals(4.61, oClim.getPptData(6, 2), 0.001);
      assertEquals(3.02, oClim.getPptData(6, 3), 0.001);
      assertEquals(1.77, oClim.getPptData(6, 4), 0.001);
      assertEquals(3.81, oClim.getPptData(6, 5), 0.001);
      assertEquals(1.57, oClim.getPptData(6, 6), 0.001);
      assertEquals(1.72, oClim.getPptData(6, 7), 0.001);
      assertEquals(4.74, oClim.getPptData(6, 8), 0.001);
      assertEquals(2.37, oClim.getPptData(6, 9), 0.001);
      assertEquals(1.17, oClim.getPptData(6, 10), 0.001);
      assertEquals(4.47, oClim.getPptData(6, 11), 0.001);
      assertEquals(2.87, oClim.getPptData(6, 12), 0.001);   
      
      //----- Nitrogen data -------------------------------------------------//
      assertEquals(4.39, oClim.getNDepData(1), 0.001);
      assertEquals(6.81, oClim.getNDepData(2), 0.001);
      assertEquals(2.92, oClim.getNDepData(3), 0.001);
      assertEquals(5.07, oClim.getNDepData(4), 0.001);
      assertEquals(6.71, oClim.getNDepData(5), 0.001);
      assertEquals(6.34, oClim.getNDepData(6), 0.001);
      
      //Write it out as a parameter file and read it back in
      assertTrue(oManager.writeParameterFile(sFileName));

      oManager.inputXMLParameterFile(sFileName);
      oStateBeh = oManager.getStateChangeBehaviors();
      p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      oClim = (ClimateImporter) p_oBehs.get(0);
      
      for (i = -3; i < 7; i++) {
        if (i != 0) {
          for (j = 1; j < 13; j++) {
            fVal = oClim.getTempData(i, j);
            assertEquals(i + (j*0.1), fVal, 0.001);
          }
        }
      }
      
      assertEquals(2.34, oClim.getPptData(-3, 1), 0.001);
      assertEquals(2.39, oClim.getPptData(-3, 2), 0.001);
      assertEquals(3.06, oClim.getPptData(-3, 3), 0.001);
      assertEquals(4.04, oClim.getPptData(-3, 4), 0.001);
      assertEquals(2.97, oClim.getPptData(-3, 5), 0.001);
      assertEquals(4.93, oClim.getPptData(-3, 6), 0.001);
      assertEquals(3.15, oClim.getPptData(-3, 7), 0.001);
      assertEquals(1.41, oClim.getPptData(-3, 8), 0.001);
      assertEquals(2.05, oClim.getPptData(-3, 9), 0.001);
      assertEquals(3.07, oClim.getPptData(-3, 10), 0.001);
      assertEquals(3.94, oClim.getPptData(-3, 11), 0.001);
      assertEquals(3.15, oClim.getPptData(-3, 12), 0.001);
      
      assertEquals(2.53, oClim.getPptData(-2, 1), 0.001);
      assertEquals(2.27, oClim.getPptData(-2, 2), 0.001);
      assertEquals(4.99, oClim.getPptData(-2, 3), 0.001);
      assertEquals(1.22, oClim.getPptData(-2, 4), 0.001);
      assertEquals(4.53, oClim.getPptData(-2, 5), 0.001);
      assertEquals(1.07, oClim.getPptData(-2, 6), 0.001);
      assertEquals(4   , oClim.getPptData(-2, 7), 0.001);
      assertEquals(2.41, oClim.getPptData(-2, 8), 0.001);
      assertEquals(1.94, oClim.getPptData(-2, 9), 0.001);
      assertEquals(3.05, oClim.getPptData(-2, 10), 0.001);
      assertEquals(2.3, oClim.getPptData(-2, 11), 0.001);
      assertEquals(4.29, oClim.getPptData(-2, 12), 0.001);
      
      assertEquals(3.25, oClim.getPptData(-1, 1), 0.001);
      assertEquals(4.85, oClim.getPptData(-1, 2), 0.001);
      assertEquals(3.22, oClim.getPptData(-1, 3), 0.001);
      assertEquals(2.59, oClim.getPptData(-1, 4), 0.001);
      assertEquals(4.9 , oClim.getPptData(-1, 5), 0.001);
      assertEquals(2.84, oClim.getPptData(-1, 6), 0.001);
      assertEquals(1.95, oClim.getPptData(-1, 7), 0.001);
      assertEquals(3.68, oClim.getPptData(-1, 8), 0.001);
      assertEquals(3.65, oClim.getPptData(-1, 9), 0.001);
      assertEquals(1.02, oClim.getPptData(-1, 10), 0.001);
      assertEquals(1.77, oClim.getPptData(-1, 11), 0.001);
      assertEquals(3.76, oClim.getPptData(-1, 12), 0.001);
      
      assertEquals(4.48, oClim.getPptData(1, 1), 0.001);
      assertEquals(4.29, oClim.getPptData(1, 2), 0.001);
      assertEquals(3.55, oClim.getPptData(1, 3), 0.001);
      assertEquals(2.41, oClim.getPptData(1, 4), 0.001);
      assertEquals(1.16, oClim.getPptData(1, 5), 0.001);
      assertEquals(3.01, oClim.getPptData(1, 6), 0.001);
      assertEquals(3.95, oClim.getPptData(1, 7), 0.001);
      assertEquals(2.38, oClim.getPptData(1, 8), 0.001);
      assertEquals(2.5 , oClim.getPptData(1, 9), 0.001);
      assertEquals(3.26, oClim.getPptData(1, 10), 0.001);
      assertEquals(2.55, oClim.getPptData(1, 11), 0.001);
      assertEquals(3.19, oClim.getPptData(1, 12), 0.001);
      
      assertEquals(3.37, oClim.getPptData(2, 1), 0.001);
      assertEquals(3.24, oClim.getPptData(2, 2), 0.001);
      assertEquals(4.9 , oClim.getPptData(2, 3), 0.001);
      assertEquals(1.32, oClim.getPptData(2, 4), 0.001);
      assertEquals(2.11, oClim.getPptData(2, 5), 0.001);
      assertEquals(3.56, oClim.getPptData(2, 6), 0.001);
      assertEquals(1.43, oClim.getPptData(2, 7), 0.001);
      assertEquals(3.24, oClim.getPptData(2, 8), 0.001);
      assertEquals(3.33, oClim.getPptData(2, 9), 0.001);
      assertEquals(3.18, oClim.getPptData(2, 10), 0.001);
      assertEquals(1.25, oClim.getPptData(2, 11), 0.001);
      assertEquals(2.26, oClim.getPptData(2, 12), 0.001);
      
      assertEquals(1.53, oClim.getPptData(3, 1), 0.001);
      assertEquals(4.47, oClim.getPptData(3, 2), 0.001);
      assertEquals(1.4 , oClim.getPptData(3, 3), 0.001);
      assertEquals(3.09, oClim.getPptData(3, 4), 0.001);
      assertEquals(2.26, oClim.getPptData(3, 5), 0.001);
      assertEquals(3.39, oClim.getPptData(3, 6), 0.001);
      assertEquals(1.99, oClim.getPptData(3, 7), 0.001);
      assertEquals(2.57, oClim.getPptData(3, 8), 0.001);
      assertEquals(2.57, oClim.getPptData(3, 9), 0.001);
      assertEquals(4.04, oClim.getPptData(3, 10), 0.001);
      assertEquals(1.52, oClim.getPptData(3, 11), 0.001);
      assertEquals(1.22, oClim.getPptData(3, 12), 0.001);
      
      assertEquals(4.44, oClim.getPptData(4, 1), 0.001);
      assertEquals(4.55, oClim.getPptData(4, 2), 0.001);
      assertEquals(1.47, oClim.getPptData(4, 3), 0.001);
      assertEquals(2.34, oClim.getPptData(4, 4), 0.001);
      assertEquals(1.71, oClim.getPptData(4, 5), 0.001);
      assertEquals(1.3 , oClim.getPptData(4, 6), 0.001);
      assertEquals(2.15, oClim.getPptData(4, 7), 0.001);
      assertEquals(1.31, oClim.getPptData(4, 8), 0.001);
      assertEquals(4.07, oClim.getPptData(4, 9), 0.001);
      assertEquals(2.46, oClim.getPptData(4, 10), 0.001);
      assertEquals(1.4 , oClim.getPptData(4, 11), 0.001);
      assertEquals(4.7 , oClim.getPptData(4, 12), 0.001);
      
      assertEquals(1.95, oClim.getPptData(5, 1), 0.001);
      assertEquals(3.43, oClim.getPptData(5, 2), 0.001);
      assertEquals(1.16, oClim.getPptData(5, 3), 0.001);
      assertEquals(3.19, oClim.getPptData(5, 4), 0.001);
      assertEquals(4.87, oClim.getPptData(5, 5), 0.001);
      assertEquals(3.04, oClim.getPptData(5, 6), 0.001);
      assertEquals(4.65, oClim.getPptData(5, 7), 0.001);
      assertEquals(3.8 , oClim.getPptData(5, 8), 0.001);
      assertEquals(1.99, oClim.getPptData(5, 9), 0.001);
      assertEquals(1.67, oClim.getPptData(5, 10), 0.001);
      assertEquals(3.65, oClim.getPptData(5, 11), 0.001);
      assertEquals(2.57, oClim.getPptData(5, 12), 0.001);
      
      assertEquals(4.79, oClim.getPptData(6, 1), 0.001);
      assertEquals(4.61, oClim.getPptData(6, 2), 0.001);
      assertEquals(3.02, oClim.getPptData(6, 3), 0.001);
      assertEquals(1.77, oClim.getPptData(6, 4), 0.001);
      assertEquals(3.81, oClim.getPptData(6, 5), 0.001);
      assertEquals(1.57, oClim.getPptData(6, 6), 0.001);
      assertEquals(1.72, oClim.getPptData(6, 7), 0.001);
      assertEquals(4.74, oClim.getPptData(6, 8), 0.001);
      assertEquals(2.37, oClim.getPptData(6, 9), 0.001);
      assertEquals(1.17, oClim.getPptData(6, 10), 0.001);
      assertEquals(4.47, oClim.getPptData(6, 11), 0.001);
      assertEquals(2.87, oClim.getPptData(6, 12), 0.001);   
      
      //----- Nitrogen data -------------------------------------------------//
      assertEquals(4.39, oClim.getNDepData(1), 0.001);
      assertEquals(6.81, oClim.getNDepData(2), 0.001);
      assertEquals(2.92, oClim.getNDepData(3), 0.001);
      assertEquals(5.07, oClim.getNDepData(4), 0.001);
      assertEquals(6.71, oClim.getNDepData(5), 0.001);
      assertEquals(6.34, oClim.getNDepData(6), 0.001);
    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();      
    }
  }
  
  private String writePrecipFile1()  throws IOException {
    String sFileName = "\\loratest.txt";
    FileWriter jOut = new FileWriter(sFileName);
    
    jOut.write("Timestep\tJanuary\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    jOut.write("1\t858\t1346\t570\t1109\t934\t784\t1139\t1328\t1483\t513\t531\t1183\n");
    jOut.write("2\t1221\t798\t1223\t1079\t1342\t754\t1143\t656\t924\t1237\t1300\t758\n");
    jOut.write("3\t1103\t502\t857\t644\t804\t732\t1456\t633\t548\t922\t1425\t1107\n");
    jOut.write("4\t1052\t1347\t725\t1246\t971\t1440\t924\t790\t948\t540\t1361\t1437\n");
    jOut.write("5\t1029\t1027\t1471\t778\t1365\t976\t1287\t636\t951\t565\t993\t928\n");
    jOut.write("6\t1500\t851\t1116\t1487\t1375\t767\t654\t786\t1042\t521\t981\t553\n");
  
    jOut.close();
    return sFileName;
  }
  
  private String writePrecipFile2()  throws IOException {
    String sFileName = "\\loratest.txt";
    FileWriter jOut = new FileWriter(sFileName);
    
    jOut.write("Timestep\tJanuary\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    jOut.write("-3\t2.34\t2.39\t3.06\t4.04\t2.97\t4.93\t3.15\t1.41\t2.05\t3.07\t3.94\t3.15\n");
    jOut.write("-2\t2.53\t2.27\t4.99\t1.22\t4.53\t1.07\t4\t2.41\t1.94\t3.05\t2.3\t4.29\n");
    jOut.write("-1\t3.25\t4.85\t3.22\t2.59\t4.9\t2.84\t1.95\t3.68\t3.65\t1.02\t1.77\t3.76\n");
    jOut.write("1\t4.48\t4.29\t3.55\t2.41\t1.16\t3.01\t3.95\t2.38\t2.5\t3.26\t2.55\t3.19\n");
    jOut.write("2\t3.37\t3.24\t4.9\t1.32\t2.11\t3.56\t1.43\t3.24\t3.33\t3.18\t1.25\t2.26\n");
    jOut.write("3\t1.53\t4.47\t1.4\t3.09\t2.26\t3.39\t1.99\t2.57\t2.57\t4.04\t1.52\t1.22\n");
    jOut.write("4\t4.44\t4.55\t1.47\t2.34\t1.71\t1.3\t2.15\t1.31\t4.07\t2.46\t1.4\t4.7\n");
    jOut.write("5\t1.95\t3.43\t1.16\t3.19\t4.87\t3.04\t4.65\t3.8\t1.99\t1.67\t3.65\t2.57\n");
    jOut.write("6\t4.79\t4.61\t3.02\t1.77\t3.81\t1.57\t1.72\t4.74\t2.37\t1.17\t4.47\t2.87\n");
  
    jOut.close();
    return sFileName;
  }
  
  private String writeTempFile1()  throws IOException {
    String sFileName = "\\loratest.txt";
    FileWriter jOut = new FileWriter(sFileName);
    
    jOut.write("Timestep\tJanuary\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    jOut.write("1\t4.95\t-1.55\t0.67\t-1.34\t11.15\t9.6\t6.27\t20.21\t-3.34\t-2.12\t-9.9\t20.79\n");
    jOut.write("2\t0.72\t-5.03\t7.74\t13.97\t-4.27\t22.7\t7.96\t17.39\t21.2\t-7.14\t11.75\t-2.93\n");
    jOut.write("3\t6.33\t6.01\t-0.58\t21.15\t10.61\t5.71\t10.91\t2.94\t-3.89\t-6.97\t-9.49\t-6.06\n");
    jOut.write("4\t9.2\t4.71\t6.45\t1.3\t3.4\t-6.58\t-8.75\t15.73\t1.05\t22.49\t9.39\t11.18\n");
    jOut.write("5\t15.94\t-1.89\t15.16\t7.67\t-7.03\t4.4\t16.89\t-1.44\t20.06\t17.71\t9.48\t0.47\n");
    jOut.write("6\t9.79\t8.4\t-2.36\t-5.62\t-0.92\t13.81\t21.19\t-5.15\t-6.95\t6.22\t20.23\t9.6\n");
  
    jOut.close();
    return sFileName;
  }
  
  private String writeTempFile2()  throws IOException {
    String sFileName = "\\loratest.txt";
    FileWriter jOut = new FileWriter(sFileName);
    
    jOut.write("Timestep\tJanuary\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    int i, j;
    for (i = -3; i < 7; i++) {
      if (i != 0) {
        jOut.write(String.valueOf(i));
        for (j = 1; j < 13; j++) {
          jOut.write("\t" + String.valueOf(i + (j*0.1)));
        }
        jOut.write("\n");
      }
    }
      
    jOut.close();
    return sFileName;
  }
  
  private String writeNDepFile1() throws IOException {
    String sFileName = "\\loratest.txt";
    FileWriter jOut = new FileWriter(sFileName);
    
    jOut.write("Timestep\tNDep\n");
    jOut.write("1\t4.39\n");
    jOut.write("2\t6.81\n");
    jOut.write("3\t2.92\n");
    jOut.write("4\t5.07\n");
    jOut.write("5\t6.71\n");
    jOut.write("6\t6.34\n");
  
    jOut.close();
    return sFileName;
  }
}
