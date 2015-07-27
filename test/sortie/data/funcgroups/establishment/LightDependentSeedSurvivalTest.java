package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class LightDependentSeedSurvivalTest extends ModelTestCase {
  
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
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      LightDependentSeedSurvival oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      
      assertEquals(23, ((Float) oEst.mp_fOptimumGLI.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(87, ((Float) oEst.mp_fOptimumGLI.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(55.66, ((Float) oEst.mp_fOptimumGLI.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.034, ((Float) oEst.mp_fLowSlope.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0098, ((Float) oEst.mp_fLowSlope.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.011, ((Float) oEst.mp_fLowSlope.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.0023, ((Float) oEst.mp_fHighSlope.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0098, ((Float) oEst.mp_fHighSlope.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.012, ((Float) oEst.mp_fHighSlope.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(oEst.m_fLightHeight.getValue(),  2, 0.001);
      
      assertEquals(0.2, ((Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.21, ((Float) oEst.mp_fLightExtCoeffPartDmg.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.22, ((Float) oEst.mp_fLightExtCoeffPartDmg.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.23, ((Float) oEst.mp_fLightExtCoeffPartDmg.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.24, ((Float) oEst.mp_fLightExtCoeffPartDmg.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float) oEst.mp_fLightExtCoeffPartDmg.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.41, ((Float) oEst.mp_fLightExtCoeffFullDmg.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.42, ((Float) oEst.mp_fLightExtCoeffFullDmg.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.43, ((Float) oEst.mp_fLightExtCoeffFullDmg.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.44, ((Float) oEst.mp_fLightExtCoeffFullDmg.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.45, ((Float) oEst.mp_fLightExtCoeffFullDmg.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.74, ((Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.84, ((Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.86, ((Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.87, ((Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.49, ((Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.51, ((Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.52, ((Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.73, ((Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.84, ((Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.55, ((Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.11, ((Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.13, ((Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.14, ((Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.15, ((Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(oEst.m_fBeamFractionOfGlobalRadiation.getValue(), 0.5, 0.001);
      assertEquals(oEst.m_fClearSkyTransmissionCoefficient.getValue(), 0.65,
                0.001);
      assertEquals(oEst.m_iJulianDayGrowthStarts.getValue(), 105);
      assertEquals(oEst.m_iJulianDayGrowthEnds.getValue(), 258);
      assertEquals(oEst.m_fMinSunAngle.getValue(), 0.785, 0.001);
      assertEquals(oEst.m_iNumAltDiv.getValue(), 12);
      assertEquals(oEst.m_iNumAziDiv.getValue(), 18);

      assertEquals(oEst.m_iSnagAgeClass1.getValue(), 12);
      assertEquals(oEst.m_iSnagAgeClass2.getValue(), 15);

    }
    catch (ModelException oErr) {
      fail("V6 file validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
    
  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.clearCurrentData();
      String sFileName = writeNoDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      EstablishmentBehaviors oEst = oManager.getEstablishmentBehaviors();
      String[] p_sExpected;
      Behavior oBeh = oEst.createBehaviorFromParameterFileTag("LightDependentSeedSurvival");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "GLI of Optimum Establishment, 0-100",
          "Slope of Dropoff Below Optimum GLI",
          "Slope of Dropoff Above the Optimum GLI",
          "Light Extinction Coeff of Undamaged Trees (0-1)",
          "Light Extinction Coeff of Medium Damage Trees (0-1)",
          "Light Extinction Coeff of Complete Damage Trees (0-1)",
          "Snag Age Class 1 Light Extinction Coefficient (0-1)",
          "Snag Age Class 2 Light Extinction Coefficient (0-1)",
          "Snag Age Class 3 Light Extinction Coefficient (0-1)",
          "Height in m At Which to Calculate GLI",
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "Number of Azimuth Sky Divisions for GLI Light Calculations",
          "Number of Altitude Sky Divisions for GLI Light Calculations",
          "First Day of Growing Season for GLI Light Calculations",
          "Minimum Solar Angle for GLI Calculations, in rad",
          "Upper Age (Yrs) of Snag Light Extinction Class 1",
          "Upper Age (Yrs) of Snag Light Extinction Class 2",
          "Last Day of Growing Season for GLI Light Calculations",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for disperse.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    EstablishmentBehaviors oEstBeh = null;
    LightDependentSeedSurvival oEst = null;
    
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //Exception processing there is an undamaged tree light extinction
      //coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fLightExtCoeffNoDmg.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in light error file 1.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing there is a partial damaged tree light extinction
      //coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fLightExtCoeffPartDmg.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in light error file 2.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing there is a full damaged tree light extinction
      //coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fLightExtCoeffFullDmg.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in light error file 3.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for light height is less than 0.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_fLightHeight.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in light error file 4.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where there are values for optimum GLI not 
      //between 0 and 100.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fOptimumGLI.getValue().set(0, new Float(100.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in light error file 5.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for
      // m_fBeamFractionOfGlobalRadiation is not a valid proportion.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_fBeamFractionOfGlobalRadiation.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 6.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for
      //m_iJulianDayGrowthStarts is not between 1 and 365 (inclusive).
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iJulianDayGrowthStarts.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 7.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for
      //m_iJulianDayGrowthEnds is not between 1 and 365 (inclusive).
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iJulianDayGrowthEnds.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 8.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for 
      //m_iNumAltDiv is not greater than 0.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iNumAltDiv.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 9.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the value for 
      //m_iNumAziDiv is not greater than 0.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iNumAziDiv.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 10.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where there is a snag size
      //class 1 light extinction coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().remove(0);
      oEst.mp_fSnagClass1LightExtinctionCoefficient.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 11.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where there is a snag size
      //class 2 light extinction coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fSnagClass2LightExtinctionCoefficient.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 12.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where there is a snag size
      //class 3 light extinction coefficient not between 0 and 1.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.mp_fSnagClass3LightExtinctionCoefficient.getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 13.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the first snag age class isn't > zero.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iSnagAgeClass1.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 14.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the 2nd snag age class isn't > zero.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iSnagAgeClass2.setValue(-2);
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 15.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing where the first snag age
      //class is greater than the second class.
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      oEst.m_iSnagAgeClass2.setValue(2);
      oEst.m_iSnagAgeClass1.setValue(12);
      try {
        oManager.inputXMLParameterFile(sFileName);
        oEst.validateData(oManager.getTreePopulation());
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in Light error file 16.");
      }
      catch (ModelException oErr) {
        ;
      }

      System.out.println("Establishment ValidateData testing succeeded.");
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that species copying happens correctly.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_2", "Species_1");
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      LightDependentSeedSurvival oEst = (LightDependentSeedSurvival) p_oBehs.get(0);      
      
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(0)).
                   floatValue(), 23, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(0)).
                   floatValue(), 0.0023, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(0)).
                   floatValue(), 0.034, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().
                     get(0)).floatValue(), 0.21, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().
                     get(1)).floatValue(), 0.21, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(0)).floatValue(), 0.23, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(1)).floatValue(), 0.23, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(0)).floatValue(), 0.8, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(1)).floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 0.7, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.7, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.5, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 0.1, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.1, 0.001);

      System.out.println("Establishment species copy testing succeeded.");
    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that species changes happen correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      LightDependentSeedSurvival oEst = (LightDependentSeedSurvival) p_oBehs.get(0);

      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(2)).
                   floatValue(), 87, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(2)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(2)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(
          2)).floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().get(
          1)).floatValue(), 0.21, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(2)).floatValue(), 0.22, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(1)).floatValue(), 0.23, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(2)).floatValue(), 0.9, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(1)).floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().get(2)).floatValue(), 0.6, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().get(1)).floatValue(), 0.7, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().get(2)).floatValue(), 0.4, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().get(1)).floatValue(), 0.5, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().get(2)).floatValue(), 1, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().get(1)).floatValue(), 0.1, 0.001);

      System.out.println("Establishment change of species testing succeeded.");
    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("LightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      LightDependentSeedSurvival oEst = (LightDependentSeedSurvival) p_oBehs.get(0);
      
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(0)).
                   floatValue(), 87, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(0)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(0)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().
                     get(0)).floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffNoDmg.getValue().
                     get(1)).floatValue(), 0.21, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(0)).floatValue(), 0.22, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffPartDmg.getValue().
                     get(1)).floatValue(), 0.23, 0.001);

      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(0)).floatValue(), 0.9, 0.001);
      assertEquals( ( (Float) oEst.mp_fLightExtCoeffFullDmg.getValue().
                     get(1)).floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 0.6, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass1LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.7, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 0.4, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass2LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.5, 0.001);

      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().
                     get(0)).floatValue(), 1, 0.001);
      assertEquals( ( (Float) oEst.mp_fSnagClass3LightExtinctionCoefficient.
                     getValue().
                     get(1)).floatValue(), 0.1, 0.001);

      assertEquals(oEst.m_fLightHeight.getValue(),  2, 0.001);
      assertEquals(oEst.m_fBeamFractionOfGlobalRadiation.getValue(), 0.5, 0.001);
      assertEquals(oEst.m_fClearSkyTransmissionCoefficient.getValue(), 0.65,
                   0.001);
      assertEquals(oEst.m_iJulianDayGrowthStarts.getValue(), 105);
      assertEquals(oEst.m_iJulianDayGrowthEnds.getValue(), 258);
      assertEquals(oEst.m_fMinSunAngle.getValue(), 0.785, 0.001);
      assertEquals(oEst.m_iNumAltDiv.getValue(), 12);
      assertEquals(oEst.m_iNumAziDiv.getValue(), 18);

      assertEquals(oEst.m_iSnagAgeClass1.getValue(), 10);
      assertEquals(oEst.m_iSnagAgeClass2.getValue(), 17);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Writes a valid establishment parameter file.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String writeValidFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeValidateFilePart1(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LightDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<LightDependentSeedSurvival1>");
    oOut.write("<es_optimumGLI>");
    oOut.write("<es_ogVal species=\"Species_2\">23</es_ogVal>");
    oOut.write("<es_ogVal species=\"Species_1\">87</es_ogVal>");
    oOut.write("</es_optimumGLI>");
    oOut.write("<es_highSlope>");
    oOut.write("<es_hsVal species=\"Species_2\">0.0023</es_hsVal>");
    oOut.write("<es_hsVal species=\"Species_1\">0.0098</es_hsVal>");
    oOut.write("</es_highSlope>");
    oOut.write("<es_lowSlope>");
    oOut.write("<es_lsVal species=\"Species_2\">0.034</es_lsVal>");
    oOut.write("<es_lsVal species=\"Species_1\">0.0098</es_lsVal>");
    oOut.write("</es_lowSlope>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.2</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.21</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<es_lightExtCoeffPartDmg>");
    oOut.write("<es_lecpdVal species=\"Species_1\">0.22</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_2\">0.23</es_lecpdVal>");
    oOut.write("</es_lightExtCoeffPartDmg>");
    oOut.write("<es_lightExtCoeffFullDmg>");
    oOut.write("<es_lecfdVal species=\"Species_2\">0.8</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_1\">0.9</es_lecfdVal>");
    oOut.write("</es_lightExtCoeffFullDmg>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_2\">0.7</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.6</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_2\">0.5</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.4</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_2\">0.1</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_1\">1</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("<es_lightHeight>2</es_lightHeight>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_snagAgeClass1>10</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>17</li_snagAgeClass2>");
    oOut.write("</LightDependentSeedSurvival1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes the common beginning part of parameter files.
   * @param oOut FileWriter File stream to write to.
   * @throws IOException If the file cannot be written.
   */
  private void writeValidateFilePart1(FileWriter oOut) throws java.io.
      IOException {
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
  }

  /**
   * Writes a file with no disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoDisperseXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
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
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
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
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>500.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
   oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.8</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.8</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.8</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.8</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.8</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>storm damage applier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Light Dependent Seed Survival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<storm>");
    oOut.write("<st_s1ReturnInterval>1000</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>1000</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>1000</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>1000</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>1000</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>1000</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>1000</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>1000</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>1000</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1000</st_s10ReturnInterval>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_stmSSTPeriod>1</st_stmSSTPeriod>");
    oOut.write("<st_stmSineD>0</st_stmSineD>");
    oOut.write("<st_stmSineF>1</st_stmSineF>");
    oOut.write("<st_stmSineG>1</st_stmSineG>");
    oOut.write("<st_stmTrendSlopeM>0</st_stmTrendSlopeM>");
    oOut.write("<st_stmTrendInterceptI>1</st_stmTrendInterceptI>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_3\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_4\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.475987</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_3\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_4\">2.475987</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">3.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">3.6331242</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_3\">3.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_4\">3.6331242</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.3545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.2289105</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_3\">-0.3545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_4\">-1.2289105</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.3282479</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_3\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_4\">0.3282479</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.6</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">2.34</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_3\">2.34</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_4\">2.34</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">-0.01</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">-0.02</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_3\">-0.01</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_4\">-0.02</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">3.82</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">1.39</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_3\">3.82</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_4\">1.39</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.079</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.05</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_3\">-0.079</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_4\">-0.05</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_3\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_4\">0</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>0</st_numYearsStormSnags>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</storm>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_4\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_4\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_4\">0</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">0</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">0</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<establishment>");
    oOut.write("<es_optimumGLI>");
    oOut.write("<es_ogVal species=\"Species_2\">23</es_ogVal>");
    oOut.write("<es_ogVal species=\"Species_3\">87</es_ogVal>");
    oOut.write("<es_ogVal species=\"Species_4\">55.66</es_ogVal>");
    oOut.write("</es_optimumGLI>");
    oOut.write("<es_lowSlope>");
    oOut.write("<es_lsVal species=\"Species_2\">0.034</es_lsVal>");
    oOut.write("<es_lsVal species=\"Species_3\">0.0098</es_lsVal>");
    oOut.write("<es_lsVal species=\"Species_4\">0.011</es_lsVal>");
    oOut.write("</es_lowSlope>");
    oOut.write("<es_highSlope>");
    oOut.write("<es_hsVal species=\"Species_2\">0.0023</es_hsVal>");
    oOut.write("<es_hsVal species=\"Species_3\">0.0098</es_hsVal>");
    oOut.write("<es_hsVal species=\"Species_4\">0.012</es_hsVal>");
    oOut.write("</es_highSlope>");
    oOut.write("<es_lightHeight>2</es_lightHeight>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.2</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.3</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_3\">0.4</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_4\">0.5</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_5\">0.6</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<es_lightExtCoeffPartDmg>");
    oOut.write("<es_lecpdVal species=\"Species_1\">0.21</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_2\">0.22</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_3\">0.23</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_4\">0.24</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_5\">0.25</es_lecpdVal>");
    oOut.write("</es_lightExtCoeffPartDmg>");
    oOut.write("<es_lightExtCoeffFullDmg>");
    oOut.write("<es_lecfdVal species=\"Species_1\">0.41</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_2\">0.42</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_3\">0.43</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_4\">0.44</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_5\">0.45</es_lecfdVal>");
    oOut.write("</es_lightExtCoeffFullDmg>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.74</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_2\">0.84</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_3\">0.86</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_4\">0.87</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_5\">0.49</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.51</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_2\">0.52</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_3\">0.73</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_4\">0.84</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_5\">0.55</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_1\">0.11</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_2\">0.12</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_3\">0.13</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_4\">0.14</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_5\">0.15</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_snagAgeClass1>12</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>15</li_snagAgeClass2>");
    oOut.write("</establishment>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}