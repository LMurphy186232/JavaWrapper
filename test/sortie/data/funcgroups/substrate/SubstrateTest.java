package sortie.data.funcgroups.substrate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SubstrateBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class SubstrateTest extends TestCase {
  
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
      SubstrateBehaviors oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("Substrate");
      assertEquals(1, p_oBehs.size());
      Substrate oSubs = (Substrate) p_oBehs.get(0);

      assertEquals(-5.1E-4, oSubs.m_fScarSoilA.getValue(), 0.0001);
      assertEquals(4.4, oSubs.m_fScarSoilB.getValue(), 0.0001);
      assertEquals(-7.0E-4, oSubs.m_fTipUpA.getValue(), 0.0001);
      assertEquals(4.3, oSubs.m_fTipUpB.getValue(), 0.0001);
      assertEquals(-0.05, oSubs.m_fFreshlogA.getValue(), 0.0001);
      assertEquals(4.4, oSubs.m_fFreshlogB.getValue(), 0.0001);
      assertEquals(-0.7985, oSubs.m_fDecayedlogA.getValue(), 0.0001);
      assertEquals(1.1, oSubs.m_fDecayedlogB.getValue(), 0.0001);
      assertEquals(10, oSubs.m_iMaxDecayTime.getValue(), 0.0001);
      assertEquals(0.0, oSubs.m_fInitCondScarSoil.getValue(), 0.0001);
      assertEquals(0.2, oSubs.m_fInitCondTipup.getValue(), 0.0001);
      assertEquals(0.01, oSubs.m_fInitCondFreshLog.getValue(), 0.0001);
      assertEquals(0.01, oSubs.m_fInitCondDecLog.getValue(), 0.0001);
      assertEquals(0.17, oSubs.m_fPartialCutScarSoil.getValue(), 0.0001);
      assertEquals(0.15, oSubs.m_fPartialCutTipup.getValue(), 0.0001);
      assertEquals(0.06, oSubs.m_fPartialCutFreshLog.getValue(), 0.0001);
      assertEquals(0.04, oSubs.m_fPartialCutDecLog.getValue(), 0.0001);
      assertEquals(0.45, oSubs.m_fGapCutScarSoil.getValue(), 0.0001);
      assertEquals(0.34, oSubs.m_fGapCutTipup.getValue(), 0.0001);
      assertEquals(0.09, oSubs.m_fGapCutFreshLog.getValue(), 0.0001);
      assertEquals(0.0, oSubs.m_fGapCutDecLog.getValue(), 0.0001);
      assertEquals(0.36, oSubs.m_fClearCutScarSoil.getValue(), 0.0001);
      assertEquals(0.34, oSubs.m_fClearCutTipup.getValue(), 0.0001);
      assertEquals(0.17, oSubs.m_fClearCutFreshLog.getValue(), 0.0001);
      assertEquals(0.01, oSubs.m_fClearCutDecLog.getValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oSubs.mp_fProportionOfDeadThatFall.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oSubs.mp_fProportionOfDeadThatFall.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0, ((Float)oSubs.mp_fProportionOfDeadThatFall.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oSubs.mp_fProportionOfFallenThatUproot.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oSubs.mp_fProportionOfFallenThatUproot.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oSubs.mp_fProportionOfFallenThatUproot.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.15, ((Float)oSubs.mp_fProportionOfSnagsThatUproot.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(3.1, oSubs.m_fRootTipupFactor.getValue(), 0.0001);
      assertEquals(0.3, oSubs.m_fMossProportion.getValue(), 0.0001);
      assertEquals(1, oSubs.m_iDirectionalTreeFall.getValue());
      
      assertEquals(2, oSubs.getNumberOfGrids());
      Grid oGrid = oSubs.getGrid(0);
      assertEquals(3.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(2.0, oGrid.getYCellLength(), 0.0001);
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
   * Tests ValidateData().
   * 
   * @throws ModelException if there's an uncaught validation problem.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SubstrateBehaviors oSubBeh = null;
    Substrate oSub;
    try {

      oManager = new GUIManager(null);

      // Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("Substrate");
      assertEquals(1, p_oBehs.size());
      oSub = (Substrate) p_oBehs.get(0);
      oSubBeh.validateData(oManager.getTreePopulation());
    } catch (ModelException oErr) {
      fail("Substrate validation failed with message " + oErr.getMessage());
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
    
    oSubBeh = oManager.getSubstrateBehaviors();
    ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("Substrate");
    assertEquals(1, p_oBehs.size());
    oSub = (Substrate) p_oBehs.get(0);

    // Case: root tip-up factor is negative
    try {
      oSub.m_fRootTipupFactor.setValue(-3);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch negative root tipup factor.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fRootTipupFactor.setValue(3);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: max decay time is negative
    try {
      oSub.m_iMaxDecayTime.setValue(-3);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch negative max decay time.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_iMaxDecayTime.setValue(3);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: proportion of dead that fall is not a proportion
    try {
      oSub.mp_fProportionOfDeadThatFall.getValue().clear();
      oSub.mp_fProportionOfDeadThatFall.getValue().add(new Float(0.2));
      oSub.mp_fProportionOfDeadThatFall.getValue().add(new Float(1.2));
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch bad value for proportion of dead that fall.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    try {
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oSubBeh = oManager.getSubstrateBehaviors();
      oSub = (Substrate) oSubBeh.getBehaviorByParameterFileTag("Substrate").get(0);
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }

    // Case: proportion of fallen that uproot is not a proportion
    try {
      oSub.mp_fProportionOfFallenThatUproot.getValue().clear();
      oSub.mp_fProportionOfFallenThatUproot.getValue().add(new Float(0.2));
      oSub.mp_fProportionOfFallenThatUproot.getValue().add(new Float(-1.2));
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch bad value for proportion of fallen that uproot.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    try {
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oSubBeh = oManager.getSubstrateBehaviors();
      oSub = (Substrate) oSubBeh.getBehaviorByParameterFileTag("Substrate").get(0);
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }

    // Case: proportion of snags that uproot is not a proportion
    try {
      oSub.mp_fProportionOfSnagsThatUproot.getValue().clear();
      oSub.mp_fProportionOfSnagsThatUproot.getValue().add(new Float(0.2));
      oSub.mp_fProportionOfSnagsThatUproot.getValue().add(new Float(-1.2));
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch bad value for proportion of snags that uproot.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    try {
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oSubBeh = oManager.getSubstrateBehaviors();
      oSub = (Substrate) oSubBeh.getBehaviorByParameterFileTag("Substrate").get(0);
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }

    // Case: proportion of moss isn't a proportion
    try {
      oSub.m_fMossProportion.setValue(-3);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch bad moss proportion value.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fMossProportion.setValue( (float)0.3);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: initial conditions add up to greater than 1
    try {
      oSub.m_fInitCondDecLog.setValue( (float)0.9);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch initial conditions greater than 1.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fInitCondDecLog.setValue( (float)0.01);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: partial cut harvest conditions add up to greater than 1
    try {
      oSub.m_fPartialCutDecLog.setValue( (float)0.9);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch partial cut conditions greater than 1.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fPartialCutDecLog.setValue( (float)0.01);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: gap cut harvest conditions add up to greater than 1
    try {
      oSub.m_fGapCutDecLog.setValue( (float)0.9);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch gap cut conditions greater than 1.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fGapCutDecLog.setValue( (float)0.01);
    oSubBeh.validateData(oManager.getTreePopulation());

    // Case: clear cut harvest conditions add up to greater than 1
    try {
      oSub.m_fClearCutDecLog.setValue( (float)0.9);
      oSubBeh.validateData(oManager.getTreePopulation());
      fail("Substrate didn't catch clear cut conditions greater than 1.");
    } catch (ModelException oErr) {
      ;
    }
    // Reset
    oSub.m_fClearCutDecLog.setValue( (float)0.01);
    oSubBeh.validateData(oManager.getTreePopulation());
   
    System.out.println("Substrate ValidateData testing succeeded.");
  }

  
  /**
   * Tests to make sure parameter files are properly read.
   */
  public void testParFileRead() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      SubstrateBehaviors oSubBeh = oManager.getSubstrateBehaviors();
      ArrayList<Behavior> p_oBehs = oSubBeh.getBehaviorByParameterFileTag("Substrate");
      assertEquals(1, p_oBehs.size());
      Substrate oSubs = (Substrate) p_oBehs.get(0);

      // Verify that all the values are set correctly
      assertEquals(oSubs.m_fScarSoilA.getValue(), -5.1, 0.001);
      assertEquals(oSubs.m_fScarSoilB.getValue(), 4.4, 0.001);
      assertEquals(oSubs.m_fTipUpA.getValue(), -7.0, 0.001);
      assertEquals(oSubs.m_fTipUpB.getValue(), 4.3, 0.001);
      assertEquals(oSubs.m_fFreshlogA.getValue(), -0.05, 0.001);
      assertEquals(oSubs.m_fFreshlogB.getValue(), 6.5, 0.001);
      assertEquals(oSubs.m_fDecayedlogA.getValue(), -0.2, 0.001);
      assertEquals(oSubs.m_fDecayedlogB.getValue(), 5.2, 0.001);
      assertEquals(oSubs.m_fInitCondScarSoil.getValue(), 0.23, 0.001);
      assertEquals(oSubs.m_fPartialCutScarSoil.getValue(), 0.17, 0.001);
      assertEquals(oSubs.m_fGapCutScarSoil.getValue(), 0.45, 0.001);
      assertEquals(oSubs.m_fClearCutScarSoil.getValue(), 0.36, 0.001);
      assertEquals(oSubs.m_fInitCondTipup.getValue(), 0.2, 0.001);
      assertEquals(oSubs.m_fPartialCutTipup.getValue(), 0.15, 0.001);
      assertEquals(oSubs.m_fGapCutTipup.getValue(), 0.34, 0.001);
      assertEquals(oSubs.m_fClearCutTipup.getValue(), 0.32, 0.001);
      assertEquals(oSubs.m_fInitCondFreshLog.getValue(), 0.01, 0.001);
      assertEquals(oSubs.m_fPartialCutFreshLog.getValue(), 0.06, 0.001);
      assertEquals(oSubs.m_fGapCutFreshLog.getValue(), 0.09, 0.001);
      assertEquals(oSubs.m_fClearCutFreshLog.getValue(), 0.13, 0.001);
      assertEquals(oSubs.m_fInitCondDecLog.getValue(), 0.11, 0.001);
      assertEquals(oSubs.m_fPartialCutDecLog.getValue(), 0.04, 0.001);
      assertEquals(oSubs.m_fGapCutDecLog.getValue(), 0.12, 0.001);
      assertEquals(oSubs.m_fClearCutDecLog.getValue(), 0.02, 0.001);
      assertEquals(oSubs.m_fRootTipupFactor.getValue(), 3.1, 0.001);
      assertEquals(oSubs.m_fMossProportion.getValue(), 0.7, 0.001);
      assertEquals(oSubs.m_iDirectionalTreeFall.getValue(), 1);
      assertEquals(oSubs.m_iMaxDecayTime.getValue(), 3);
      assertEquals(((Float) oSubs.mp_fProportionOfDeadThatFall.getValue()
          .get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSubs.mp_fProportionOfDeadThatFall.getValue()
          .get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSubs.mp_fProportionOfFallenThatUproot.getValue()
          .get(1)).floatValue(), 0.18, 0.001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(0)).floatValue(), 0.4, 0.001);
      assertEquals(((Float) oSubs.mp_fProportionOfSnagsThatUproot.getValue()
          .get(1)).floatValue(), 0.8, 0.001);
    } catch (ModelException oErr) {
      fail("Substrate parameter file read test failed with message "
          + oErr.getMessage());
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      if (sFileName != null) {
        new File(sFileName).delete();
      }
    }
  }

  /**
   * Writes a substrate file.
   * 
   * @throws IOException if there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLValidFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

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
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
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
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Substrate</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<Substrate1>");
    oOut.write("<su_scarSoilDecayAlpha>-5.1</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-7.0</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
    oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
    oOut.write("<su_freshLogDecayBeta>6.5</su_freshLogDecayBeta>");
    oOut.write("<su_decayedLogDecayAlpha>-0.2</su_decayedLogDecayAlpha>");
    oOut.write("<su_decayedLogDecayBeta>5.2</su_decayedLogDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>3</su_maxNumberDecayYears>");
    oOut.write("<su_initialScarSoil>0.23</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.2</su_initialTipup>");
    oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
    oOut.write("<su_initialDecayedLog>0.11</su_initialDecayedLog>");
    oOut.write("<su_partialCutScarSoil>0.17</su_partialCutScarSoil>");
    oOut.write("<su_partialCutTipup>0.15</su_partialCutTipup>");
    oOut.write("<su_partialCutFreshLog>0.06</su_partialCutFreshLog>");
    oOut.write("<su_partialCutDecayedLog>0.04</su_partialCutDecayedLog>");
    oOut.write("<su_gapCutScarSoil>0.45</su_gapCutScarSoil>");
    oOut.write("<su_gapCutTipup>0.34</su_gapCutTipup>");
    oOut.write("<su_gapCutFreshLog>0.09</su_gapCutFreshLog>");
    oOut.write("<su_gapCutDecayedLog>0.12</su_gapCutDecayedLog>");
    oOut.write("<su_clearCutScarSoil>0.36</su_clearCutScarSoil>");
    oOut.write("<su_clearCutTipup>0.32</su_clearCutTipup>");
    oOut.write("<su_clearCutFreshLog>0.13</su_clearCutFreshLog>");
    oOut.write("<su_clearCutDecayedLog>0.02</su_clearCutDecayedLog>");
    oOut.write("<su_propOfDeadFall>");
    oOut.write("<su_podfVal species=\"Species_1\">1</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_2\">0.2</su_podfVal>");
    oOut.write("</su_propOfDeadFall>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_1\">0</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_2\">0.18</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">0.4</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_2\">0.8</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_mossProportion>0.7</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>1</su_directionalTreeFall>");
    oOut.write("<su_rootTipupFactor>3.1</su_rootTipupFactor>");
    oOut.write("</Substrate1>");
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
    oOut.write("<timesteps>10</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
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
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Substrate\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"scarsoil\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"ffmoss\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tipup\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog\">4</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fflitter\">5</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>3</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>2</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"substratecalcs\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"newtipup\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_0\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_1\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_2\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_3\">4</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_4\">5</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_5\">6</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_6\">7</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_7\">8</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_8\">9</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog_9\">10</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_0\">11</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_1\">12</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_2\">13</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_3\">14</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_4\">15</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_5\">16</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_6\">17</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_7\">18</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_8\">19</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog_9\">20</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>3</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>2</ma_lengthYCells>");
    oOut.write("<ma_v x=\"0\" y=\"0\">");
    oOut.write("<fl c=\"0\">0.1</fl>");
    oOut.write("<fl c=\"1\">0.01</fl>");
    oOut.write("<fl c=\"2\">0.02</fl>");
    oOut.write("<fl c=\"3\">0.03</fl>");
    oOut.write("<fl c=\"4\">0.04</fl>");
    oOut.write("<fl c=\"5\">0.05</fl>");
    oOut.write("<fl c=\"6\">0.06</fl>");
    oOut.write("<fl c=\"7\">0.07</fl>");
    oOut.write("<fl c=\"8\">0.08</fl>");
    oOut.write("<fl c=\"9\">0.09</fl>");
    oOut.write("<fl c=\"10\">0.11</fl>");
    oOut.write("<fl c=\"11\">0.11</fl>");
    oOut.write("<fl c=\"12\">0.12</fl>");
    oOut.write("<fl c=\"13\">0.13</fl>");
    oOut.write("<fl c=\"14\">0.14</fl>");
    oOut.write("<fl c=\"15\">0.15</fl>");
    oOut.write("<fl c=\"16\">0.16</fl>");
    oOut.write("<fl c=\"17\">0.17</fl>");
    oOut.write("<fl c=\"18\">0.18</fl>");
    oOut.write("<fl c=\"19\">0.19</fl>");
    oOut.write("<fl c=\"20\">0.20</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>substrate</behaviorName>");
    oOut.write("<version>2.1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">1.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">1.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">1.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("<mo_randomJuvenileMortality>");
    oOut.write("<mo_rjmVal species=\"Species_1\">0.0</mo_rjmVal>");
    oOut.write("<mo_rjmVal species=\"Species_2\">0.0</mo_rjmVal>");
    oOut.write("<mo_rjmVal species=\"Species_3\">0.0</mo_rjmVal>");
    oOut.write("</mo_randomJuvenileMortality>");
    oOut.write("</mortality>");
    oOut.write("<substrate>");
    oOut.write("<su_scarSoilDecayAlpha>-5.1E-4</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>-7.0E-4</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
    oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
    oOut.write("<su_freshLogDecayBeta>4.4</su_freshLogDecayBeta>");
    oOut.write("<su_decayedLogDecayAlpha>-0.7985</su_decayedLogDecayAlpha>");
    oOut.write("<su_decayedLogDecayBeta>1.1</su_decayedLogDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>10</su_maxNumberDecayYears>");
    oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.2</su_initialTipup>");
    oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
    oOut.write("<su_initialDecayedLog>0.01</su_initialDecayedLog>");
    oOut.write("<su_partialCutScarSoil>0.17</su_partialCutScarSoil>");
    oOut.write("<su_partialCutTipup>0.15</su_partialCutTipup>");
    oOut.write("<su_partialCutFreshLog>0.06</su_partialCutFreshLog>");
    oOut.write("<su_partialCutDecayedLog>0.04</su_partialCutDecayedLog>");
    oOut.write("<su_gapCutScarSoil>0.45</su_gapCutScarSoil>");
    oOut.write("<su_gapCutTipup>0.34</su_gapCutTipup>");
    oOut.write("<su_gapCutFreshLog>0.09</su_gapCutFreshLog>");
    oOut.write("<su_gapCutDecayedLog>0.0</su_gapCutDecayedLog>");
    oOut.write("<su_clearCutScarSoil>0.36</su_clearCutScarSoil>");
    oOut.write("<su_clearCutTipup>0.34</su_clearCutTipup>");
    oOut.write("<su_clearCutFreshLog>0.17</su_clearCutFreshLog>");
    oOut.write("<su_clearCutDecayedLog>0.01</su_clearCutDecayedLog>");
    oOut.write("<su_propOfDeadFall>");
    oOut.write("<su_podfVal species=\"Species_1\">0.2</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_2\">0.5</su_podfVal>");
    oOut.write("<su_podfVal species=\"Species_3\">0.0</su_podfVal>");
    oOut.write("</su_propOfDeadFall>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_1\">1</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_2\">1</su_pofuVal>");
    oOut.write("<su_pofuVal species=\"Species_3\">1</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">1</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_2\">0.7</su_posuVal>");
    oOut.write("<su_posuVal species=\"Species_3\">0.15</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3.1</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.3</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>1</su_directionalTreeFall>");
    oOut.write("</substrate>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
