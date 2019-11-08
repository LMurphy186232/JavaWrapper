package sortie.data.funcgroups.seedpredation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.funcgroups.TestSeedPredationBehaviors;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NeighborhoodSeedPredationLnkTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredationLnk oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
           
      
      assertEquals(10.0, oPred.m_fNeighPredRadius.getValue(), SMALL_VAL);
      
      assertEquals(10, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(11, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(2, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(2)).floatValue(), SMALL_VAL);
      assertEquals(13, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(3)).floatValue(), SMALL_VAL);
      assertEquals(14, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(4)).floatValue(), SMALL_VAL);
      
      assertEquals(1.32, ((Float) oPred.mp_fNeighPredP0.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.86, ((Float) oPred.mp_fNeighPredP0.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(-0.93, ((Float) oPred.mp_fNeighPredP0.getValue().get(3)).floatValue(), SMALL_VAL);
      
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(-5.94, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(-4.72, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(2.8, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(3.1, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(0.99, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(-1.9, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(1.99, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(3.24, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(0.228, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.255, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(5.93, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(0.76, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.05, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(0.93, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that pn values are managed correctly when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredationLnk oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
      assertEquals(4, oPred.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oPred.getNumberOfCombos(); i++) {
        oCombo = oPred.getSpeciesTypeCombo(i);
        assertEquals(0, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
            
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
                   (float) 1.41, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
                   (float) 1.42, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
                   (float) 1.43, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
                   (float) 1.44, SMALL_VAL);
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.45, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.46, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.47, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.48, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.49, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.5, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.51, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.52, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.53, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.54, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.55, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.56, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.57, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.58, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.59, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.6, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.61, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.62, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.63, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.64, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.65, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.66, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.67, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.68, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.69, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.7, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.71, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.72, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.73, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.74, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.75, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.76, SMALL_VAL);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      //Linked pn
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
                   (float) 1.41, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
                   (float) 1.42, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
                   (float) 1.43, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
                   (float) 1.44, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.42, SMALL_VAL);
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.45, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.46, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.47, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.48, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.46, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.49, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.5, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.51, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.52, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.5, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.53, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.54, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.55, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.56, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.54, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.57, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.58, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.59, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.6, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.58, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.61, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.62, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.63, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.64, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.62, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.65, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.66, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.67, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.68, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.66, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.45, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.46, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.47, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.48, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.46, SMALL_VAL);
      
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.73, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.74, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.75, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.76, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.74, SMALL_VAL);
     

      System.out.println("Test copy species was successful.");
    }
    catch (ModelException oErr) {
      fail("Test copy species failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  
  /**
   * Makes sure that pn values are managed correctly when species change.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredationLnk oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
      
      //Count the total number of linked pns
      int iCount = 0;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iCount++;
        }
      }
      assertEquals(2, iCount);
      
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iIndex = i; break;
        }
      }
      
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(-5.32, ((Number) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(-3.2, ((Number) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals(2.1, ((Number) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(3.4, ((Number) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      
      assertEquals(1, oPred.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Neighborhood Seed Predation");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(4, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
        assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));                
      }
      assertEquals("Pre-predation seeds for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Pre-predation seeds for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 2", oGrid.getDataMembers()[3].getDisplayName());

      //Change the species
      String[] sNewSpecies = new String[] {
            "Species 2",
            "Species 3",
            "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oPredBeh = oManager.getSeedPredationBehaviors();
      p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);

      //Now do the linked pns
     iCount = 0;
     for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
       oData =  oPred.getRequiredData(i);
       if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
         iCount++;
       }
     }
     assertEquals(3, iCount);

     for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
      oData =  oPred.getRequiredData(i);
      if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
        iIndex = i;
        break;
      }
    }
    oVector = (ModelVector) oPred.getRequiredData(iIndex);
    assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
    assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
                 (float) 3.4, SMALL_VAL);

    iIndex++;
    oVector = (ModelVector) oPred.getRequiredData(iIndex);
    assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
               -1 != oVector.getDescriptor().indexOf("\"pn\""));

    iIndex++;
    oVector = (ModelVector) oPred.getRequiredData(iIndex);
    assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
        -1 != oVector.getDescriptor().indexOf("\"pn\""));

    iIndex++;
     if (iIndex < oPred.getNumberOfRequiredDataObjects()) {
       oVector = (ModelVector) oPred.getRequiredData(iIndex);
       assertTrue(-1 == oVector.getDescriptor().indexOf("\"pn\""));
     } 
     
     assertEquals(1, oPred.getNumberOfGrids());
     oGrid = oManager.getGridByName("Neighborhood Seed Predation");
     //Don't test cell size changes because they will not be respected
     //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
     //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
     assertEquals(6, oGrid.getDataMembers().length);
     for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
       assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
       assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));                  
     }
     assertEquals("Pre-predation seeds for Species 2", oGrid.getDataMembers()[0].getDisplayName());
     assertEquals("Pre-predation seeds for Species 3", oGrid.getDataMembers()[1].getDisplayName());
     assertEquals("Pre-predation seeds for Species 4", oGrid.getDataMembers()[2].getDisplayName());
     assertEquals("Proportion seeds eaten for Species 2", oGrid.getDataMembers()[3].getDisplayName());
     assertEquals("Proportion seeds eaten for Species 3", oGrid.getDataMembers()[4].getDisplayName());
     assertEquals("Proportion seeds eaten for Species 4", oGrid.getDataMembers()[5].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SeedPredationBehaviors oPredBeh = null;
    NeighborhoodSeedPredationLnk oPred = null;
    try {
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getSeedPredationBehaviors().validateData(oManager.
            getTreePopulation());
      }
      catch (ModelException oErr) {
        fail("Seed predation validation failed with message " + oErr.getMessage());
      }
      
      //Exception processing - linked neighborhood predation is enabled but
      //not linked functional response predation
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPredBeh.removeBehavior(p_oBehs.get(0));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail(
            "Seed predation didn't catch no linked functional response with " +
            "linked neighborhood predation.");
      }
      catch (ModelException oErr) {
        ;
      }
            
      //Exception processing - case: Neighborhood predation minimum neighbor 
      //DBH is less than zero (linked)
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
        oPred.mp_fNeighPredMinDbh.getValue().remove(0);
        oPred.mp_fNeighPredMinDbh.getValue().add(Float.valueOf((float)-1.3));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for minimum neighbor DBH.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
      
      //Exception processing - case: Neighborhood predation neighbor radius is
      //less than zero (linked)
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
        oPred.m_fNeighPredRadius.setValue((float)-1.3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for neighborhood radius.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());  
      
    } catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
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

      oManager = new GUIManager(null);
      ModelData oData;
      ModelVector oVector;

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = TestSeedPredationBehaviors.writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedNeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredationLnk oPred = (NeighborhoodSeedPredationLnk) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
      int i, iIndex;

      assertEquals( ( (Float) oPred.mp_fNeighPredMinDbh.getValue().get(0)).
          floatValue(), 13, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMinDbh.getValue().get(1)).
          floatValue(), 14, SMALL_VAL);

      assertEquals(oPred.m_fNeighPredRadius.getValue(), 11, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredP0.getValue().get(0)).
          floatValue(), 1.67, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredP0.getValue().get(1)).
          floatValue(), 1.83, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf("\"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) -5.32, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) -3.2, SMALL_VAL);
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 2.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 3.4, SMALL_VAL);
      
      //Test grid setup
      assertEquals(1, oPred.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Neighborhood Seed Predation");
      assertEquals(4, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
        assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));
      }
    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a valid seed predation parameter file.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  public String writeFile1() throws java.io.IOException {

    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">"); 
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>96</plot_lenX>");
    oOut.write("<plot_lenY>96</plot_lenY>");
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
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
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
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>LinkedFunctionalResponseSeedPredation</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>"); 
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LinkedNeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Neighborhood Seed Predation\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NonSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</NonSpatialDisperse1>");
    
    oOut.write("<FunctionalResponseSeedPredation2>");
    oOut.write("<pr_funcRespPredatorInitialDensity>0.002</pr_funcRespPredatorInitialDensity>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1>-0.04</pr_funcRespMaxInstDeclineRate1>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2>-1.04</pr_funcRespMaxInstDeclineRate2>");
    oOut.write("<pr_funcRespDemographicEfficiency1>0.0002</pr_funcRespDemographicEfficiency1>");
    oOut.write("<pr_funcRespDemographicEfficiency2>0.3</pr_funcRespDemographicEfficiency2>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1>-0.008</pr_funcRespDensityDependentCoefficient1>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2>-0.8</pr_funcRespDensityDependentCoefficient2>");
    oOut.write("<pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_frmirlVal species=\"Species_1\">300</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_2\">245</pr_frmirlVal>");
    oOut.write("</pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_funcRespForagingEfficiency>");
    oOut.write("<pr_frfelVal species=\"Species_1\">0.002</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_2\">0.444</pr_frfelVal>");
    oOut.write("</pr_funcRespForagingEfficiency>");
    oOut.write("<pr_funcRespNumWeeksSeedFall>7</pr_funcRespNumWeeksSeedFall>");
    oOut.write("<pr_funcRespWeekSeason2Starts>21</pr_funcRespWeekSeason2Starts>");
    oOut.write("<pr_funcRespNumWeeksToModel>30</pr_funcRespNumWeeksToModel>");
    oOut.write("<pr_funcRespWeekGerminationStarts>10</pr_funcRespWeekGerminationStarts>");
    oOut.write("<pr_funcRespPreservePredatorDensities>1</pr_funcRespPreservePredatorDensities>");
    oOut.write("<pr_funcRespProportionGerminating>0.1</pr_funcRespProportionGerminating>");
    oOut.write("<pr_funcRespOutputFilename>TestLinkSeeds.txt</pr_funcRespOutputFilename>");
    oOut.write("</FunctionalResponseSeedPredation2>");

    oOut.write("<NeighborhoodSeedPredation3>");
    oOut.write("<pr_neighPredRadius>11</pr_neighPredRadius>");
    oOut.write("<pr_neighPredP0>");
    oOut.write("<pr_npmlp0Val species=\"Species_1\">1.67</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_2\">1.83</pr_npmlp0Val>");
    oOut.write("</pr_neighPredP0>");
    oOut.write("<pr_neighPredPnSpecies_1>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-5.32</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">-3.2</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnSpecies_1>");
    oOut.write("<pr_neighPredPnSpecies_2>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">2.1</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">3.4</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnSpecies_2>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_nplmndVal species=\"Species_1\">13</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_2\">14</pr_nplmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");

    oOut.write("</NeighborhoodSeedPredation3>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  private String writeXMLFileForSpeciesCopy() throws java.io.IOException {
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
    oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\" />");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\" />");
    oOut.write("<tr_species speciesName=\"Hybrid_Spruce\" />");
    oOut.write("<tr_species speciesName=\"Lodgepole_Pine\" />");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\" />");
    oOut.write("<tr_species speciesName=\"Black_Cottonwood\" />");
    oOut.write("<tr_species speciesName=\"Paper_Birch\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>LinkedFunctionalResponseSeedPredation</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LinkedNeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    
    oOut.write("<NeighborhoodSeedPredation3>");
    oOut.write("<pr_neighPredPnWestern_Hemlock>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.41</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.42</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.43</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.44</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnWestern_Hemlock>");
    oOut.write("<pr_neighPredPnWestern_Redcedar>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.45</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.46</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.47</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.48</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnWestern_Redcedar>");
    oOut.write("<pr_neighPredPnAmabilis_Fir>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.49</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.5</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.51</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.52</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnAmabilis_Fir>");
    oOut.write("<pr_neighPredPnSubalpine_Fir>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.53</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.54</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.55</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.56</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnSubalpine_Fir>");
    oOut.write("<pr_neighPredPnHybrid_Spruce>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.57</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.58</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.59</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.60</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnHybrid_Spruce>");
    oOut.write("<pr_neighPredPnLodgepole_Pine>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.61</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.62</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.63</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.64</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLodgepole_Pine>");
    oOut.write("<pr_neighPredPnTrembling_Aspen>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.65</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.66</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.67</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.68</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnTrembling_Aspen>");
    oOut.write("<pr_neighPredPnBlack_Cottonwood>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.69</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.7</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.71</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.72</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnBlack_Cottonwood>");
    oOut.write("<pr_neighPredPnPaper_Birch>");
    oOut.write("<pr_nppnlVal species=\"Western_Hemlock\">1.73</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Western_Redcedar\">1.74</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Amabilis_Fir\">1.75</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Subalpine_Fir\">1.76</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnPaper_Birch>");
    
    oOut.write("</NeighborhoodSeedPredation3>");
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
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
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
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
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
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0242</tr_sacrVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.7059</tr_cdtdVal>");
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
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.464</tr_sachVal>");
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
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0263</tr_soahVal>");
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
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_3\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_4\">4</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>50</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>50</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>linked functional response seed predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Linked Neighborhood Seed Predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Neighborhood Seed Predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_5\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_5\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_4\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">2</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_4\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_5\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<seedPredation>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1Lnk>-0.04</pr_funcRespMaxInstDeclineRate1Lnk>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2Lnk>-0.04</pr_funcRespMaxInstDeclineRate2Lnk>");
    oOut.write("<pr_funcRespDemographicEfficiency1Lnk>0.0002</pr_funcRespDemographicEfficiency1Lnk>");
    oOut.write("<pr_funcRespDemographicEfficiency2Lnk>0.0002</pr_funcRespDemographicEfficiency2Lnk>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1Lnk>-0.008</pr_funcRespDensityDependentCoefficient1Lnk>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2Lnk>-0.008</pr_funcRespDensityDependentCoefficient2Lnk>");
    oOut.write("<pr_funcRespPredatorInitialDensityLnk>0.002</pr_funcRespPredatorInitialDensityLnk>");
    oOut.write("<pr_funcRespMaxIntakeRateLnk>");
    oOut.write("<pr_frmirlVal species=\"Species_1\">20</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_2\">20</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_4\">20</pr_frmirlVal>");
    oOut.write("</pr_funcRespMaxIntakeRateLnk>");
    oOut.write("<pr_funcRespForagingEfficiencyLnk>");
    oOut.write("<pr_frfelVal species=\"Species_1\">0.002</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_2\">0.002</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_4\">0.002</pr_frfelVal>");
    oOut.write("</pr_funcRespForagingEfficiencyLnk>");
    oOut.write("<pr_funcRespWeekSeason2StartsLnk>12</pr_funcRespWeekSeason2StartsLnk>");
    oOut.write("<pr_funcRespNumWeeksSeedFallLnk>12</pr_funcRespNumWeeksSeedFallLnk>");
    oOut.write("<pr_funcRespNumWeeksToModelLnk>45</pr_funcRespNumWeeksToModelLnk>");
    oOut.write("<pr_funcRespWeekGerminationStartsLnk>30</pr_funcRespWeekGerminationStartsLnk>");
    oOut.write("<pr_funcRespPreservePredatorDensitiesLnk>0</pr_funcRespPreservePredatorDensitiesLnk>");
    oOut.write("<pr_funcRespProportionGerminatingLnk>0.4</pr_funcRespProportionGerminatingLnk>");
    oOut.write("<pr_neighPredRadiusLnk>10</pr_neighPredRadiusLnk>");
    oOut.write("<pr_neighPredLnkMinNeighDBH>");
    oOut.write("<pr_nplmndVal species=\"Species_1\">10</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_2\">11</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_3\">2</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_4\">13</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_5\">14</pr_nplmndVal>");
    oOut.write("</pr_neighPredLnkMinNeighDBH>");
    oOut.write("<pr_neighPredLnkP0>");
    oOut.write("<pr_npmlp0Val species=\"Species_1\">1.32</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_2\">0.86</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_4\">-0.93</pr_npmlp0Val>");
    oOut.write("</pr_neighPredLnkP0>");
    oOut.write("<pr_neighPredPnLnkSpecies_1>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-5.94</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">-4.72</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_1>");
    oOut.write("<pr_neighPredPnLnkSpecies_2>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">2.8</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">3.1</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">0.99</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_2>");
    oOut.write("<pr_neighPredPnLnkSpecies_3>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-1.9</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">1.99</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">3.24</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_3>");
    oOut.write("<pr_neighPredPnLnkSpecies_4>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">0.228</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0.255</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">5.93</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_4>");
    oOut.write("<pr_neighPredPnLnkSpecies_5>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">0.76</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0.05</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">0.93</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_5>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredMastDecisionMethod>0</pr_neighPredMastDecisionMethod>");
    oOut.write("<pr_neighPredRadius>10</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_npmndVal species=\"Species_1\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_2\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_3\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_4\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_5\">10</pr_npmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Species_5\">-2.51</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">2.56</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">4.91</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">2.66</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">0.0255</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_neighPredMastingPnSpecies_5>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">0.0255</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_5>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Species_5\">-2.3</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">3.24</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">5.93</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">1.83</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">13.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_5>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">13.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_5>");
    oOut.write("<pr_neighPredCounts4Mast>");
    oOut.write("<pr_npc4mVal species=\"Species_5\">1</pr_npc4mVal>");
    oOut.write("</pr_neighPredCounts4Mast>");
    oOut.write("</seedPredation>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
