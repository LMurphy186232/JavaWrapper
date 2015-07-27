package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disperse.SpatialDisperseBase;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


public class GapSubstrateSeedSurvivalTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      GapSubstrateSeedSurvival oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      
      int C = SpatialDisperseBase.CANOPY,
          G = SpatialDisperseBase.GAP;
            
      assertEquals(0.01, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.017, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.96, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.92, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.01, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0, ((Float) oEst.mp_fScarifiedSoilFavorability[C].getValue().get(6)).floatValue(), 0.001);

      assertEquals(0.315, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.48, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.95, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.48, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.315, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.48, ((Float) oEst.mp_fTipUpFavorability[C].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.462, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.98, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.278, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.462, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(1, ((Float) oEst.mp_fFreshLogsFavorability[C].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.155, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.278, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.99, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.155, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0, ((Float) oEst.mp_fDecayedLogsFavorability[C].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(1, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.391, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.92, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.391, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.391, ((Float) oEst.mp_fForestFloorLitterFavorability[C].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.9, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.388, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.567, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.995, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.871, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.445, ((Float) oEst.mp_fForestFloorMossFavorability[C].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.983, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.424, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.983, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.424, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.983, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.424, ((Float) oEst.mp_fScarifiedSoilFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.009, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.413, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.009, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.413, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.009, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.413, ((Float) oEst.mp_fTipUpFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.353, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.024, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.353, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.024, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.353, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0, ((Float) oEst.mp_fFreshLogsFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.058, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.31, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(1, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.058, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.31, ((Float) oEst.mp_fDecayedLogsFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.88, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.771, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.771, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.771, ((Float) oEst.mp_fForestFloorLitterFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
      assertEquals(0.02, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.942, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.663, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.716, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.629, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.951, ((Float) oEst.mp_fForestFloorMossFavorability[G].getValue().get(6)).floatValue(), 0.001);
      
    }
    catch (ModelException oErr) {
      fail("V6 file validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
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
      EstablishmentBehaviors oEstablishment = null;
      TreePopulation oPop = null;
      String[] p_sExpected;
      Behavior oBeh;

      //Case:  only gap substrate-dependent seed survival is enabled
      oManager.inputXMLParameterFile(writeValidFile1());
      oEstablishment = oManager.getEstablishmentBehaviors();
      oPop = oManager.getTreePopulation();
      
      ArrayList<Behavior> p_oBehs = oEstablishment.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oBeh = p_oBehs.get(0);
      p_sExpected = new String[] {
          "Fraction Seeds Germinating on Canopy Fresh Logs",
          "Fraction Seeds Germinating on Canopy Decayed Logs",
          "Fraction Seeds Germinating on Canopy Scarified Soil",
          "Fraction Seeds Germinating on Canopy Forest Floor Litter",
          "Fraction Seeds Germinating on Canopy Forest Floor Moss",
          "Fraction Seeds Germinating on Canopy Tip-Up",
          "Fraction Seeds Germinating on Gap Fresh Logs",
          "Fraction Seeds Germinating on Gap Decayed Logs",
          "Fraction Seeds Germinating on Gap Scarified Soil",
          "Fraction Seeds Germinating on Gap Forest Floor Litter",
          "Fraction Seeds Germinating on Gap Forest Floor Moss",
          "Fraction Seeds Germinating on Gap Tip-Up"
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
    GapSubstrateSeedSurvival oEst = null;
    try {
      sFileName = writeValidFile1();
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();        
        oManager.inputXMLParameterFile(sFileName);
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());        
      }
      catch (ModelException oErr) {
        fail("Establishment validation failed with message " + oErr.getMessage());
      }           
      
      //Exception processing - canopy decayed log favorability isn't a 
      //proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fDecayedLogsFavorability[SpatialDisperseBase.CANOPY].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 1.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - canopy forest floor litter favorability isn't 
      //a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fForestFloorLitterFavorability[SpatialDisperseBase.CANOPY].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 2.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Canopy fresh log favorability isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.CANOPY].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 3.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Canopy scarified soil favorability isn't a 
      //proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fScarifiedSoilFavorability[SpatialDisperseBase.CANOPY].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 4.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Canopy tip up favorability isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fTipUpFavorability[SpatialDisperseBase.CANOPY].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 5.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap decayed log favorability isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fDecayedLogsFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 6.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap forest floor litter favorability 
      //isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fForestFloorLitterFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 7.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap fresh log favorability isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 8.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap scarified soil favorability isn't 
      //a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fScarifiedSoilFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 9.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap tip-up favorability isn't a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 10.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Canopy forest floor moss favorability isn't 
      //a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fForestFloorMossFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 11.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - Gap forest floor moss favorability isn't 
      //a proportion.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      oEst.mp_fForestFloorMossFavorability[SpatialDisperseBase.GAP].getValue().set(0, new Float(1.2));
      try {
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
        fail("Didn't catch error in substrate error file 12.");
      }
      catch (ModelException oErr) {
        ;
      }
      System.out.println("Establishment ValidateData testing succeeded.");
    }
    catch (IOException oE) {
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
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_2", "Species_1");
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      GapSubstrateSeedSurvival oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
            
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.024, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.024, 0.001);

      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.048, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.048, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.01, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.885, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.885, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.0010, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.0010, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.911, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.911, 0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(0)).floatValue(), 0.424,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.424,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(0)).floatValue(), 0.319, 0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(1)).floatValue(), 0.319, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.48, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.48, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.213, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.213, 0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(0)).floatValue(), 0.391,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.391,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(0)).floatValue(), 0.179, 0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(1)).floatValue(), 0.179, 0.001);

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
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      GapSubstrateSeedSurvival oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
   
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(2)).
                   floatValue(), 0.058, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.024, 0.001);

      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(2)).floatValue(),
                   0.7, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.048, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(2)).
                   floatValue(), 0.0090, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(2)).
                   floatValue(), 0.162, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.885, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(2)).floatValue(),
                   0.894, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.0010, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(2)).floatValue(),
                   0.568, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.911, 0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(2)).floatValue(), 0.353,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.424,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(2)).floatValue(), 0.967, 0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(1)).floatValue(), 0.319, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(2)).
                   floatValue(), 0.129, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.48, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(2)).floatValue(),
                   0.925, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.213, 0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(2)).floatValue(), 0.983,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.391,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(2)).floatValue(), 0.913, 0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(1)).floatValue(), 0.179, 0.001);

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
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("GapSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      GapSubstrateSeedSurvival oEst = (GapSubstrateSeedSurvival) p_oBehs.get(0);
      
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.058, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.024, 0.001);

      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.7, 0.001);
      assertEquals( ( (Float) oEst.mp_fDecayedLogsFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.048, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.0090, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.162, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.885, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.894, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorLitterFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.0010, 0.001);

      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.568, 0.001);
      assertEquals( ( (Float) oEst.mp_fForestFloorMossFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.911, 0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(0)).floatValue(), 0.353,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.424,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(0)).floatValue(), 0.967, 0.001);
      assertEquals( ( (Float) oEst.mp_fFreshLogsFavorability[SpatialDisperseBase.
                     GAP].getValue().get(1)).floatValue(), 0.319, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(0)).
                   floatValue(), 0.129, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.CANOPY].getValue().get(1)).
                   floatValue(), 0.48, 0.001);

      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(0)).floatValue(),
                   0.925, 0.001);
      assertEquals( ( (Float) oEst.mp_fScarifiedSoilFavorability[
                     SpatialDisperseBase.GAP].getValue().get(1)).floatValue(),
                   0.213, 0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(0)).floatValue(), 0.983,
                   0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.
                     CANOPY].getValue().get(1)).floatValue(), 0.391,
                   0.001);

      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(0)).floatValue(), 0.913, 0.001);
      assertEquals( ( (Float) oEst.mp_fTipUpFavorability[SpatialDisperseBase.GAP].
                     getValue().get(1)).floatValue(), 0.179, 0.001);

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
  private String writeValidFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeValidateFilePart1(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GapSubstrateSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<SubstrateDependentSeedSurvival1>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_1\">0.058</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.024</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_1\">0.0090</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_2\">0.01</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_1\">0.162</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.885</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_1\">0.353</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.424</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_1\">0.129</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.48</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_1\">0.983</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.391</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("<es_decayedLogGapFav>");
    oOut.write("<es_dlgfVal species=\"Species_1\">0.7</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_2\">0.048</es_dlgfVal>");
    oOut.write("</es_decayedLogGapFav>");
    oOut.write("<es_forestFloorLitterGapFav>");
    oOut.write("<es_fflgfVal species=\"Species_1\">0.894</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_2\">0.0010</es_fflgfVal>");
    oOut.write("</es_forestFloorLitterGapFav>");
    oOut.write("<es_forestFloorMossGapFav>");
    oOut.write("<es_ffmgfVal species=\"Species_1\">0.568</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_2\">0.911</es_ffmgfVal>");
    oOut.write("</es_forestFloorMossGapFav>");
    oOut.write("<es_freshLogGapFav>");
    oOut.write("<es_flgfVal species=\"Species_1\">0.967</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_2\">0.319</es_flgfVal>");
    oOut.write("</es_freshLogGapFav>");
    oOut.write("<es_scarifiedSoilGapFav>");
    oOut.write("<es_ssgfVal species=\"Species_1\">0.925</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_2\">0.213</es_ssgfVal>");
    oOut.write("</es_scarifiedSoilGapFav>");
    oOut.write("<es_tipUpGapFav>");
    oOut.write("<es_tugfVal species=\"Species_1\">0.913</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_2\">0.179</es_tugfVal>");
    oOut.write("</es_tipUpGapFav>");
    oOut.write("</SubstrateDependentSeedSurvival1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes the common beginning part of parameter files.
   * @param oOut FileWriter File stream to write to.
   * @throws IOException If the file cannot be written.
   */
  public static void writeValidateFilePart1(FileWriter oOut) throws java.io.
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
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("<tr_species speciesName=\"Species_6\" />");
    oOut.write("<tr_species speciesName=\"Species_7\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_7\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_7\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_7\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_7\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_7\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_7\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_7\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_7\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_7\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_6\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_7\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_7\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_7\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_7\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_7\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_7\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_7\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_7\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_7\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>substrate</behaviorName>");
    oOut.write("<version>2.1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>gap disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Gap Substrate Seed Survival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_6\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_7\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_5\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_4\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<substrate>");
    oOut.write("<su_scarSoilDecayAlpha>-5.1E-4</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-7.0E-4</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
    oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
    oOut.write("<su_freshLogDecayBeta>6.5</su_freshLogDecayBeta>");
    oOut.write("<su_decayedLogDecayAlpha>-0.7985</su_decayedLogDecayAlpha>");
    oOut.write("<su_decayedLogDecayBeta>1.1</su_decayedLogDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>10</su_maxNumberDecayYears>");
    oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.0</su_initialTipup>");
    oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
    oOut.write("<su_initialDecayedLog>0.11</su_initialDecayedLog>");
    oOut.write("<su_propOfDeadFall>");
    oOut.write("<su_podfVal species=\"Species_2\">0</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_3\">0</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_4\">0</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_5\">0</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_6\">0</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_7\">0</su_podfVal>");
    oOut.write("</su_propOfDeadFall>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_2\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_3\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_4\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_5\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_6\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_7\">0</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_2\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_3\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_4\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_5\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_6\">0</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_7\">0</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3.0</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.64</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>1</su_directionalTreeFall>");
    oOut.write("</substrate>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_5\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_6\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_7\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_7\">0</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_6\">0</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_5\">0</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_4\">0</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_3\">0</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">0</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_7\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_6\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_5\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_4\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_3\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">2.0</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_7\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_6\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_5\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_4\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_3\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.76E-4</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_7\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_6\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_5\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_4\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_3\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.0</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_weibullGapSTR>");
    oOut.write("<di_wgsVal species=\"Species_7\">0</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_6\">0</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_5\">0</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_4\">0</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_3\">0</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_2\">0</di_wgsVal>");
    oOut.write("</di_weibullGapSTR>");
    oOut.write("<di_weibullGapBeta>");
    oOut.write("<di_wgbVal species=\"Species_2\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_3\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_4\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_5\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_6\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_7\">2.3</di_wgbVal>");
    oOut.write("</di_weibullGapBeta>");
    oOut.write("<di_weibullGapDispersal>");
    oOut.write("<di_wgdVal species=\"Species_2\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_3\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_4\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_5\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_6\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_7\">1.32E-4</di_wgdVal>");
    oOut.write("</di_weibullGapDispersal>");
    oOut.write("<di_weibullGapTheta>");
    oOut.write("<di_wgtVal species=\"Species_2\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_3\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_4\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_5\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_6\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_7\">3.0</di_wgtVal>");
    oOut.write("</di_weibullGapTheta>");
    oOut.write("<di_maxGapDensity>0</di_maxGapDensity>");
    oOut.write("<di_gapFunction>");
    oOut.write("<di_gfVal species=\"Species_7\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_6\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_5\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_4\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_3\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_2\">0</di_gfVal>");
    oOut.write("</di_gapFunction>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_7\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_6\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_5\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_4\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<establishment>");
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.01</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_3\">0.017</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_4\">0.96</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_5\">0.92</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_6\">0.01</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_7\">0</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.315</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_3\">0.48</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_4\">0.95</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_5\">0.48</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_6\">0.315</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_7\">0.48</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.462</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_3\">0</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_4\">0.98</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_5\">0.278</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_6\">0.462</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_7\">1</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.155</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_3\">0.278</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_4\">0.99</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_5\">0</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_6\">0.155</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_7\">0</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_2\">1</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_3\">0.391</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_4\">0.92</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_5\">0.391</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_6\">0.88</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_7\">0.391</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.9</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_3\">0.388</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_4\">0.567</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_5\">0.995</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_6\">0.871</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_7\">0.445</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_scarifiedSoilGapFav>");
    oOut.write("<es_ssgfVal species=\"Species_2\">0.983</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_3\">0.424</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_4\">0.983</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_5\">0.424</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_6\">0.983</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_7\">0.424</es_ssgfVal>");
    oOut.write("</es_scarifiedSoilGapFav>");
    oOut.write("<es_tipUpGapFav>");
    oOut.write("<es_tugfVal species=\"Species_2\">0.009</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_3\">0.413</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_4\">0.009</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_5\">0.413</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_6\">0.009</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_7\">0.413</es_tugfVal>");
    oOut.write("</es_tipUpGapFav>");
    oOut.write("<es_freshLogGapFav>");
    oOut.write("<es_flgfVal species=\"Species_2\">0.353</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_3\">0.024</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_4\">0.353</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_5\">0.024</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_6\">0.353</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_7\">0</es_flgfVal>");
    oOut.write("</es_freshLogGapFav>");
    oOut.write("<es_decayedLogGapFav>");
    oOut.write("<es_dlgfVal species=\"Species_2\">0.058</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_3\">0.31</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_4\">0.88</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_5\">1</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_6\">0.058</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_7\">0.31</es_dlgfVal>");
    oOut.write("</es_decayedLogGapFav>");
    oOut.write("<es_forestFloorLitterGapFav>");
    oOut.write("<es_fflgfVal species=\"Species_2\">0.88</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_3\">0.771</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_4\">0.88</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_5\">0.771</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_6\">0.88</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_7\">0.771</es_fflgfVal>");
    oOut.write("</es_forestFloorLitterGapFav>");
    oOut.write("<es_forestFloorMossGapFav>");
    oOut.write("<es_ffmgfVal species=\"Species_2\">0.02</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_3\">0.942</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_4\">0.663</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_5\">0.716</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_6\">0.629</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_7\">0.951</es_ffmgfVal>");
    oOut.write("</es_forestFloorMossGapFav>");
    oOut.write("</establishment>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
