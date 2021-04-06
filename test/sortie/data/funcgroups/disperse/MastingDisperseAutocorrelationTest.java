package sortie.data.funcgroups.disperse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class MastingDisperseAutocorrelationTest extends ModelTestCase {
  
  /**
   * Tests species changes. Even though DisperseBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);

      int WEIB = SpatialDisperseBase.WEIBULL;
      int CAN = SpatialDisperseBase.CANOPY;

      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Disperse with Autocorrelation");
      assertEquals(1, p_oDisps.size());
      MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 17.0, 0.001);
    
      //Mast timeseries
      assertEquals(oDisperse.mp_fMastTimeSeries[0], 0, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[1], 0.74, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[2], 1, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[3], 0.25, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[4], 0.5, 0.001);
      
      //Maximum DBH for size effects
      assertEquals(((Float)oDisperse.mp_fMaxDbh.getValue().get(0)).floatValue(), 100.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMaxDbh.getValue().get(1)).floatValue(), 120.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMaxDbh.getValue().get(3)).floatValue(), 20.0, 0.001);
      
      //Beta
      assertEquals(((Float)oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().get(0)).floatValue(), 1.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().get(1)).floatValue(), 1.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().get(3)).floatValue(), 1.2, 0.001);
      
      //Mean STR
      assertEquals(((Float)oDisperse.mp_fSTR[SpatialDisperseBase.CANOPY].getValue().get(0)).floatValue(), 500.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[SpatialDisperseBase.CANOPY].getValue().get(1)).floatValue(), 1460, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[SpatialDisperseBase.CANOPY].getValue().get(3)).floatValue(), 344, 0.001);
      
      //A, B, and C for fraction participating
      assertEquals(((Float)oDisperse.mp_fA.getValue().get(0)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fA.getValue().get(1)).floatValue(), 4.6, 0.001);
      assertEquals(((Float)oDisperse.mp_fA.getValue().get(3)).floatValue(), 10, 0.001);

      assertEquals(((Float)oDisperse.mp_fB.getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fB.getValue().get(1)).floatValue(), 2.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fB.getValue().get(3)).floatValue(), 0.6, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fC.getValue().get(0)).floatValue(), 0.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fC.getValue().get(1)).floatValue(), 0.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fC.getValue().get(3)).floatValue(), 1, 0.001);
      
      //Autocorrelation factor for rho
      assertEquals(((Float)oDisperse.mp_fACF.getValue().get(0)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fACF.getValue().get(1)).floatValue(), 46.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fACF.getValue().get(3)).floatValue(), 1.5, 0.001);
      
      //Standard deviation for noise for rho
      assertEquals(((Float)oDisperse.mp_fRhoNoiseSD.getValue().get(0)).floatValue(), 0.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fRhoNoiseSD.getValue().get(1)).floatValue(), 0.6, 0.001);
      assertEquals(((Float)oDisperse.mp_fRhoNoiseSD.getValue().get(3)).floatValue(), 0, 0.001);
      
      //Slope and intercept of the linear function of DBH for individual
      //probability of reproducting
      assertEquals(((Float)oDisperse.mp_fPRA.getValue().get(0)).floatValue(), 0.75, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRA.getValue().get(1)).floatValue(), 0.45, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRA.getValue().get(3)).floatValue(), 0.97, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fPRB.getValue().get(0)).floatValue(), 0.004, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRB.getValue().get(1)).floatValue(), 0.04, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRB.getValue().get(3)).floatValue(), 0.06, 0.001);
      
      //Seed producer score standard deviation
      assertEquals(((Float)oDisperse.mp_fSPSSD.getValue().get(0)).floatValue(), 0.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fSPSSD.getValue().get(1)).floatValue(), 0.34, 0.001);
      assertEquals(((Float)oDisperse.mp_fSPSSD.getValue().get(3)).floatValue(), 0.53, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 0);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.76E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), 1.82E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 9.61E-05, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.2, 0.001);
      
    }
    catch (ModelException oErr) {
      fail("Par file test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
      
  /**
   * Tests the ValidateData function.
   */
  public void testValidateData() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = null;
      TreePopulation oPop = null;
      try {
        //Valid file 1
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        new File(sFileName).delete();
      }
      catch (ModelException oErr) {
        fail("Disperse validation failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }    
      
      //Valid file 2     
      try {
        sFileName = writeLognormalDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        new File(sFileName).delete();
      }
      catch (ModelException oErr) {
        fail("Disperse validation failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }    
      
      //Case: Maximum DBH for size effects less than min repro DBH
      try {
        sFileName = writeErrorFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch max repro less than min repro.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("DBH of Max Size Effect") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Fraction participating A == 0
      try {
        sFileName = writeErrorFile2();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch fraction a == 0.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("\"a\" for Fraction of Pop") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Reproduction fraction c not between 0 and 1
      try {
        sFileName = writeErrorFile3();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch fraction c not between 0 and 1.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("\"c\" for Fraction of Pop") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Autocorrelation factor for rho <= 0
      try {
        sFileName = writeErrorFile4();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch autocorrelation factor < 0.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Autocorrelation Range for Rho") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Standard deviation for noise for rho less than 0
      try {
        sFileName = writeErrorFile5();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch sd rho noise < 0.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Standard Deviation for Rho") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Seed producer standard deviation less than 0.
      try {
        sFileName = writeErrorFile6();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch sd sps < 0.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Standard Deviation of Seed Producer Score") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      //Case: Provided timestep masting level not between 0 and 1.
      try {
        sFileName = writeErrorFile7();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        fail("Masting disperse with autocorrelation validation failed to catch bad timestep masting.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("masting levels") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }

      finally {
        new File(sFileName).delete();
      }
      
      
      //Case: Lognormal canopy Xbs = 0
      try {
        sFileName = writeLognormalDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb canopy values " +
            "when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy Xb") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: Lognormal canopy X0s = 0
      try {
        sFileName = writeLognormalDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 canopy values " +
            "when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy X0") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }      
                
      //Case: canopy thetas are greater than 50
      try {
        oManager.clearCurrentData();
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta canopy values " +
            "when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Canopy Theta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
            
      //Case: weibull canopy betas are greater than 25 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta weibull canopy " +
            "values when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: lognormal canopy betas are greater than 25
      try {
        sFileName = writeLognormalDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta lognormal canopy " +
            "values when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
                        
      //Case: a value in mp_fMinDbhForReproduction is negative
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(1, p_oDisps.size());
        MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().remove(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch negative reproduction DBH" +
            " values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Minimum DBH for Reproduction") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }      
    }  
    catch (ModelException oErr) {
      fail("Disperse validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests species changes. Even though DisperseBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);

      int WEIB = SpatialDisperseBase.WEIBULL;
      int CAN = SpatialDisperseBase.CANOPY;


      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
      assertEquals(1, p_oDisps.size());
      MastingDisperseAutocorrelation oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
      assertEquals(1, p_oDisps.size());
      oDisperse = (MastingDisperseAutocorrelation) p_oDisps.get(0);
      
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 17.0, 0.001);
    
      //Mast timeseries
      assertEquals(oDisperse.mp_fMastTimeSeries[0], 0, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[1], 0.74, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[2], 1, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[3], 0.25, 0.001);
      assertEquals(oDisperse.mp_fMastTimeSeries[4], 0.5, 0.001);
      
      //Maximum DBH for size effects
      assertEquals(((Float)oDisperse.mp_fMaxDbh.getValue().get(3)).floatValue(), 100.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMaxDbh.getValue().get(1)).floatValue(), 20.0, 0.001);
      
      //Beta
      assertEquals(((Float)oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().get(3)).floatValue(), 1.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().get(1)).floatValue(), 1.2, 0.001);
      
      //Mean STR
      assertEquals(((Float)oDisperse.mp_fSTR[SpatialDisperseBase.CANOPY].getValue().get(3)).floatValue(), 500.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[SpatialDisperseBase.CANOPY].getValue().get(1)).floatValue(), 344, 0.001);
      
      //A, B, and C for fraction participating
      assertEquals(((Float)oDisperse.mp_fA.getValue().get(3)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fA.getValue().get(1)).floatValue(), 10, 0.001);

      assertEquals(((Float)oDisperse.mp_fB.getValue().get(3)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fB.getValue().get(1)).floatValue(), 0.6, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fC.getValue().get(3)).floatValue(), 0.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fC.getValue().get(1)).floatValue(), 1, 0.001);
      
      //Autocorrelation factor for rho
      assertEquals(((Float)oDisperse.mp_fACF.getValue().get(3)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fACF.getValue().get(1)).floatValue(), 1.5, 0.001);
      
      //Standard deviation for noise for rho
      assertEquals(((Float)oDisperse.mp_fRhoNoiseSD.getValue().get(3)).floatValue(), 0.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fRhoNoiseSD.getValue().get(1)).floatValue(), 0, 0.001);
      
      //Slope and intercept of the linear function of DBH for individual
      //probability of reproducting
      assertEquals(((Float)oDisperse.mp_fPRA.getValue().get(3)).floatValue(), 0.75, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRA.getValue().get(1)).floatValue(), 0.97, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fPRB.getValue().get(3)).floatValue(), 0.004, 0.001);
      assertEquals(((Float)oDisperse.mp_fPRB.getValue().get(1)).floatValue(), 0.06, 0.001);
      
      //Seed producer score standard deviation
      assertEquals(((Float)oDisperse.mp_fSPSSD.getValue().get(3)).floatValue(), 0.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fSPSSD.getValue().get(1)).floatValue(), 0.53, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 1.76E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), 9.61E-05, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.2, 0.001);
      
      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a file with all disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDisperseXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06070101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>5</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MastingDisperseAutocorrelation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<MastingDisperseAutocorrelation1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">10.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");

    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.74</di_mdaMTS>)");
    oOut.write("<di_mdaMTS ts=\"3\">1</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.25</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"5\">0.5</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");


    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_4\">20</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_4\">1.2</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_4\">344</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_4\">10</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_4\">0.6</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_4\">1</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_4\">1.5</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_4\">0</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_4\">0.97</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_4\">0.06</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_4\">0.53</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_4\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_4\">9.61E-05</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_4\">3.2</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</GeneralDisperse>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with all disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeLognormalDisperseXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06070101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>5</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MastingDisperseAutocorrelation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<MastingDisperseAutocorrelation1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_1\">2.82</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_2\">3.32</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_1\">3.7</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_2\">3.8</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Maximum DBH for size effects less than min repro DBH
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">9</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Case: Fraction participating A == 0
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile2() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">0</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Reproduction fraction c not between 0 and 1
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile3() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.74</di_mdaMTS>)");
    oOut.write("<di_mdaMTS ts=\"3\">1</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.25</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"5\">0.5</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");


    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">1.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Autocorrelation factor for rho <= 0
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile4() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">-5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Standard deviation for noise for rho less than 0
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile5() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.74</di_mdaMTS>)");
    oOut.write("<di_mdaMTS ts=\"3\">1</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.25</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"5\">0.5</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");


    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">-0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Seed producer standard deviation less than 0.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile6() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.74</di_mdaMTS>)");
    oOut.write("<di_mdaMTS ts=\"3\">1</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.25</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"5\">0.5</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");


    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">-0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Case: Provided timestep masting level not between 0 and 1.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeErrorFile7() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    writeCommonStuff(oOut);
    
    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">1.74</di_mdaMTS>)");
    oOut.write("<di_mdaMTS ts=\"3\">1</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.25</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"5\">0.5</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");


    //Maximum DBH for size effects
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("<di_mdfseVal species=\"Species_2\">120</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");

    //Beta
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">1.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");

    //Mean STR
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">500</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">1460</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");

    //A, B, and C for fraction participating
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">2</di_mdarfaVal>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">4.6</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">2.5</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0.2</di_mdarfcVal>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">0.4</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");

    //Autocorrelation factor for rho
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">5.2</di_mdaraVal>");
    oOut.write("<di_mdaraVal species=\"Species_2\">46.3</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");

    //Standard deviation for noise for rho
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0.2</di_mdarnsdVal>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0.6</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");

    //Slope and intercept of the linear function of DBH for individual
    //probability of reproducting
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("<di_mdapraVal species=\"Species_2\">0.45</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.04</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");

    //Seed producer score standard deviation
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.34</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");

    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  } 
   
  private void writeCommonStuff(FileWriter oOut) throws IOException {
        oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06070101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>5</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");

    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">15.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">16.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MastingDisperseAutocorrelation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<MastingDisperseAutocorrelation1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">10.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">10.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
  }
  
}
