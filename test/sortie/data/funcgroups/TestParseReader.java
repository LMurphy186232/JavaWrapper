package sortie.data.funcgroups;

import java.io.FileWriter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

import sortie.ModelTestCase;
import sortie.data.funcgroups.analysis.VolumeCalculator;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.sax.ParameterFileParser;
import sortie.sax.SaxParseTools;

import java.util.ArrayList;
import java.io.File;


/**
 * Tests ParseReader class
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class TestParseReader
    extends ModelTestCase {

  /**
   * Tests the XMLTrim method.
   */
  public void testXMLTrim() {
    //Case - null string
    assertEquals(null, SaxParseTools.XMLTrim(null));

    //Case - string of no length
    assertEquals("", SaxParseTools.XMLTrim(""));

    //Case - string with no trim characters
    assertEquals("test", SaxParseTools.XMLTrim("test"));

    //Case - string that is all spaces
//    assertEquals("", SaxParseTools.XMLTrim("    "));

    //Case - string that is all end-of-lines
    assertEquals("", SaxParseTools.XMLTrim("\n\n\n"));

    //Case - string that is all returns
    assertEquals("", SaxParseTools.XMLTrim("\r\r\r"));

    //Case - string that is all tabs
    assertEquals("", SaxParseTools.XMLTrim("\t\t\t"));

    //Case - string that begins with spaces
//    assertEquals("test", SaxParseTools.XMLTrim("    test"));

    //Case - string that begins with end-of-lines
    assertEquals("test", SaxParseTools.XMLTrim("\n\ntest"));

    //Case - string that begins with returns
    assertEquals("test", SaxParseTools.XMLTrim("\rtest"));

    //Case - string that begins with tabs
    assertEquals("test", SaxParseTools.XMLTrim("\t\t\t\t\ttest"));

    //Case - string that ends with spaces
//    assertEquals("test", SaxParseTools.XMLTrim("test "));

    //Case - string that ends with end-of-lines
    assertEquals("test", SaxParseTools.XMLTrim("test\n\n\n"));

    //Case - string that ends with returns
    assertEquals("test", SaxParseTools.XMLTrim("test\r\r"));

    //Case - string that ends with tabs
    assertEquals("test", SaxParseTools.XMLTrim("test\t\t\t"));

    //Case - string that beings and ends with mix
    assertEquals(" test ", SaxParseTools.XMLTrim("\t\r\n test \n\r\t\t"));

    System.out.println("XMLTrim test successful.");

  }

  /**
   * Tests basic parsing and value assignment of an XML file.
   */
  public void testParsing() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeXMLFile1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      
      //Now check the data
      AnalysisBehaviors oAnalysisBeh = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBehs = oAnalysisBeh.getBehaviorByParameterFileTag("TreeVolumeCalculator");
      assertEquals(1, p_oBehs.size());
      VolumeCalculator oVol = (VolumeCalculator) p_oBehs.get(0);
      ArrayList<Object> p_oVector;
      ModelData oData;
      String sXMLTag;
      Float fVal;
      int i;
      boolean bFound;
      
      //Single values
      bFound = false;
      sXMLTag = "vo_stumpHeight";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          assertEquals((float)13.0, ((ModelFloat) oData).getValue());    
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
      
      bFound = false;
      sXMLTag = "vo_minUsableDiam";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          assertEquals((float)3, ((ModelFloat) oData).getValue());    
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
      
      //Test the vector with all values for all species
      bFound = false;
      sXMLTag = "vo_taperA";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          fVal = (Float) p_oVector.get(0);
          assertEquals( (float) 1.18, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(1);
          assertEquals( (float) 1.53, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(2);
          assertEquals( (float) 0.92, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(3);
          assertEquals( (float) 0.98, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(4);
          assertEquals( (float) 1.6, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(5);
          assertEquals( (float) 0.93, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(6);
          assertEquals( (float) 1.14, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(7);
          assertEquals( (float) 2.47, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(8);
          assertEquals( (float) 0.72, fVal.floatValue(), 0.0001);                
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);   

      //Test the vector with no values for any species
      bFound = false;
      sXMLTag = "vo_taperB";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          assertEquals(0, p_oVector.size());    
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
          
      //Test the vector with values for some species
      bFound = false;
      sXMLTag = "vo_taperC";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          fVal = (Float) p_oVector.get(0);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(1);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(2);
          assertEquals( (float) 0.01, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(3);
          assertEquals( (float) 0.015, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(4);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(5);
          assertEquals( (float) 0.0090, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(6);
          assertEquals( (float) 0.034, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(7);
          assertEquals( (float) 0.035, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(8);
          assertEquals(null, fVal);                
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);      

      //Test the vector with values for all species, not in order
      bFound = false;
      sXMLTag = "vo_taperD";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          fVal = (Float) p_oVector.get(0);
          assertEquals( (float) 0.5, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(1);
          assertEquals( (float) 0.3, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(2);
          assertEquals( (float) 0.1, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(3);
          assertEquals( (float) 0.4, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(4);
          assertEquals( (float) 0.2, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(5);
          assertEquals( (float) 0.0050, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(6);
          assertEquals( (float) 0.024, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(7);
          assertEquals( (float) 0.011, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(8);
          assertEquals( (float) 0.016, fVal.floatValue(), 0.0001);                
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
            
      //Test the vector with values for some species, not in order
      bFound = false;
      sXMLTag = "vo_taperF";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          fVal = (Float) p_oVector.get(0);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(1);
          assertEquals( (float) 0.1, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(2);
          assertEquals( (float) 0.016, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(3);
          assertEquals( (float) 0.4, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(4);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(5);
          assertEquals( (float) 0.02, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(6);
          assertEquals( (float) 0.3, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(7);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(8);
          assertEquals(null, fVal);                
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
      

      //Test the vector with value for one species
      bFound = false;
      sXMLTag = "vo_taperG";
      for (i = 0; i < oVol.getNumberOfRequiredDataObjects(); i++) {
        oData = oVol.getDataObject(i);
        if (oData.getXMLTag().equals(sXMLTag)) {
          bFound = true;
          p_oVector = ((ModelVector) oData).getValue();
          fVal = (Float) p_oVector.get(0);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(1);
          assertEquals( (float) 12.87, fVal.floatValue(), 0.0001);
          fVal = (Float) p_oVector.get(2);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(3);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(4);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(5);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(6);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(7);
          assertEquals(null, fVal);
          fVal = (Float) p_oVector.get(8);
          assertEquals(null, fVal);                
        }       
      }
      if (!bFound) fail("Didn't find value for " + sXMLTag);
    }
    catch (ModelException oErr) {
      fail("Parse test failed with message " + oErr.getMessage());
    }
    catch (org.xml.sax.SAXException oE) {
      fail("Parse test could not parse parameter file - message: " +
           oE.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
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

      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>TreeVolumeCalculator</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");

      oOut.write("<TreeVolumeCalculator1>");

      //The softball:  Vector value for every species, in order
      oOut.write("<vo_taperA>");
      oOut.write("<vo_taVal species=\"Western_Hemlock\">1.18</vo_taVal>");
      oOut.write("<vo_taVal species=\"Western_Redcedar\">1.53</vo_taVal>");
      oOut.write("<vo_taVal species=\"Amabilis_Fir\">0.92</vo_taVal>");
      oOut.write("<vo_taVal species=\"Subalpine_Fir\">0.98</vo_taVal>");
      oOut.write("<vo_taVal species=\"Hybrid_Spruce\">1.6</vo_taVal>");
      oOut.write("<vo_taVal species=\"Lodgepole_Pine\">0.93</vo_taVal>");
      oOut.write("<vo_taVal species=\"Trembling_Aspen\">1.14</vo_taVal>");
      oOut.write("<vo_taVal species=\"Black_Cottonwood\">2.47</vo_taVal>");
      oOut.write("<vo_taVal species=\"Paper_Birch\">0.72</vo_taVal>");
      oOut.write("</vo_taperA>");

      //No values for any species
      oOut.write("<vo_taperB>");
      oOut.write("</vo_taperB>");

      //Values for some species
      oOut.write("<vo_taperC>");
      oOut.write("<vo_tcVal species=\"Amabilis_Fir\">0.01</vo_tcVal>");
      oOut.write("<vo_tcVal species=\"Subalpine_Fir\">0.015</vo_tcVal>");
      oOut.write("<vo_tcVal species=\"Lodgepole_Pine\">0.0090</vo_tcVal>");
      oOut.write("<vo_tcVal species=\"Trembling_Aspen\">0.034</vo_tcVal>");
      oOut.write("<vo_tcVal species=\"Black_Cottonwood\">0.035</vo_tcVal>");
      oOut.write("</vo_taperC>");

      //Sprinkle in a single value
      oOut.write("<vo_stumpHeight>13</vo_stumpHeight>");

      //Values for all species, not in order
      oOut.write("<vo_taperD>");
      oOut.write("<vo_tdVal species=\"Amabilis_Fir\">0.1</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Hybrid_Spruce\">0.2</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Western_Redcedar\">0.3</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Trembling_Aspen\">0.024</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Subalpine_Fir\">0.4</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Paper_Birch\">0.016</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Lodgepole_Pine\">0.0050</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Black_Cottonwood\">0.011</vo_tdVal>");
      oOut.write("<vo_tdVal species=\"Western_Hemlock\">0.5</vo_tdVal>");
      oOut.write("</vo_taperD>");

      //Values for some species, not in order
      oOut.write("<vo_taperF>");
      oOut.write("<vo_tfVal species=\"Trembling_Aspen\">0.3</vo_tfVal>");
      oOut.write("<vo_tfVal species=\"Amabilis_Fir\">0.016</vo_tfVal>");
      oOut.write("<vo_tfVal species=\"Western_Redcedar\">0.1</vo_tfVal>");
      oOut.write("<vo_tfVal species=\"Lodgepole_Pine\">0.02</vo_tfVal>");
      oOut.write("<vo_tfVal species=\"Subalpine_Fir\">0.4</vo_tfVal>");
      oOut.write("</vo_taperF>");

      //Sprinkle in a single value
      oOut.write("<vo_minUsableDiam>3</vo_minUsableDiam>");

      //Values for one species
      oOut.write("<vo_taperG>");
      oOut.write("<vo_tgVal species=\"Western_Redcedar\">12.87</vo_tgVal>");
      oOut.write("</vo_taperG>");

      oOut.write("</TreeVolumeCalculator1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

}
