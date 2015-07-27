package sortie.data.funcgroups.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.ManagementBehaviors;
import sortie.data.funcgroups.management.QualityVigorClassifier.QualityVigorSizeClass;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.ModelTestCase;

public class QualityVigorClassifierTest extends ModelTestCase {

  //This didn't come out before version 7 so no need to test.
  //public void testReadV6ParFile()
  
  /**
   * Tests species changes. Even though AnalysisBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Now change the species
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1",
            "Species 4",
            "Species 5",
            "Species 6"};
      
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      ManagementBehaviors oManagementBeh = oManager.getManagementBehaviors();
      ArrayList<Behavior> p_oBehs = oManagementBeh.getBehaviorByParameterFileTag("QualityVigorClassifier");
      assertEquals(1, p_oBehs.size());
      QualityVigorClassifier oQual = (QualityVigorClassifier) p_oBehs.get(0);
      
      assertEquals(3, oQual.mp_oQualityVigorSizeClasses.size());
      
      QualityVigorSizeClass oClass = oQual.mp_oQualityVigorSizeClasses.get(0);
      assertEquals(10, oClass.m_fMinDbh, 0.0001);
      assertEquals(20, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.78, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.88, ((Float)oClass.mp_fProbVigorous.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.61, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.33, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.64, ((Float)oClass.mp_fProbSawlog.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.55, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      
      oClass = oQual.mp_oQualityVigorSizeClasses.get(1);
      assertEquals(20, oClass.m_fMinDbh, 0.0001);
      assertEquals(30, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.33, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.81, ((Float)oClass.mp_fProbVigorous.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.64, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.32, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.32, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.69, ((Float)oClass.mp_fProbSawlog.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.58, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      
      oClass = oQual.mp_oQualityVigorSizeClasses.get(2);
      assertEquals(30, oClass.m_fMinDbh, 0.0001);
      assertEquals(40, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.34, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.57, ((Float)oClass.mp_fProbVigorous.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.26, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.13, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oClass.mp_fProbSawlog.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.66, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.45, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.35, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(2.43, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-2.3, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.12, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.32, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1.3, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.13, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-1, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(-0.45, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.01, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.02, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.04, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.001, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.13, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1.15, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.36, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.02, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.3, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.4, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.74, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.74, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(6, oQual.mp_iQualVigorDeciduous.getValue().size());
      for (int i = 0; i < oQual.mp_iQualVigorDeciduous.getValue().size(); i++)
        assertNotNull(oQual.mp_iQualVigorDeciduous.getValue().get(i));
      assertEquals(1, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(1)).getValue());
      assertEquals(0, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(0)).getValue());
      assertEquals(1, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(3)).getValue());
      assertEquals(0, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(4)).getValue());

      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Change of species test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

    
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      ManagementBehaviors oManagementBeh = oManager.getManagementBehaviors();
      ArrayList<Behavior> p_oBehs = oManagementBeh.getBehaviorByParameterFileTag("QualityVigorClassifier");
      assertEquals(1, p_oBehs.size());
      QualityVigorClassifier oQual = (QualityVigorClassifier) p_oBehs.get(0);
      
      assertEquals(3, oQual.mp_oQualityVigorSizeClasses.size());
      
      QualityVigorSizeClass oClass = oQual.mp_oQualityVigorSizeClasses.get(0);
      assertEquals(10, oClass.m_fMinDbh, 0.0001);
      assertEquals(20, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.78, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.88, ((Float)oClass.mp_fProbVigorous.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.61, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.33, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.64, ((Float)oClass.mp_fProbSawlog.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.55, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      
      oClass = oQual.mp_oQualityVigorSizeClasses.get(1);
      assertEquals(20, oClass.m_fMinDbh, 0.0001);
      assertEquals(30, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.33, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.81, ((Float)oClass.mp_fProbVigorous.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.64, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.32, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.32, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.69, ((Float)oClass.mp_fProbSawlog.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.58, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      
      oClass = oQual.mp_oQualityVigorSizeClasses.get(2);
      assertEquals(30, oClass.m_fMinDbh, 0.0001);
      assertEquals(40, oClass.m_fMaxDbh, 0.0001);
      
      assertEquals(0.34, ((Float)oClass.mp_fProbVigorous.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.57, ((Float)oClass.mp_fProbVigorous.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.26, ((Float)oClass.mp_fProbVigorous.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float)oClass.mp_fProbVigorous.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.13, ((Float)oClass.mp_fProbSawlog.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oClass.mp_fProbSawlog.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.66, ((Float)oClass.mp_fProbSawlog.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.45, ((Float)oClass.mp_fProbSawlog.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta0Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.35, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(2.43, ((Float)oQual.mp_fQualVigorBeta11Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-2.3, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.12, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.32, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1.3, ((Float)oQual.mp_fQualVigorBeta12Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.13, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta13Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-1, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta14Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(-0.45, ((Float)oQual.mp_fQualVigorBeta15Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.46, ((Float)oQual.mp_fQualVigorBeta16Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.01, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.02, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.04, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta2Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.001, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta3Vig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.13, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1.15, ((Float)oQual.mp_fQualVigorBeta0Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.36, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta11Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.02, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta12Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-0.3, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta13Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-0.4, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta14Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-0.2, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta2Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oQual.mp_fQualVigorBeta3Qual.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.74, ((Float)oQual.mp_fQualVigorProbNewAdultsVig.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.74, ((Float)oQual.mp_fQualVigorProbNewAdultsQual.getValue().get(4)).floatValue(), 0.0001);
      assertEquals(1, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(1)).getValue());
      assertEquals(0, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(2)).getValue());
      assertEquals(1, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(3)).getValue());
      assertEquals(0, ((ModelEnum)oQual.mp_iQualVigorDeciduous.getValue().get(4)).getValue());
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
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
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s1.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s30.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s40.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s50.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_4\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_5\">");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s40.0\">250</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s50.0\">250</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
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
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
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
    oOut.write("<behaviorName>QualityVigorClassifier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<QualityVigorClassifier1>");
    oOut.write("<ma_classifierInitialConditions>");
    oOut.write("<ma_classifierSizeClass>");
    oOut.write("<ma_classifierBeginDBH>10</ma_classifierBeginDBH>");
    oOut.write("<ma_classifierEndDBH>20</ma_classifierEndDBH>");
    oOut.write("<ma_classifierProbVigorous>");
    oOut.write("<ma_cpvVal species=\"Species_2\">0.78</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_3\">0.88</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_4\">0</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_5\">0.61</ma_cpvVal>");
    oOut.write("</ma_classifierProbVigorous>");
    oOut.write("<ma_classifierProbSawlog>");
    oOut.write("<ma_cpsVal species=\"Species_2\">0.33</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_3\">0.64</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_4\">1</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_5\">0.55</ma_cpsVal>");
    oOut.write("</ma_classifierProbSawlog>");
    oOut.write("</ma_classifierSizeClass>");
    oOut.write("<ma_classifierSizeClass>");
    oOut.write("<ma_classifierBeginDBH>20</ma_classifierBeginDBH>");
    oOut.write("<ma_classifierEndDBH>30</ma_classifierEndDBH>");
    oOut.write("<ma_classifierProbVigorous>");
    oOut.write("<ma_cpvVal species=\"Species_2\">0.33</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_3\">0.81</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_4\">0.64</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_5\">0.32</ma_cpvVal>");
    oOut.write("</ma_classifierProbVigorous>");
    oOut.write("<ma_classifierProbSawlog>");
    oOut.write("<ma_cpsVal species=\"Species_2\">0.32</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_3\">0.69</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_4\">0.33</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_5\">0.58</ma_cpsVal>");
    oOut.write("</ma_classifierProbSawlog>");
    oOut.write("</ma_classifierSizeClass>");
    oOut.write("<ma_classifierSizeClass>");
    oOut.write("<ma_classifierBeginDBH>30</ma_classifierBeginDBH>");
    oOut.write("<ma_classifierEndDBH>40</ma_classifierEndDBH>");
    oOut.write("<ma_classifierProbVigorous>");
    oOut.write("<ma_cpvVal species=\"Species_2\">0.34</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_3\">0.57</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_4\">0.26</ma_cpvVal>");
    oOut.write("<ma_cpvVal species=\"Species_5\">0.46</ma_cpvVal>");
    oOut.write("</ma_classifierProbVigorous>");
    oOut.write("<ma_classifierProbSawlog>");
    oOut.write("<ma_cpsVal species=\"Species_2\">0.13</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_3\">0.36</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_4\">0.66</ma_cpsVal>");
    oOut.write("<ma_cpsVal species=\"Species_5\">0.45</ma_cpsVal>");
    oOut.write("</ma_classifierProbSawlog>");
    oOut.write("</ma_classifierSizeClass>");
    oOut.write("</ma_classifierInitialConditions>");
    oOut.write("<ma_classifierVigBeta0>");
    oOut.write("<ma_cvb0Val species=\"Species_2\">0.1</ma_cvb0Val>");
    oOut.write("<ma_cvb0Val species=\"Species_3\">0</ma_cvb0Val>");
    oOut.write("<ma_cvb0Val species=\"Species_4\">0.3</ma_cvb0Val>");
    oOut.write("<ma_cvb0Val species=\"Species_5\">0.4</ma_cvb0Val>");
    oOut.write("</ma_classifierVigBeta0>");
    oOut.write("<ma_classifierVigBeta11>");
    oOut.write("<ma_cvb11Val species=\"Species_2\">0.2</ma_cvb11Val>");
    oOut.write("<ma_cvb11Val species=\"Species_3\">2.35</ma_cvb11Val>");
    oOut.write("<ma_cvb11Val species=\"Species_4\">0.1</ma_cvb11Val>");
    oOut.write("<ma_cvb11Val species=\"Species_5\">2.43</ma_cvb11Val>");
    oOut.write("</ma_classifierVigBeta11>");
    oOut.write("<ma_classifierVigBeta12>");
    oOut.write("<ma_cvb12Val species=\"Species_2\">-2.3</ma_cvb12Val>");
    oOut.write("<ma_cvb12Val species=\"Species_3\">1.12</ma_cvb12Val>");
    oOut.write("<ma_cvb12Val species=\"Species_4\">0.32</ma_cvb12Val>");
    oOut.write("<ma_cvb12Val species=\"Species_5\">1.3</ma_cvb12Val>");
    oOut.write("</ma_classifierVigBeta12>");
    oOut.write("<ma_classifierVigBeta13>");
    oOut.write("<ma_cvb13Val species=\"Species_2\">0.13</ma_cvb13Val>");
    oOut.write("<ma_cvb13Val species=\"Species_3\">1</ma_cvb13Val>");
    oOut.write("<ma_cvb13Val species=\"Species_4\">-0.2</ma_cvb13Val>");
    oOut.write("<ma_cvb13Val species=\"Species_5\">1</ma_cvb13Val>");
    oOut.write("</ma_classifierVigBeta13>");
    oOut.write("<ma_classifierVigBeta14>");
    oOut.write("<ma_cvb14Val species=\"Species_2\">0.9</ma_cvb14Val>");
    oOut.write("<ma_cvb14Val species=\"Species_3\">0</ma_cvb14Val>");
    oOut.write("<ma_cvb14Val species=\"Species_4\">-1</ma_cvb14Val>");
    oOut.write("<ma_cvb14Val species=\"Species_5\">0</ma_cvb14Val>");
    oOut.write("</ma_classifierVigBeta14>");
    oOut.write("<ma_classifierVigBeta15>");
    oOut.write("<ma_cvb15Val species=\"Species_2\">1</ma_cvb15Val>");
    oOut.write("<ma_cvb15Val species=\"Species_3\">0.25</ma_cvb15Val>");
    oOut.write("<ma_cvb15Val species=\"Species_4\">1</ma_cvb15Val>");
    oOut.write("<ma_cvb15Val species=\"Species_5\">-0.45</ma_cvb15Val>");
    oOut.write("</ma_classifierVigBeta15>");
    oOut.write("<ma_classifierVigBeta16>");
    oOut.write("<ma_cvb16Val species=\"Species_2\">1</ma_cvb16Val>");
    oOut.write("<ma_cvb16Val species=\"Species_3\">0.36</ma_cvb16Val>");
    oOut.write("<ma_cvb16Val species=\"Species_4\">0</ma_cvb16Val>");
    oOut.write("<ma_cvb16Val species=\"Species_5\">0.46</ma_cvb16Val>");
    oOut.write("</ma_classifierVigBeta16>");
    oOut.write("<ma_classifierVigBeta2>");
    oOut.write("<ma_cvb2Val species=\"Species_2\">0.01</ma_cvb2Val>");
    oOut.write("<ma_cvb2Val species=\"Species_3\">0.02</ma_cvb2Val>");
    oOut.write("<ma_cvb2Val species=\"Species_4\">0.04</ma_cvb2Val>");
    oOut.write("<ma_cvb2Val species=\"Species_5\">0.1</ma_cvb2Val>");
    oOut.write("</ma_classifierVigBeta2>");
    oOut.write("<ma_classifierVigBeta3>");
    oOut.write("<ma_cvb3Val species=\"Species_2\">0.001</ma_cvb3Val>");
    oOut.write("<ma_cvb3Val species=\"Species_3\">0.2</ma_cvb3Val>");
    oOut.write("<ma_cvb3Val species=\"Species_4\">0.3</ma_cvb3Val>");
    oOut.write("<ma_cvb3Val species=\"Species_5\">0.4</ma_cvb3Val>");
    oOut.write("</ma_classifierVigBeta3>");
    oOut.write("<ma_classifierQualBeta0>");
    oOut.write("<ma_cqb0Val species=\"Species_2\">0.25</ma_cqb0Val>");
    oOut.write("<ma_cqb0Val species=\"Species_3\">1.13</ma_cqb0Val>");
    oOut.write("<ma_cqb0Val species=\"Species_4\">0</ma_cqb0Val>");
    oOut.write("<ma_cqb0Val species=\"Species_5\">1.15</ma_cqb0Val>");
    oOut.write("</ma_classifierQualBeta0>");
    oOut.write("<ma_classifierQualBeta11>");
    oOut.write("<ma_cqb11Val species=\"Species_2\">0.36</ma_cqb11Val>");
    oOut.write("<ma_cqb11Val species=\"Species_3\">0</ma_cqb11Val>");
    oOut.write("<ma_cqb11Val species=\"Species_4\">0.4</ma_cqb11Val>");
    oOut.write("<ma_cqb11Val species=\"Species_5\">0</ma_cqb11Val>");
    oOut.write("</ma_classifierQualBeta11>");
    oOut.write("<ma_classifierQualBeta12>");
    oOut.write("<ma_cqb12Val species=\"Species_2\">0.02</ma_cqb12Val>");
    oOut.write("<ma_cqb12Val species=\"Species_3\">10</ma_cqb12Val>");
    oOut.write("<ma_cqb12Val species=\"Species_4\">0.3</ma_cqb12Val>");
    oOut.write("<ma_cqb12Val species=\"Species_5\">30</ma_cqb12Val>");
    oOut.write("</ma_classifierQualBeta12>");
    oOut.write("<ma_classifierQualBeta13>");
    oOut.write("<ma_cqb13Val species=\"Species_2\">0.2</ma_cqb13Val>");
    oOut.write("<ma_cqb13Val species=\"Species_3\">10</ma_cqb13Val>");
    oOut.write("<ma_cqb13Val species=\"Species_4\">-0.3</ma_cqb13Val>");
    oOut.write("<ma_cqb13Val species=\"Species_5\">30</ma_cqb13Val>");
    oOut.write("</ma_classifierQualBeta13>");
    oOut.write("<ma_classifierQualBeta14>");
    oOut.write("<ma_cqb14Val species=\"Species_2\">-0.2</ma_cqb14Val>");
    oOut.write("<ma_cqb14Val species=\"Species_3\">10</ma_cqb14Val>");
    oOut.write("<ma_cqb14Val species=\"Species_4\">-0.4</ma_cqb14Val>");
    oOut.write("<ma_cqb14Val species=\"Species_5\">30</ma_cqb14Val>");
    oOut.write("</ma_classifierQualBeta14>");
    oOut.write("<ma_classifierQualBeta2>");
    oOut.write("<ma_cqb2Val species=\"Species_2\">-0.2</ma_cqb2Val>");
    oOut.write("<ma_cqb2Val species=\"Species_3\">10</ma_cqb2Val>");
    oOut.write("<ma_cqb2Val species=\"Species_4\">0</ma_cqb2Val>");
    oOut.write("<ma_cqb2Val species=\"Species_5\">30</ma_cqb2Val>");
    oOut.write("</ma_classifierQualBeta2>");
    oOut.write("<ma_classifierQualBeta3>");
    oOut.write("<ma_cqb3Val species=\"Species_2\">1</ma_cqb3Val>");
    oOut.write("<ma_cqb3Val species=\"Species_3\">10</ma_cqb3Val>");
    oOut.write("<ma_cqb3Val species=\"Species_4\">0.1</ma_cqb3Val>");
    oOut.write("<ma_cqb3Val species=\"Species_5\">30</ma_cqb3Val>");
    oOut.write("</ma_classifierQualBeta3>");
    oOut.write("<ma_classifierNewAdultProbVigorous>");
    oOut.write("<ma_cnapvVal species=\"Species_2\">0.1</ma_cnapvVal>");
    oOut.write("<ma_cnapvVal species=\"Species_3\">0.25</ma_cnapvVal>");
    oOut.write("<ma_cnapvVal species=\"Species_4\">0.5</ma_cnapvVal>");
    oOut.write("<ma_cnapvVal species=\"Species_5\">0.74</ma_cnapvVal>");
    oOut.write("</ma_classifierNewAdultProbVigorous>");
    oOut.write("<ma_classifierNewAdultProbSawlog>");
    oOut.write("<ma_cnapsVal species=\"Species_2\">0.9</ma_cnapsVal>");
    oOut.write("<ma_cnapsVal species=\"Species_3\">0.25</ma_cnapsVal>");
    oOut.write("<ma_cnapsVal species=\"Species_4\">0.3</ma_cnapsVal>");
    oOut.write("<ma_cnapsVal species=\"Species_5\">0.74</ma_cnapsVal>");
    oOut.write("</ma_classifierNewAdultProbSawlog>");
    oOut.write("<ma_classifierDeciduous>");
    oOut.write("<ma_cdVal species=\"Species_2\">1</ma_cdVal>");
    oOut.write("<ma_cdVal species=\"Species_3\">0</ma_cdVal>");
    oOut.write("<ma_cdVal species=\"Species_4\">1</ma_cdVal>");
    oOut.write("<ma_cdVal species=\"Species_5\">0</ma_cdVal>");
    oOut.write("</ma_classifierDeciduous>");
    oOut.write("</QualityVigorClassifier1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }  
}
