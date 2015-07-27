package sortie.gui;

import junit.framework.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class TestGUIManager extends TestCase {
  
  /**
   * Tests to make sure parameter file list positions are fixed.
   */
  public void testListPositionOrdering() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = WriteXMLFile2();
      
      oManager.inputXMLParameterFile(sFileName);
      
      ArrayList<Behavior> p_oBehs = oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm");
      assertEquals(1, p_oBehs.size());
      assertEquals(1, p_oBehs.get(0).getListPosition());
      
      p_oBehs = oManager.getLightBehaviors().getBehaviorByParameterFileTag("BasalAreaLight");
      assertEquals(2, p_oBehs.size());
      assertEquals(2, p_oBehs.get(0).getListPosition());
      assertEquals(3, p_oBehs.get(1).getListPosition());
      
    } catch (ModelException oErr) {
      fail("GUIManager testing failed with message " +
           oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());

    //}  catch (Exception e) {
    //  fail("Caught Exception.  Message: " + e.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests the ChangeOfPlotResolution method.
   */
  public void testChangeOfPlotResolution() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      Grid oGrid;
      GridValue oValue;
      sFileName = WriteXMLFile1();
      int iCount = 0, i;

      oManager.inputXMLParameterFile(sFileName);

      //*********************************
      // Case one: plot shrinks uniformly
      //*********************************
      //The parameter file contained a map of Basal Area Light - check it now
      oGrid = oManager.getGridByName("Basal Area Light");
      if (oGrid == null) {
        fail ("Couldn't find the \"Basal Area Light\" grid.");
      }
      for (i = 0; i < oGrid.mp_oGridVals.size(); i++) {
        oValue = oGrid.mp_oGridVals.get(i);
        iCount++;
      }
      assertEquals(400, iCount);

      //Now chop the plot to 80 by 100
      oManager.changeOfPlotResolution(200, 200, 80, 100);
      iCount = 0;
      for (i = 0; i < oGrid.mp_oGridVals.size(); i++) {
        oValue = oGrid.mp_oGridVals.get(i);
        assertTrue(oValue.getCell().getX() < 8);
        assertTrue(oValue.getCell().getY() < 10);
        iCount++;
      }
      assertEquals(80, iCount);

      //*********************************
      // Case one: plot grows
      //*********************************
      oManager.inputXMLParameterFile(sFileName);
      oGrid = oManager.getGridByName("Basal Area Light");
      if (oGrid == null) {
        fail ("Couldn't find the \"Basal Area Light\" grid.");
      }
      iCount = 0;
      for (i = 0; i < oGrid.mp_oGridVals.size(); i++) {
        oValue = oGrid.mp_oGridVals.get(i);
        iCount++;
      }
      assertEquals(400, iCount);

      //Now grow the plot to 300 by 300
      try {
        JOptionPane.showMessageDialog(null, "Expect a message about grid maps\n"+
            "no longer covering the plot.");
        oManager.changeOfPlotResolution(200, 200, 300, 300);
      } catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Existing grid maps") == -1) {
          throw (oErr);
        }
      }
      iCount = 0;
      for (i = 0; i < oGrid.mp_oGridVals.size(); i++) {
        oValue = oGrid.mp_oGridVals.get(i);
        iCount++;
      }
      assertEquals(400, iCount);

    } catch (ModelException oErr) {
      fail("GUIManager testing failed with message " +
           oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());

    //}  catch (Exception e) {
    //  fail("Caught Exception.  Message: " + e.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a valid file for testing parameter file reading.
   * @throws IOException if the file can't be written.
   * @return String Filename.
   */
  private String WriteXMLFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.02418</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.1</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">0.9</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">1.3</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.34</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">0.9</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.2871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0163</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">2</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">1</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">1</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Basal Area Light\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Light\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"Con BA\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"Ang BA\">2</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>10</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>10</ma_lengthYCells>");

    float fVal = 1;
    int iNumXCells = 20, iNumYCells = 20, iX, iY;
    for (iX = 0; iX < iNumXCells; iX++) {
      for (iY = 0; iY < iNumYCells; iY++) {
        oOut.write("<ma_v x=\"");
        oOut.write(String.valueOf(iX));
        oOut.write("\" y=\"");
        oOut.write(String.valueOf(iY));
        oOut.write("\">");
        oOut.write("<fl c=\"0\">");
        oOut.write(String.valueOf(fVal));
        oOut.write("</fl>");
        oOut.write("<fl c=\"1\">");
        oOut.write(String.valueOf(fVal));
        oOut.write("</fl>");
        oOut.write("</ma_v>");
      }
    }

    oOut.write("</grid>");
    oOut.write("</paramFile>");

    oOut.close();

    return sFileName;
  }

  /**
   * Writes a file for testing parameter file reading. Positions are wrong.
   * @throws IOException if the file can't be written.
   * @return String Filename.
   */
  private String WriteXMLFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.02418</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.1</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">0.9</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">1.3</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.34</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">0.9</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.2871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0163</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">2</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">1</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">1</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>10</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>11</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("</paramFile>");

    oOut.close();

    return sFileName;
  }

}
