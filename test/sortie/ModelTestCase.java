package sortie;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.TableData;

/**
 * Provides common methods for testing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class ModelTestCase extends TestCase {
  
  public void TestTable(ArrayList<BehaviorParameterDisplay> p_oLotsaData, String[] p_sExpected) {
    //Add all the data to the first list
    BehaviorParameterDisplay oBehData = p_oLotsaData.get(0);
    for (int i = 1; i < p_oLotsaData.size(); i++) {
      for (int j = 0; j < p_oLotsaData.get(i).mp_oTableData.size(); j++) {
        oBehData.mp_oTableData.add(p_oLotsaData.get(i).mp_oTableData.get(j));
      }
    }
    TestTable(oBehData, p_sExpected);
  }

  /**
   * Tests a table set against a list of expected members in the table.  It
   * does not care how many tables are defined in the vector.  It will
   * throw a fail if there are members missing or members that are not
   * expected.  Species are not checked.  Blanks are skipped.
   * @param p_oTablesToTest Vector of tables to test.
   * @param p_sExpected Expected member list.
   */
  public void TestTable(BehaviorParameterDisplay oBehData, String[] p_sExpected) {
    TableData p_oOneTable;
    String sMemberName;
    int[] p_iFoundTimes = new int[p_sExpected.length]; //holds the number of
    //times each member was found
    int iNumberOfTables = oBehData.mp_oTableData.size(),
        i, j, k;
    boolean bFound;

    //Initialize the number of times found array to all 0s
    for (i = 0; i < p_iFoundTimes.length; i++) {
      p_iFoundTimes[i] = 0;
    }

    //Analyze each table
    for (i = 0; i < iNumberOfTables; i++) {
      p_oOneTable = oBehData.mp_oTableData.get(i);

      //Go through each table row
      for (j = 0; j < p_oOneTable.mp_oTableData.length; j++) {

        sMemberName = (String) p_oOneTable.mp_oTableData[j][0];
        bFound = false;

        if (sMemberName.trim().length() > 0) {

          //Does it match a member of the expected list?
          for (k = 0; k < p_sExpected.length; k++) {

            if (sMemberName.equals(p_sExpected[k])) {

              bFound = true;
              p_iFoundTimes[k]++;

            }
          }
        } else bFound = true;

        //Did we find our member in the expected list?
        if (!bFound) {
          fail(sMemberName + " unexpectedly found in table.");
        }
      }
    }

    //Now look at the number of times we found each expected member - if
    //not 1 for each, fail
    for (i = 0; i < p_iFoundTimes.length; i++) {
      assertEquals("Found " + p_sExpected[i] + " unexpected number of times.",
                   1, p_iFoundTimes[i]);
    }
  }
  
  /**
   * Writes a file with only one light behavior.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeNeutralFile() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
