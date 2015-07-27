package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class StormDamageKillerTest extends ModelTestCase {
  
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("StormKiller");
      assertEquals(1, p_oDists.size());
      StormDamageKiller oDist = (StormDamageKiller) p_oDists.get(0);

      assertEquals(5, ((Float) oDist.mp_fMinStormDamageDBH.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6, ((Float) oDist.mp_fMinStormDamageDBH.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0.6, ((Float) oDist.mp_fStmMedDmgSurvProbA.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.34, ((Float) oDist.mp_fStmMedDmgSurvProbA.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(-0.01, ((Float) oDist.mp_fStmMedDmgSurvProbB.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-0.02, ((Float) oDist.mp_fStmMedDmgSurvProbB.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(3.82, ((Float) oDist.mp_fStmFullDmgSurvProbA.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.39, ((Float) oDist.mp_fStmFullDmgSurvProbA.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(-0.079, ((Float) oDist.mp_fStmFullDmgSurvProbB.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-0.05, ((Float) oDist.mp_fStmFullDmgSurvProbB.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0.3, ((Float) oDist.mp_fStmPropFullTipUp.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.8, ((Float) oDist.mp_fStmPropFullTipUp.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(3, oDist.m_iNumSnagYears.getValue());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure the correct options are displayed in parameters.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      String[] p_sExpected;
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDisturbance = oManager.getDisturbanceBehaviors();
      StormDamageKiller oBeh = (StormDamageKiller) oDisturbance.createBehaviorFromParameterFileTag("StormKiller");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Minimum DBH for Storm Damage, in cm",
          "Storm Medium Damage Survival Prob Intercept (a)",
          "Storm Medium Damage Survival Prob DBH Coeff. (b)",
          "Storm Heavy Damage Survival Prob Intercept (a)",
          "Storm Heavy Damage Survival Prob DBH Coeff. (b)",
          "Storm - Prop. Heavy Damage Dead Trees that Tip Up",
          "Number of Years Storm-Damaged Snags Last"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for disturbance.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests ValidateData().  Four valid files and 10 invalid files are
   * put through.
   * @throws ModelException if the validation happens incorrectly.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      new File(sFileName).delete();     
    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

            
    //Exception processing - error file 1 
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 1.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - error file 2
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile2();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 2.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - error file 3
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile3();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 3.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - error file 4
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile4();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 4.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  public void testReadParFile() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //File 1 - deterministic uniform damage pattern
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("StormKiller");
      assertEquals(1, p_oDists.size());
      StormDamageKiller oDist = (StormDamageKiller) p_oDists.get(0);

      assertEquals( ( (Float) oDist.mp_fMinStormDamageDBH.getValue().get(0)).
                   floatValue(), 5, 0.001);
      assertEquals( ( (Float) oDist.mp_fMinStormDamageDBH.getValue().get(1)).
                   floatValue(), 6, 0.001);
      
      assertEquals( ( (Float) oDist.mp_fStmMedDmgSurvProbA.getValue().
                      get(0)).floatValue(), 0.1, 0.001);
      assertEquals( ( (Float) oDist.mp_fStmMedDmgSurvProbA.getValue().
                      get(1)).floatValue(), 0.2, 0.001);

      assertEquals( ( (Float) oDist.mp_fStmMedDmgSurvProbB.getValue().
                      get(0)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) oDist.mp_fStmMedDmgSurvProbB.getValue().
                      get(1)).floatValue(), 0.4, 0.001);

      assertEquals( ( (Float) oDist.mp_fStmFullDmgSurvProbA.getValue().
                      get(0)).floatValue(), -0.1, 0.001);
      assertEquals( ( (Float) oDist.mp_fStmFullDmgSurvProbA.getValue().
                      get(1)).floatValue(), -0.2, 0.001);

      assertEquals( ( (Float) oDist.mp_fStmFullDmgSurvProbB.getValue().
                      get(0)).floatValue(), -0.3, 0.001);
      assertEquals( ( (Float) oDist.mp_fStmFullDmgSurvProbB.getValue().
                      get(1)).floatValue(), -0.4, 0.001);

      assertEquals( ( (Float) oDist.mp_fStmPropFullTipUp.getValue().get(0)).
                   floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) oDist.mp_fStmPropFullTipUp.getValue().get(1)).
                   floatValue(), 0.6, 0.001);

      assertEquals(3, oDist.m_iNumSnagYears.getValue());

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Storm validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Writes a valid file for storms with deterministic uniform damage.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormDamageApplier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormKiller</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
    oOut.write("<StormDamageApplier2>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.772772</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">2.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">4.9189817</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.2545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.936994</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.2411269</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</StormDamageApplier2>");
    oOut.write("<StormKiller3>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.1</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">0.2</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">0.3</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">0.4</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">-0.1</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">-0.2</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.3</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.4</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0.5</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.6</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>3</st_numYearsStormSnags>");
    oOut.write("</StormKiller3>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  } 
  
  /**
   * Writes a file where the "Storm Killer" behavior is enabled but not
   * the "Storm Damage Applier".
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormKiller</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
    oOut.write("<StormKiller2>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.1</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">0.2</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">0.3</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">0.4</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">-0.1</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">-0.2</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.3</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.4</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0.5</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.6</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>3</st_numYearsStormSnags>");
    oOut.write("</StormKiller2>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the species/type combos assigned to "Storm Damage
   * Applier" and "Storm Killer" don't match.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormDamageApplier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormKiller</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
    oOut.write("<StormDamageApplier2>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.772772</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">2.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">4.9189817</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.2545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.936994</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.2411269</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</StormDamageApplier2>");
    oOut.write("<StormKiller3>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.1</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">0.2</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">0.3</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">0.4</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">-0.1</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">-0.2</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.3</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.4</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0.5</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.6</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>3</st_numYearsStormSnags>");
    oOut.write("</StormKiller3>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the proportion of trees with total damage that tip up
   * is not a proportion.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile3() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormDamageApplier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormKiller</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
    oOut.write("<StormDamageApplier2>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.772772</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">2.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">4.9189817</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.2545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.936994</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.2411269</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</StormDamageApplier2>");
    oOut.write("<StormKiller3>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.1</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">0.2</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">0.3</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">0.4</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">-0.1</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">-0.2</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.3</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.4</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">1.5</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.6</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>3</st_numYearsStormSnags>");
    oOut.write("</StormKiller3>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the snag lifetime is negative.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile4() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormDamageApplier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormKiller</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
    oOut.write("<StormDamageApplier2>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.772772</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">2.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">4.9189817</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.2545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.936994</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.2411269</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</StormDamageApplier2>");
    oOut.write("<StormKiller3>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.1</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">0.2</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">0.3</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">0.4</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">-0.1</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">-0.2</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.3</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.4</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0.5</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.6</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>-3</st_numYearsStormSnags>");
    oOut.write("</StormKiller3>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes beginning portion of test parameter files.
   * @param oOut FileWriter Filestream to write to.
   * @throws IOException if there is a problem writing the file.
   */
  private void writeFileStart(FileWriter oOut) throws IOException {
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
    oOut.write("<timesteps>5</timesteps>"); 
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>storm killer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<storm>");
    oOut.write("<st_s1ReturnInterval>0</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>0</st_s10ReturnInterval>");
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
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.475987</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">3.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">3.6331242</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.3545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.2289105</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.3282479</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.6</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">2.34</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">-0.01</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">-0.02</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">3.82</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">1.39</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.079</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.05</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0.3</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0.8</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("<st_numYearsStormSnags>3</st_numYearsStormSnags>");
    oOut.write("</storm>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
