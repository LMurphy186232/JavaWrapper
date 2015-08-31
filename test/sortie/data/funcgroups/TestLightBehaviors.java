package sortie.data.funcgroups;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;


/**
 * Tests the LightBehaviors class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestLightBehaviors
extends ModelTestCase {
  
  
  /**
   * Tests that the correct parameters are displayed for each behavior. In this
   * case, the LightBehaviors object formats the data for display in order to 
   * capture (or not) general light parameters.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      LightBehaviors oLight = null;
      String[] p_sExpected;
      Behavior oBeh;

      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      TreePopulation oPop = oManager.getTreePopulation();
      oLight = oManager.getLightBehaviors();

      //Case:  only sail light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("SailLight");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "Calculated Crown Depth",
          "Sail Light Minimum Solar Angle, in degrees",
          "Sail Light Maximum Shading Neighbor Distance, in meters",
          "Height of Fisheye Photo",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only constant GLI light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("ConstantGLI");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Constant GLI - Constant GLI Value (0-100)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);


      //Case:  only gli light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("GLILight");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "Height of Fisheye Photo",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for GLI Light, in rad",
          "Number of Azimuth Sky Divisions for GLI Light Calculations",
          "Number of Altitude Sky Divisions for GLI Light Calculations",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only quadrat gli light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("QuadratLight");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for Quadrat Light, in rad",
          "Height at Which GLI is Calculated for Quadrats, in meters",
          "Number of Azimuth Sky Divisions for Quadrat Light Calculations",
          "Number of Altitude Sky Divisions for Quadrat Light Calculations",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Quadrat GLI - Always Calculate All GLIs",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only gli map is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("GLIMapCreator");
      p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for GLI Map Creator, in rad",
          "Height at Which GLI is Calculated for GLI Map, in meters",
          "Number of Azimuth Sky Divisions for GLI Map Creator Calculations",
          "Number of Altitude Sky Divisions for GLI Map Creator Calculations",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only gli points is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("GLIPointCreator");
      p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for GLI Points Creator, in rad",
          "GLI Points Input File",
          "GLI Points Output File",
          "Azimuth of North, in rad",
          "Number of Azimuth Sky Divisions for GLI Points Creator",
          "Number of Altitude Sky Divisions for GLI Points Creator",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only storm light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("StormLight");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Storm Light - Max Radius (m) for Damaged Neighbors",
          "Storm Light - Slope of Light Function",
          "Storm Light - Intercept of Light Function",
          "Storm Light - Standard Deviation",
          "Storm Light - Stochasticity",
          "Storm Light - Max Years Snags Affect Light",
          "Storm Light - Minimum Trees For Full Canopy",
          "Storm Light - Max Years Damaged Trees Affect Light"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only density light is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("BasalAreaLight");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Basal Area Light - Species Type",
          "Basal Area Light - Mean GLI \"a\" Parameter",
          "Basal Area Light - Conifer \"b\" Parameter",
          "Basal Area Light - Conifer \"c\" Parameter",
          "Basal Area Light - Angiosperm \"b\" Parameter",
          "Basal Area Light - Angiosperm \"c\" Parameter",
          "Basal Area Light - Lognormal PDF Sigma",
          "Basal Area Light - Minimum DBH for Trees",
          "Basal Area Light - Minimum BA Change for New GLI (m2)",
          "Basal Area Light - Search Radius for Neighbors (m)",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);
      
      //Case: only light filter is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNoLightXMLFile1());
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.createBehaviorFromParameterFileTag("LightFilter");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Light Filter Light Transmission Coefficient",
          "Height of Light Filter, in m"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for light.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Writes a file with no light settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoLightXMLFile1() throws java.io.IOException {
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DensitySelfThinning</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<DensitySelfThinning1>");
    oOut.write("<mo_selfThinRadius>");
    oOut.write("<mo_strVal species=\"Species_1\">9</mo_strVal>");
    oOut.write("</mo_selfThinRadius>");
    oOut.write("<mo_minDensityForMort>");
    oOut.write("<mo_mdfmVal species=\"Species_1\">0</mo_mdfmVal>");
    oOut.write("</mo_minDensityForMort>");
    oOut.write("<mo_selfThinAsymptote>");
    oOut.write("<mo_staVal species=\"Species_1\">0.1019</mo_staVal>");
    oOut.write("</mo_selfThinAsymptote>");
    oOut.write("<mo_selfThinDiamEffect>");
    oOut.write("<mo_stdieVal species=\"Species_1\">0.5391</mo_stdieVal>");
    oOut.write("</mo_selfThinDiamEffect>");
    oOut.write("<mo_selfThinDensityEffect>");
    oOut.write("<mo_stdeeVal species=\"Species_1\">0.00000877</mo_stdeeVal>");
    oOut.write("</mo_selfThinDensityEffect>");
    oOut.write("</DensitySelfThinning1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}