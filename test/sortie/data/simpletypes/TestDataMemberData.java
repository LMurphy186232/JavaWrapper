package sortie.data.simpletypes;

import java.io.FileWriter;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Tree;
import sortie.data.funcgroups.TreePopulation;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

public class TestDataMemberData extends ModelTestCase {

  public void testRemove() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeXMLFile1(), null);
      TreePopulation oPop = oManager.getTreePopulation();
      float fVal;
      int iVal;
      
      int iNumFloats = 1, iNumInts = 1, iNumChars = 1, iNumBools = 1;
      
      Tree oTree = new Tree(3, 0, iNumFloats, iNumInts, iNumChars, iNumBools, oPop);
      fVal = (float)0.6; 
      oTree.setValue(0, fVal);
      iVal = 2;
      oTree.setValue(0, iVal);
      oTree.setValue(0, true);
      oTree.setValue(0, "This");
      
      assertEquals(fVal, oTree.getFloat(0).floatValue());
      assertEquals(iVal, oTree.getInt(0).intValue());
      assertTrue(oTree.getBool(0).booleanValue());
      assertEquals("This", oTree.getChar(0));
      
      iNumFloats = 3;
      iNumInts = 4;
      iNumChars = 5;
      iNumBools = 0;
      oTree = new Tree(3, 0, iNumFloats, iNumInts, iNumChars, iNumBools, oPop);
      fVal = (float)0.1;
      oTree.setValue(0, fVal);
      fVal = (float)0.2;
      oTree.setValue(1, fVal);
      fVal = (float)0.3;
      oTree.setValue(2, fVal);
      
      iVal = 0;
      oTree.setValue(0, iVal);
      iVal = 1;
      oTree.setValue(1, iVal);
      iVal = 2;
      oTree.setValue(2, iVal);
      iVal = 3;
      oTree.setValue(3, iVal);
      
      oTree.setValue(0, "Zero");
      oTree.setValue(1, "One");
      oTree.setValue(2, "Two");
      oTree.setValue(3, "Three");
      oTree.setValue(4, "Four");
      
      fVal = (float)0.1;
      assertEquals(fVal, oTree.getFloat(0).floatValue());
      fVal = (float)0.2;
      assertEquals(fVal, oTree.getFloat(1).floatValue());
      fVal = (float)0.3;
      assertEquals(fVal, oTree.getFloat(2).floatValue());
      
      assertEquals(0, oTree.getInt(0).intValue());
      assertEquals(1, oTree.getInt(1).intValue());
      assertEquals(2, oTree.getInt(2).intValue());
      assertEquals(3, oTree.getInt(3).intValue());
      
      assertEquals("Zero", oTree.getChar(0));
      assertEquals("One", oTree.getChar(1));
      assertEquals("Two", oTree.getChar(2));
      assertEquals("Three", oTree.getChar(3));
      assertEquals("Four", oTree.getChar(4));
      
      //Remove some values
      oTree.removeFloat(1);
      assertEquals(2, oTree.getNumberOfFloats());
      fVal = (float)0.1;
      assertEquals(fVal, oTree.getFloat(0).floatValue());
      fVal = (float)0.3;
      assertEquals(fVal, oTree.getFloat(1).floatValue());
      
      oTree.removeFloat(1);
      assertEquals(1, oTree.getNumberOfFloats());
      fVal = (float)0.1;
      assertEquals(fVal, oTree.getFloat(0).floatValue());
      
      oTree.removeFloat(0);
      assertEquals(0, oTree.getNumberOfFloats());
      
      oTree.removeInt(3);
      assertEquals(3, oTree.getNumberOfInts());
      assertEquals(0, oTree.getInt(0).intValue());
      assertEquals(1, oTree.getInt(1).intValue());
      assertEquals(2, oTree.getInt(2).intValue());
      
      oTree.removeInt(2);
      assertEquals(2, oTree.getNumberOfInts());
      assertEquals(0, oTree.getInt(0).intValue());
      assertEquals(1, oTree.getInt(1).intValue());
      
      oTree.removeInt(1);
      assertEquals(1, oTree.getNumberOfInts());
      assertEquals(0, oTree.getInt(0).intValue());
      
      oTree.removeInt(0);
      assertEquals(0, oTree.getNumberOfInts());
      
      oTree.removeChar(0);
      assertEquals(4, oTree.getNumberOfChars());
      assertEquals("One", oTree.getChar(0));
      assertEquals("Two", oTree.getChar(1));
      assertEquals("Three", oTree.getChar(2));
      assertEquals("Four", oTree.getChar(3));
      
      oTree.removeChar(0);
      assertEquals(3, oTree.getNumberOfChars());
      assertEquals("Two", oTree.getChar(0));
      assertEquals("Three", oTree.getChar(1));
      assertEquals("Four", oTree.getChar(2));
      
      oTree.removeChar(0);
      assertEquals(2, oTree.getNumberOfChars());
      assertEquals("Three", oTree.getChar(0));
      assertEquals("Four", oTree.getChar(1));
      
      oTree.removeChar(0);
      assertEquals(1, oTree.getNumberOfChars());
      assertEquals("Four", oTree.getChar(0));
      
      oTree.removeChar(0);
      assertEquals(0, oTree.getNumberOfChars());      
    }
    
    catch (ModelException oErr) {
      fail("Data member data testing failed with message " +
          oErr.getMessage());
    }    
  }

  /**
   * Writes a parameter file.
   * 
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\testtreemap1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      // Plot - so we can test reading an old tree map
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");

      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("<tr_species speciesName=\"QURU\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ACSA\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"ACRU\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"QURU\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("</trees>");
      oOut.write("<allometry>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ACSA\">39.48</tr_chVal>");
      oOut.write("<tr_chVal species=\"ACRU\">39.54</tr_chVal>");
      oOut.write("<tr_chVal species=\"QURU\">40.0</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ACSA\">0.0549</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"ACRU\">0.0614</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"QURU\">0.0242</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ACSA\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"ACRU\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"QURU\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ACSA\">0.8008</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"ACRU\">0.5944</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"QURU\">0.7059</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ACSA\">0.389</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"ACRU\">0.368</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"QURU\">0.464</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ACSA\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"ACRU\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"QURU\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ACSA\">0.03418</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"ACRU\">0.0269</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"QURU\">0.02871</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ACSA\">0.0299</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"ACRU\">0.0241</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"QURU\">0.0263</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");

      oOut.write("</allometry>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>DensitySelfThinning</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<DensitySelfThinning1>");
      oOut.write("<mo_selfThinRadius>");
      oOut.write("<mo_strVal species=\"ACRU\">9</mo_strVal>");
      oOut.write("</mo_selfThinRadius>");
      oOut.write("<mo_minDensityForMort>");
      oOut.write("<mo_mdfmVal species=\"ACRU\">0</mo_mdfmVal>");
      oOut.write("</mo_minDensityForMort>");
      oOut.write("<mo_selfThinAsymptote>");
      oOut.write("<mo_staVal species=\"ACRU\">0.1019</mo_staVal>");
      oOut.write("</mo_selfThinAsymptote>");
      oOut.write("<mo_selfThinDiamEffect>");
      oOut.write("<mo_stdieVal species=\"ACRU\">0.5391</mo_stdieVal>");
      oOut.write("</mo_selfThinDiamEffect>");
      oOut.write("<mo_selfThinDensityEffect>");
      oOut.write("<mo_stdeeVal species=\"ACRU\">0.00000877</mo_stdeeVal>");
      oOut.write("</mo_selfThinDensityEffect>");
      oOut.write("</DensitySelfThinning1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    } catch (java.io.IOException oE) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
