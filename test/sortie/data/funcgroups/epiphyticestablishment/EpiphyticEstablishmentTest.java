package sortie.data.funcgroups.epiphyticestablishment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EpiphyticEstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class EpiphyticEstablishmentTest extends ModelTestCase {

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
      EpiphyticEstablishmentBehaviors oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      EpiphyticEstablishment oEpi = (EpiphyticEstablishment) p_oBehs.get(0);      

      assertEquals(0.5, oEpi.m_fBeamFractionOfGlobalRadiation.getValue(), 0.0001);
      assertEquals(0.65, oEpi.m_fClearSkyTransmissionCoefficient.getValue(), 0.0001);
      assertEquals(105, oEpi.m_iJulianDayGrowthStarts.getValue());
      assertEquals(258, oEpi.m_iJulianDayGrowthEnds.getValue());
      assertEquals(0.785, oEpi.m_fMinSunAngle.getValue(), 0.0001);
      assertEquals(12, oEpi.m_iNumAltDiv.getValue());
      assertEquals(18, oEpi.m_iNumAziDiv.getValue());
      
      assertEquals(0.1, ((Float) oEpi.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float) oEpi.mp_fLightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) oEpi.mp_fLightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(-3.6482, ((Float) oEpi.mp_fTreeFernRecruitA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-6.1567, ((Float) oEpi.mp_fTreeFernRecruitA.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.5442, ((Float) oEpi.mp_fTreeFernRecruitB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.0008, ((Float) oEpi.mp_fTreeFernRecruitB.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.016595, ((Float) oEpi.mp_fTreeFernRecruitC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.040162, ((Float) oEpi.mp_fTreeFernRecruitC.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(-27.21, ((Float) oEpi.mp_fTreeFernRecruitM.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-16.87, ((Float) oEpi.mp_fTreeFernRecruitM.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(53.76, ((Float) oEpi.mp_fTreeFernRecruitN.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(111.86, ((Float) oEpi.mp_fTreeFernRecruitN.getValue().get(2)).floatValue(), 0.0001);
      
      for (int i = 0; i < oEpi.getNumberOfDataObjects(); i++) {
        ModelData oData = oEpi.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("species of new seedlings") > -1) {
          ModelEnum oEnum = (ModelEnum) oEpi.getDataObject(i);
          assertEquals(1, oEnum.getValue());
          break;
        }
      }      
    }
    catch (ModelException oErr) {
      fail("V6 read validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  /**
   * Makes sure the enums get set up correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    ModelData oData;
    ModelEnum oEnum;
    int i;
    boolean bFound;
    try {

      oManager = new GUIManager(null);

      //Case: basic parameter file read.
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      EpiphyticEstablishmentBehaviors oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      EpiphyticEstablishment oEpi = (EpiphyticEstablishment) p_oBehs.get(0);      

      bFound = false;
      for (i = 0; i < oEpi.getNumberOfDataObjects(); i++) {
        oData = oEpi.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("species of new seedlings") > -1) {
          oEnum = (ModelEnum) oEpi.getDataObject(i);
          assertEquals(1, oEnum.getValue());
          String[] p_sAllowed = oEnum.getAllowedValueLabels();
          assertEquals(3, p_sAllowed.length);
          assertEquals(p_sAllowed[2], "Species 1");
          assertEquals(p_sAllowed[1], "Species 2");
          assertEquals(p_sAllowed[0], "Species 3");
          bFound = true;
          break;
        }
      }
      assertTrue(bFound);

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 4",
          "Species 5",
      "Species 2"}; 
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      oEpi = (EpiphyticEstablishment) p_oBehs.get(0);      

      bFound = false;
      for (i = 0; i < oEpi.getNumberOfDataObjects(); i++) {
        oData = oEpi.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("species of new seedlings") > -1) {
          oEnum = (ModelEnum) oEpi.getDataObject(i);
          assertEquals(3, oEnum.getValue());
          String[] p_sAllowed = oEnum.getAllowedValueLabels();
          assertEquals(4, p_sAllowed.length);
          assertEquals(p_sAllowed[3], "Species 3");
          assertEquals(p_sAllowed[2], "Species 4");
          assertEquals(p_sAllowed[1], "Species 5");
          assertEquals(p_sAllowed[0], "Species 2");          
          bFound = true;
          break;
        }
      }
      assertTrue(bFound);

      sNewSpecies = new String[] {
          "Species 3",
      "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      oEpi = (EpiphyticEstablishment) p_oBehs.get(0);      

      bFound = false;
      for (i = 0; i < oEpi.getNumberOfDataObjects(); i++) {
        oData = oEpi.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("species of new seedlings") > -1) {
          oEnum = (ModelEnum) oEpi.getDataObject(i);
          assertEquals(0, oEnum.getValue());
          String[] p_sAllowed = oEnum.getAllowedValueLabels();
          assertEquals(2, p_sAllowed.length);
          assertEquals(p_sAllowed[1], "Species 3");
          assertEquals(p_sAllowed[0], "Species 4");
          bFound = true;
          break;
        }
      }
      assertTrue(bFound);

      new java.io.File(sFileName).delete();                                  
    }
    catch (ModelException oErr) {
      fail("Epiphytic recruitment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      TreePopulation oPop;
      EpiphyticEstablishmentBehaviors oEpi = null;
      String[] p_sExpected;
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeNeutralFile());
      oEpi = oManager.getEpiphyticEstablishmentBehaviors();
      oPop = oManager.getTreePopulation();
      EpiphyticEstablishment oBeh = (EpiphyticEstablishment) oEpi.createBehaviorFromParameterFileTag("EpiphyticEstablishment");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Tree Fern Establishment Seedling Prob \"a\"",
          "Tree Fern Establishment Seedling Prob \"b\"",
          "Tree Fern Establishment Seedling Prob \"c\"",
          "Tree Fern Establishment Seedling Height \"m\"",
          "Tree Fern Establishment Seedling Height \"n\"",
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for GLI Calculations, in rad",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2",
          "Tree Fern Establishment Species of New Seedlings",
          "Number of Azimuth Sky Divisions for GLI Light Calculations",
          "Number of Altitude Sky Divisions for GLI Light Calculations",
          "First Day of Growing Season for GLI Light Calculations",
          "Last Day of Growing Season for GLI Light Calculations"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for epiphytic recruitment.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests validate data.
   */
  public void testValidateData() {
    //Case:  all valid.
    GUIManager oManager = null;
    String sFileName = null;
    EpiphyticEstablishmentBehaviors oEpiBeh = null;
    EpiphyticEstablishment oEpi = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      oEpi = (EpiphyticEstablishment) p_oBehs.get(0);
      oPop = oManager.getTreePopulation();
      oEpiBeh.validateData(oPop);
    }
    catch (ModelException oErr) {
      fail("Epiphytic recruitment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    //Case:  number of azimuth angles for GLI light is negative.
    oEpi.m_iNumAziDiv.setValue( -10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    oEpi.m_iNumAziDiv.setValue(10);

    //Case:  number of altitude angles for GLI light is negative.
    oEpi.m_iNumAltDiv.setValue( -10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case:  number of altitude angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    oEpi.m_iNumAltDiv.setValue(10);

    //Case:  min sun angle for GLI light is zero.
    oEpi.m_fMinSunAngle.setValue(-10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case:  min sun angle for GLI light is negative.");
    }
    catch (ModelException oErr) {;}
    oEpi.m_fMinSunAngle.setValue((float)0.7);

    //Case: julian days not properly bounded.
    oEpi.m_iJulianDayGrowthStarts.setValue(-10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case: bad Julian start day.");
    }
    catch (ModelException oErr) {;}
    oEpi.m_iJulianDayGrowthStarts.setValue(10);
    oEpi.m_iJulianDayGrowthEnds.setValue(-10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case: bad Julian end day.");
    }
    catch (ModelException oErr) {;}
    oEpi.m_iJulianDayGrowthEnds.setValue(10);

    //Case: clear sky transmission coefficient less than 0.
    oEpi.m_fClearSkyTransmissionCoefficient.setValue(-10);
    try {
      oEpiBeh.validateData(oPop);
      fail("Epiphytic recruitment validation failed - Case: bad clear sky transmission coefficient.");
    }
    catch (ModelException oErr) {;}
    oEpi.m_fClearSkyTransmissionCoefficient.setValue(10);
  }

  /**
   * Makes sure that values are properly read, and that default light settings
   * are correctly chosen.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    ModelData oData;
    ModelEnum oEnum;
    int i;
    try {

      oManager = new GUIManager(null);

      //Case: basic parameter file read.
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      EpiphyticEstablishmentBehaviors oEpiBeh = oManager.getEpiphyticEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEpiBeh.getBehaviorByDisplayName("Epiphytic Establishment");
      assertEquals(1, p_oBehs.size());
      EpiphyticEstablishment oEpi = (EpiphyticEstablishment) p_oBehs.get(0);      
      assertEquals( ( (Float) oEpi.mp_fLightTransmissionCoefficient.getValue().
          get(0)).floatValue(), 0.0, 0.001);
      assertEquals( ( (Float) oEpi.mp_fLightTransmissionCoefficient.getValue().
          get(1)).floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oEpi.mp_fLightTransmissionCoefficient.getValue().
          get(2)).floatValue(), 0.4, 0.001);
      assertEquals(oEpi.m_fBeamFractionOfGlobalRadiation.getValue(),
          (float) 0.5, 0.001);
      assertEquals(oEpi.m_fClearSkyTransmissionCoefficient.getValue(),
          (float) 0.65, 0.001);
      assertEquals(oEpi.m_iJulianDayGrowthStarts.getValue(), 105);
      assertEquals(oEpi.m_iJulianDayGrowthEnds.getValue(), 258);
      assertEquals(oEpi.m_iSnagAgeClass1.getValue(), 10);
      assertEquals(oEpi.m_iSnagAgeClass2.getValue(), 17);
      assertEquals(oEpi.m_fMinSunAngle.getValue(), (float) 0.785, 0.001);
      assertEquals(oEpi.m_iNumAltDiv.getValue(), 12);
      assertEquals(oEpi.m_iNumAziDiv.getValue(), 18);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitA.getValue().
          get(0)).floatValue(), -3.6482, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitA.getValue().
          get(2)).floatValue(), -6.1567, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitB.getValue().
          get(0)).floatValue(), 0.5442, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitB.getValue().
          get(2)).floatValue(), 1.0008, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitC.getValue().
          get(0)).floatValue(), 0.016595, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitC.getValue().
          get(2)).floatValue(), 0.040162, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitM.getValue().
          get(0)).floatValue(), -27.21, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitM.getValue().
          get(2)).floatValue(), -16.87, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitN.getValue().
          get(0)).floatValue(), 53.76, 0.001);
      assertEquals( ( (Float) oEpi.mp_fTreeFernRecruitN.getValue().
          get(2)).floatValue(), 111.86, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass1LightTransmissionCoefficient.getValue().
          get(0)).floatValue(), 0.02, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass1LightTransmissionCoefficient.getValue().
          get(1)).floatValue(), 0.42, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass1LightTransmissionCoefficient.getValue().
          get(2)).floatValue(), 0.52, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass2LightTransmissionCoefficient.getValue().
          get(0)).floatValue(), 0.03, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass2LightTransmissionCoefficient.getValue().
          get(1)).floatValue(), 0.43, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass2LightTransmissionCoefficient.getValue().
          get(2)).floatValue(), 0.53, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass3LightTransmissionCoefficient.getValue().
          get(0)).floatValue(), 0.04, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass3LightTransmissionCoefficient.getValue().
          get(1)).floatValue(), 0.44, 0.001);
      assertEquals( ( (Float) oEpi.mp_fSnagClass3LightTransmissionCoefficient.getValue().
          get(2)).floatValue(), 0.54, 0.001);

      for (i = 0; i < oEpi.getNumberOfDataObjects(); i++) {
        oData = oEpi.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("species of new seedlings") > -1) {
          oEnum = (ModelEnum) oEpi.getDataObject(i);
          assertEquals(1, oEnum.getValue());
          break;
        }
      }

      new java.io.File(sFileName).delete();                                  
    }
    catch (ModelException oErr) {
      fail("Epiphytic recruitment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Writes a file with basic epiphytic recruitment settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
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
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.589</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.589</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.589</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.1299</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.1299</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.1299</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">0.1</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">0.1</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0.1</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.1299</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.1299</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.1299</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0.1</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0.1</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0.1</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.1299</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.1299</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.1299</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0.1</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0.1</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.1</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>EpiphyticEstablishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<EpiphyticEstablishment3>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.2</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_3\">0.4</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<ep_epiphyticA>");
    oOut.write("<ep_eaVal species=\"Species_1\">-3.6482</ep_eaVal>");
    oOut.write("<ep_eaVal species=\"Species_3\">-6.1567</ep_eaVal>");
    oOut.write("</ep_epiphyticA>");
    oOut.write("<ep_epiphyticB>");
    oOut.write("<ep_ebVal species=\"Species_1\">0.5442</ep_ebVal>");
    oOut.write("<ep_ebVal species=\"Species_3\">1.0008</ep_ebVal>");
    oOut.write("</ep_epiphyticB>");
    oOut.write("<ep_epiphyticC>");
    oOut.write("<ep_ecVal species=\"Species_1\">0.016595</ep_ecVal>");
    oOut.write("<ep_ecVal species=\"Species_3\">0.040162</ep_ecVal>");
    oOut.write("</ep_epiphyticC>");
    oOut.write("<ep_epiphyticM>");
    oOut.write("<ep_emVal species=\"Species_1\">-27.21</ep_emVal>");
    oOut.write("<ep_emVal species=\"Species_3\">-16.87</ep_emVal>");
    oOut.write("</ep_epiphyticM>");
    oOut.write("<ep_epiphyticN>");
    oOut.write("<ep_enVal species=\"Species_1\">53.76</ep_enVal>");
    oOut.write("<ep_enVal species=\"Species_3\">111.86</ep_enVal>");
    oOut.write("</ep_epiphyticN>");
    oOut.write("<ep_epiphyticSeedlingSpecies>1</ep_epiphyticSeedlingSpecies>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.02</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_2\">0.42</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_3\">0.52</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.03</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_2\">0.43</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_3\">0.53</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_1\">0.04</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_2\">0.44</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_3\">0.54</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_snagAgeClass1>10</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>17</li_snagAgeClass2>");
    oOut.write("</EpiphyticEstablishment3>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
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
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.589</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.589</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.589</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.1299</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.1299</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.1299</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">0.1</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">0.1</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0.1</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.1299</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.1299</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.1299</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0.1</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0.1</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0.1</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.1299</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.1299</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.1299</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0.1</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0.1</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.1</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Epiphytic Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<epiphyticEstablishment>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.1</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.2</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_3\">0.3</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<ep_epiphyticA>");
    oOut.write("<ep_eaVal species=\"Species_1\">-3.6482</ep_eaVal>");
    oOut.write("<ep_eaVal species=\"Species_3\">-6.1567</ep_eaVal>");
    oOut.write("</ep_epiphyticA>");
    oOut.write("<ep_epiphyticB>");
    oOut.write("<ep_ebVal species=\"Species_1\">0.5442</ep_ebVal>");
    oOut.write("<ep_ebVal species=\"Species_3\">1.0008</ep_ebVal>");
    oOut.write("</ep_epiphyticB>");
    oOut.write("<ep_epiphyticC>");
    oOut.write("<ep_ecVal species=\"Species_1\">0.016595</ep_ecVal>");
    oOut.write("<ep_ecVal species=\"Species_3\">0.040162</ep_ecVal>");
    oOut.write("</ep_epiphyticC>");
    oOut.write("<ep_epiphyticM>");
    oOut.write("<ep_emVal species=\"Species_1\">-27.21</ep_emVal>");
    oOut.write("<ep_emVal species=\"Species_3\">-16.87</ep_emVal>");
    oOut.write("</ep_epiphyticM>");
    oOut.write("<ep_epiphyticN>");
    oOut.write("<ep_enVal species=\"Species_1\">53.76</ep_enVal>");
    oOut.write("<ep_enVal species=\"Species_3\">111.86</ep_enVal>");
    oOut.write("</ep_epiphyticN>");
    oOut.write("<ep_epiphyticSeedlingSpecies>1</ep_epiphyticSeedlingSpecies>");
    oOut.write("</epiphyticEstablishment>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">1</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">1</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">1</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("<mo_randomJuvenileMortality>");
    oOut.write("<mo_rjmVal species=\"Species_1\">0</mo_rjmVal>");
    oOut.write("</mo_randomJuvenileMortality>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
