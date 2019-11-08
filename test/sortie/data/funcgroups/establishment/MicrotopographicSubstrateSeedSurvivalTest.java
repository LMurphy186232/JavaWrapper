package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;


public class MicrotopographicSubstrateSeedSurvivalTest extends ModelTestCase {
    
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
      Behavior oBeh = oEst.createBehaviorFromParameterFileTag("MicrotopographicSubstrateSeedSurvival");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Fraction Seeds Germinating on Mound Fresh Logs",
          "Fraction Seeds Germinating on Mound Decayed Logs",
          "Fraction Seeds Germinating on Mound Scarified Soil",
          "Fraction Seeds Germinating on Mound Forest Floor Litter",
          "Fraction Seeds Germinating on Mound Forest Floor Moss",
          "Fraction Seeds Germinating on Mound Tip-Up",
          "Fraction Seeds Germinating on Ground Fresh Logs",
          "Fraction Seeds Germinating on Ground Decayed Logs",
          "Fraction Seeds Germinating on Ground Scarified Soil",
          "Fraction Seeds Germinating on Ground Forest Floor Litter",
          "Fraction Seeds Germinating on Ground Forest Floor Moss",
          "Fraction Seeds Germinating on Ground Tip-Up",
          "Proportion of Plot Area that is Mound"
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
    MicrotopographicSubstrateSeedSurvival oEst = null;
    int MOUND = MicrotopographicSubstrateSeedSurvival.MOUND,
        GROUND = MicrotopographicSubstrateSeedSurvival.GROUND;
    try {
      sFileName = writeValidFile();
      
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oEstBeh = oManager.getEstablishmentBehaviors();
      oEstBeh.validateData(oManager.getTreePopulation());
      
      //Exception processing - case:  mound tip-up isn't a proportion
      try {
        
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);        
        
        oEst.mp_fTipUpFavorability[MOUND].getValue().clear();
        oEst.mp_fTipUpFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fTipUpFavorability[MOUND].getValue().add(Float.valueOf((float) -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound tip-up " +
        		"favorability.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      //Exception processing - case:  mound scarified soil isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fScarifiedSoilFavorability[MOUND].getValue().clear();
        oEst.mp_fScarifiedSoilFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fScarifiedSoilFavorability[MOUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound scarified soil " +
        		"favorability.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      //Exception processing - case:  mound forest floor litter isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fForestFloorLitterFavorability[MOUND].getValue().clear();
        oEst.mp_fForestFloorLitterFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fForestFloorLitterFavorability[MOUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound forest floor " +
        		"litter favorability.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      //Exception processing - case:  mound forest floor moss isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fForestFloorMossFavorability[MOUND].getValue().clear();
        oEst.mp_fForestFloorMossFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fForestFloorMossFavorability[MOUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound forest " +
        		"floor moss favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  mound fresh logs isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fFreshLogsFavorability[MOUND].getValue().clear();
        oEst.mp_fFreshLogsFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fFreshLogsFavorability[MOUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound fresh " +
        		"logs favorability.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      //Exception processing - case:  mound decayed logs isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fDecayedLogsFavorability[MOUND].getValue().clear();
        oEst.mp_fDecayedLogsFavorability[MOUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fDecayedLogsFavorability[MOUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound decayed " +
        		"logs favorability.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      //Exception processing - case:  ground tip-up isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fTipUpFavorability[GROUND].getValue().clear();
        oEst.mp_fTipUpFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fTipUpFavorability[GROUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground " +
        		"tip-up favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  ground scarified soil isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fScarifiedSoilFavorability[GROUND].getValue().clear();
        oEst.mp_fScarifiedSoilFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fScarifiedSoilFavorability[GROUND].getValue().add(Float.valueOf((float)-1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground scarified " +
        		"soil favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  ground forest floor litter isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fForestFloorLitterFavorability[GROUND].getValue().clear();
        oEst.mp_fForestFloorLitterFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fForestFloorLitterFavorability[GROUND].getValue().add(Float.valueOf((float) -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground forest floor " +
        		"litter favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  ground forest floor moss isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fForestFloorMossFavorability[GROUND].getValue().clear();
        oEst.mp_fForestFloorMossFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fForestFloorMossFavorability[GROUND].getValue().add(Float.valueOf((float) -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground forest floor " +
        		"moss favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  ground fresh logs isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fFreshLogsFavorability[GROUND].getValue().clear();
        oEst.mp_fFreshLogsFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fFreshLogsFavorability[GROUND].getValue().add(Float.valueOf((float) -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground fresh " +
        		"logs favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case:  ground decayed logs isn't a proportion
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.mp_fDecayedLogsFavorability[GROUND].getValue().clear();
        oEst.mp_fDecayedLogsFavorability[GROUND].getValue().add(Float.valueOf((float)0.2));
        oEst.mp_fDecayedLogsFavorability[GROUND].getValue().add(Float.valueOf((float) -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for ground decayed " +
        		"logs favorability.");
      }
      catch (ModelException oErr) {
        ;
      }

    //Exception processing - case:  mound proportion isn't a proportion
      try {        
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);
        oEst.m_fMoundProportion.setValue( (float) 1.5);
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for mound proportion.");
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
      int GROUND = MicrotopographicSubstrateSeedSurvival.GROUND,
          MOUND = MicrotopographicSubstrateSeedSurvival.MOUND;
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_2", "Species_1");
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      MicrotopographicSubstrateSeedSurvival oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);


      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.31, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.31, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.331, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.331, 0.001);                  

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.23, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);            

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.024, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.024, 0.001);            

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.424, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.413, 0.001);
      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.413, 0.001);

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(0)).floatValue(), 0.424, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0, 0.001);

      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(0)).floatValue(), 0.278, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0.278, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(0)).floatValue(), 0.391, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.391, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.23, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);

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
   * Makes sure all parameters read correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    int GROUND = MicrotopographicSubstrateSeedSurvival.GROUND,
        MOUND = MicrotopographicSubstrateSeedSurvival.MOUND;
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
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      MicrotopographicSubstrateSeedSurvival oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);

      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(2)).floatValue(), 0.058, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.31, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(2)).floatValue(), 0.88, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.331, 0.001);                  

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(2)).floatValue(), 0.456, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);            

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].
          getValue().get(2)).floatValue(), 0.353, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.024, 0.001);            

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(2)).floatValue(), 0.983, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(2)).floatValue(), 0.155, 0.001);
      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.413, 0.001);

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(2)).floatValue(), 0.01, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(2)).floatValue(), 0.462, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0, 0.001);

      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(2)).floatValue(), 0.88, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0.278, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(2)).floatValue(), 1, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.391, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(2)).floatValue(), 0.456, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);

      assertEquals(oEst.m_fMoundProportion.getValue(), 0.75, 0.001);

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
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      int GROUND = MicrotopographicSubstrateSeedSurvival.GROUND,
          MOUND = MicrotopographicSubstrateSeedSurvival.MOUND;
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicrotopographicSubstrateSeedSurvival");
      assertEquals(1, p_oBehs.size());
      MicrotopographicSubstrateSeedSurvival oEst = (MicrotopographicSubstrateSeedSurvival) p_oBehs.get(0);


      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.058, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.31, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.88, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.331, 0.001);                  

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.456, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);            

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.353, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.024, 0.001);            

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.983, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(0)).floatValue(), 0.155, 0.001);
      assertEquals(((Float) oEst.mp_fTipUpFavorability[GROUND].getValue().
          get(1)).floatValue(), 0.413, 0.001);

      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(0)).floatValue(), 0.01, 0.001);
      assertEquals(((Float) oEst.mp_fScarifiedSoilFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.424, 0.001);

      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(0)).floatValue(), 0.462, 0.001);
      assertEquals(((Float) oEst.mp_fFreshLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0, 0.001);

      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(0)).floatValue(), 0.88, 0.001);
      assertEquals(((Float) oEst.mp_fDecayedLogsFavorability[MOUND].getValue().
          get(1)).floatValue(), 0.278, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorLitterFavorability[MOUND].
          getValue().get(1)).floatValue(), 0.391, 0.001);

      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(0)).floatValue(), 0.456, 0.001);
      assertEquals(((Float) oEst.mp_fForestFloorMossFavorability[GROUND].
          getValue().get(1)).floatValue(), 0.23, 0.001);

      assertEquals(oEst.m_fMoundProportion.getValue(), 0.75, 0.001);

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
    oOut.write(
    "<behaviorName>MicrotopographicSubstrateSeedSurvival</behaviorName>");
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
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_1\">0.983</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.424</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_1\">0.155</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.413</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_1\">0.353</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.024</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_1\">0.058</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.31</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_1\">0.88</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_2\">0.331</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_1\">0.456</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.23</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_scarifiedSoilMoundFav>");
    oOut.write("<es_ssmfVal species=\"Species_1\">0.01</es_ssmfVal>");
    oOut.write("<es_ssmfVal species=\"Species_2\">0.424</es_ssmfVal>");
    oOut.write("</es_scarifiedSoilMoundFav>");
    oOut.write("<es_tipUpMoundFav>");
    oOut.write("<es_tumfVal species=\"Species_1\">0.315</es_tumfVal>");
    oOut.write("<es_tumfVal species=\"Species_2\">0.48</es_tumfVal>");
    oOut.write("</es_tipUpMoundFav>");
    oOut.write("<es_freshLogMoundFav>");
    oOut.write("<es_flmfVal species=\"Species_1\">0.462</es_flmfVal>");
    oOut.write("<es_flmfVal species=\"Species_2\">0</es_flmfVal>");
    oOut.write("</es_freshLogMoundFav>");
    oOut.write("<es_decayedLogMoundFav>");
    oOut.write("<es_dlmfVal species=\"Species_1\">0.88</es_dlmfVal>");
    oOut.write("<es_dlmfVal species=\"Species_2\">0.278</es_dlmfVal>");
    oOut.write("</es_decayedLogMoundFav>");
    oOut.write("<es_forestFloorLitterMoundFav>");
    oOut.write("<es_fflmfVal species=\"Species_1\">1</es_fflmfVal>");
    oOut.write("<es_fflmfVal species=\"Species_2\">0.391</es_fflmfVal>");
    oOut.write("</es_forestFloorLitterMoundFav>");
    oOut.write("<es_forestFloorMossMoundFav>");
    oOut.write("<es_ffmmfVal species=\"Species_1\">0.9</es_ffmmfVal>");
    oOut.write("<es_ffmmfVal species=\"Species_2\">0.388</es_ffmmfVal>");
    oOut.write("</es_forestFloorMossMoundFav>");
    oOut.write("<es_moundProportion>0.75</es_moundProportion>");
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
}