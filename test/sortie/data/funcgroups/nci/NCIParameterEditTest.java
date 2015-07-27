package sortie.data.funcgroups.nci;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.nci.CrowdingEffectDefault;
import sortie.data.funcgroups.nci.DamageEffectDefault;
import sortie.data.funcgroups.nci.NCITermDefault;
import sortie.data.funcgroups.nci.ShadingDefault;
import sortie.data.funcgroups.nci.SizeEffectDefault;
import sortie.data.funcgroups.nci.NitrogenEffectGaussian;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.funcgroups.nci.PrecipitationEffectWeibull;
import sortie.data.funcgroups.nci.TemperatureEffectWeibull;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.ModelFlowSetup;
import sortie.gui.ModelFlowSetup.DisplayBehaviorComboEdit;
import sortie.gui.ModelFlowSetup.DisplayBehaviorEdit;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.NCIEffectsEditor;
import sortie.gui.behaviorsetup.NCIParameterEdit;
import sortie.gui.behaviorsetup.TableData;

public class NCIParameterEditTest extends ModelTestCase {
  
  /**
   * Tests proper display of an existing behavior.
   */
  public void testExistingBehaviorRead() {
    String sFileName = null;
    try {    
      GUIManager oManager = new GUIManager(new MainWindow());
      oManager.clearCurrentData();
      sFileName = NCIMasterGrowthTest.writeXMLValidFile2(true);
      oManager.inputXMLParameterFile(sFileName);
      
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      
      //Open the parameters window
      NCIParameterEdit oNCIEdit = new NCIParameterEdit(null, oManager, oManager.getMainWindow(), oGrowth);
      
      //Verify the chose effects 
      NCIEffectsEditor oNCIEffEdit = new NCIEffectsEditor(oGrowth, oNCIEdit);
      assertEquals("Default crowding", oNCIEffEdit.m_jCrowdingEffect.getSelectedItem().toString());
      assertEquals("Default shading", oNCIEffEdit.m_jShadingEffect.getSelectedItem().toString());
      assertEquals("Default NCI term", oNCIEffEdit.m_jNCITerm.getSelectedItem().toString());
      assertEquals("Default size effect", oNCIEffEdit.m_jSizeEffect.getSelectedItem().toString());
      assertEquals("Default storm damage", oNCIEffEdit.m_jDamageEffect.getSelectedItem().toString());
      assertEquals("Weibull precipitation effect", oNCIEffEdit.m_jPrecipitationEffect.getSelectedItem().toString());
      assertEquals("Weibull temperature effect", oNCIEffEdit.m_jTemperatureEffect.getSelectedItem().toString());
      assertEquals("Gaussian nitrogen effect", oNCIEffEdit.m_jNitrogenEffect.getSelectedItem().toString());
      assertEquals("Size independent infection effect", oNCIEffEdit.m_jInfectionEffect.getSelectedItem().toString());
      oNCIEffEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      
      //Verify that there's all the effects
      assertEquals(10, oNCIEdit.mp_oAllTables.size());
      String[] p_sExpected = new String[]{
          "NCI Maximum Potential Growth, cm/yr",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Growth Increment Adjustment PDF"};
      BehaviorParameterDisplay oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(0))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Shading effect
      p_sExpected = new String[]{
          "NCI Shading Effect Coefficient (m)",
          "NCI Shading Effect Exponent (n)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(1))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Crowding effect
      p_sExpected = new String[]{
          "NCI Crowding Effect Slope (C)",
          "NCI Crowding Effect Steepness (D)",
          "NCI Size Sensitivity to NCI (gamma)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(2))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //NCI term
      p_sExpected = new String[]{
          "NCI Maximum Crowding Distance, in meters",
          "NCI Minimum Neighbor DBH, in cm",
          "NCI Alpha",
          "NCI Beta",
          "Species 1 NCI Lambda Neighbors",
          "Species 2 NCI Lambda Neighbors",
          "NCI DBH Divisor (q)",
          "Include Snags in NCI Calculations"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(3))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Size Effect
      p_sExpected = new String[]{
          "NCI Size Effect Mode, in cm (X0)",
          "NCI Size Effect Variance, in cm (Xb)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(4))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Damage effect
      p_sExpected = new String[]{
          "NCI Damage Effect - Medium Storm Damage (0-1)",
          "NCI Damage Effect - Complete Storm Damage (0-1)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(5))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Precipitation effect
      p_sExpected = new String[]{
          "Weibull Precip Effect \"A\"",
          "Weibull Precip Effect \"B\"",
          "Weibull Precip Effect \"C\"",
          "Precipitation Type to Use"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(6))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Temperature effect
      p_sExpected = new String[]{
          "Weibull Temperature Effect \"A\"",
          "Weibull Temperature Effect \"B\"",
          "Weibull Temperature Effect \"C\""};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(7))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Nitrogen effect
      p_sExpected = new String[]{
          "Nitrogen Effect X0",
          "Nitrogen Effect Xb"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(8))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Infection effect
      p_sExpected = new String[]{
          "Infection Effect \"a\"",
          "Infection Effect \"b\""};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(9))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests the edit window when NCI is first added
   */
  public void testNewBehaviorAdd() {
    
    String sFileName = null;
    try {
      
      GUIManager oManager = new GUIManager(new MainWindow());
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Add the NCI behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(5); //Growth behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(12);  //NCI
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      DisplayBehaviorComboEdit oEdit2 = oSetup.new DisplayBehaviorComboEdit(oBehEdit, 
          oBehEdit.m_jEnabledBehaviorListModel.get(2), oManager.getHelpBroker());
      //Add combos
      oEdit2.m_jSpecies.setSelectedIndices(new int[] {0, 2});
      oEdit2.m_jTypes.setSelectedIndices(new int[] {
          TreePopulation.SAPLING, 
          TreePopulation.ADULT});
      oEdit2.actionPerformed(new ActionEvent(this, 0, "Add"));
      //Simulate OK button click
      oEdit2.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      
      //Open the parameters window
      NCIParameterEdit oNCIEdit = new NCIParameterEdit(null, oManager, oManager.getMainWindow(), oGrowth);
      //Verify that there's only the right parameters in the 
      //parameters window
      assertEquals(1, oNCIEdit.mp_oAllTables.size());
      assertEquals(2, oNCIEdit.mp_oAllTables.get(0).size());
      
      String[] p_sExpected = {
          "NCI Maximum Potential Growth, cm/yr",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Growth Increment Adjustment PDF"};
      BehaviorParameterDisplay oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(0))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Verify the default effects 
      NCIEffectsEditor oNCIEffEdit = new NCIEffectsEditor(oGrowth, oNCIEdit);
      assertEquals("No crowding", oNCIEffEdit.m_jCrowdingEffect.getSelectedItem().toString());
      assertEquals("No shading", oNCIEffEdit.m_jShadingEffect.getSelectedItem().toString());
      assertEquals("No NCI term", oNCIEffEdit.m_jNCITerm.getSelectedItem().toString());
      assertEquals("No size effect", oNCIEffEdit.m_jSizeEffect.getSelectedItem().toString());
      assertEquals("No storm damage", oNCIEffEdit.m_jDamageEffect.getSelectedItem().toString());
      assertEquals("No precipitation effect", oNCIEffEdit.m_jPrecipitationEffect.getSelectedItem().toString());
      assertEquals("No temperature effect", oNCIEffEdit.m_jTemperatureEffect.getSelectedItem().toString());
      assertEquals("No nitrogen effect", oNCIEffEdit.m_jNitrogenEffect.getSelectedItem().toString());
      assertEquals("No infection effect", oNCIEffEdit.m_jInfectionEffect.getSelectedItem().toString());
      
      //Set effects and verify that the table updates
      oNCIEffEdit.m_jCrowdingEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jShadingEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jNCITerm.setSelectedIndex(1);
      oNCIEffEdit.m_jSizeEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jDamageEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jPrecipitationEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jTemperatureEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jNitrogenEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jInfectionEffect.setSelectedIndex(1);
      oNCIEffEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      assertEquals(10, oNCIEdit.mp_oAllTables.size());
      p_sExpected = new String[]{"NCI Maximum Potential Growth, cm/yr",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Growth Increment Adjustment PDF"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(0))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Size Effect
      p_sExpected = new String[]{
          "NCI Size Effect Mode, in cm (X0)",
          "NCI Size Effect Variance, in cm (Xb)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(1))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Crowding effect
      p_sExpected = new String[]{
          "NCI Crowding Effect Slope (C)",
          "NCI Crowding Effect Steepness (D)",
          "NCI Size Sensitivity to NCI (gamma)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(2))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //NCI term
      p_sExpected = new String[]{
          "NCI Maximum Crowding Distance, in meters",
          "NCI Minimum Neighbor DBH, in cm",
          "NCI Alpha",
          "NCI Beta",
          "Species 1 NCI Lambda Neighbors",
          "Species 2 NCI Lambda Neighbors",
          "Species 3 NCI Lambda Neighbors",
          "NCI DBH Divisor (q)",
          "Include Snags in NCI Calculations"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(3))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Shading effect
      p_sExpected = new String[]{
          "NCI Shading Effect Coefficient (m)",
          "NCI Shading Effect Exponent (n)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(4))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Damage effect
      p_sExpected = new String[]{
          "NCI Damage Effect - Medium Storm Damage (0-1)",
          "NCI Damage Effect - Complete Storm Damage (0-1)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(5))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Precipitation effect
      p_sExpected = new String[]{
          "Weibull Precip Effect \"A\"",
          "Weibull Precip Effect \"B\"",
          "Weibull Precip Effect \"C\"",
          "Precipitation Type to Use"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(6))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Temperature effect
      p_sExpected = new String[]{
          "Weibull Temperature Effect \"A\"",
          "Weibull Temperature Effect \"B\"",
          "Weibull Temperature Effect \"C\""};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(7))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Nitrogen effect
      p_sExpected = new String[]{
          "Nitrogen Effect X0",
          "Nitrogen Effect Xb"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(8))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Infection effect
      p_sExpected = new String[]{
          "Infection Effect \"a\"",
          "Infection Effect \"b\""};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(9))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //*********************************************
      //Put values in the tables
      
      assertEquals(10, oNCIEdit.mp_oAllTables.size());
      
      //PDF
      EnhancedTable oEditTable = oNCIEdit.mp_oAllTables.get(0).get(0);
      int i, j;
      oEditTable.setValueAt("&&Normal|None,Normal,Lognormal", 1, 1);
      
      //Max growth and rand parameter
      float fCount = 1;
      oEditTable = oNCIEdit.mp_oAllTables.get(0).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Maximum Potential Growth, cm/yr")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Std Deviation for Normal or Lognormal Adjustment")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      //Size Effect
      oEditTable = oNCIEdit.mp_oAllTables.get(1).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Size Effect Mode, in cm (X0)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Size Effect Variance, in cm (Xb)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
        
      //Crowding effect
      oEditTable = oNCIEdit.mp_oAllTables.get(2).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Crowding Effect Slope (C)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Crowding Effect Steepness (D)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      } 
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Size Sensitivity to NCI (gamma)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      //NCI term
      oEditTable = oNCIEdit.mp_oAllTables.get(3).get(2);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Minimum Neighbor DBH, in cm")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(3).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Maximum Crowding Distance, in meters")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
            
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Alpha")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Beta")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      fCount = 0;
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 1 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 2 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 3 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(3).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI DBH Divisor (q)")) {
          oEditTable.setValueAt(String.valueOf(fCount), i, 1);
          fCount++;           
        }
      }
      
      //Shading effect
      oEditTable = oNCIEdit.mp_oAllTables.get(4).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Shading Effect Coefficient (m)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Shading Effect Exponent (n)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      //Damage effect
      fCount = 0;
      oEditTable = oNCIEdit.mp_oAllTables.get(5).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Damage Effect - Medium Storm Damage (0-1)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Damage Effect - Complete Storm Damage (0-1)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Precipitation effect
      oEditTable = oNCIEdit.mp_oAllTables.get(6).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Precipitation Type to Use")) {
          oEditTable.setValueAt(1, i, 1);           
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(6).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"A\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"B\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"C\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Temperature effect
      oEditTable = oNCIEdit.mp_oAllTables.get(7).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"A\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"B\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"C\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Nitrogen effect
      oEditTable = oNCIEdit.mp_oAllTables.get(8).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Nitrogen Effect X0")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Nitrogen Effect Xb")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Infection effect
      oEditTable = oNCIEdit.mp_oAllTables.get(9).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Infection Effect \"a\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Infection Effect \"b\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Click OK
      oNCIEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //**********************************
      // Check that the values got to the behavior
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      
      fCount = 1;
      
      assertEquals(fCount, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oGrowth.mp_fRandParameter.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oGrowth.mp_fRandParameter.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(0);
      
      assertEquals(fCount, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(1);
      
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof NCITermDefault);
      NCITermDefault oNCITerm = (NCITermDefault) oGrowth.mp_oEffects.get(2);
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      j = -1;
      ModelData oData;
      ModelVector oVector;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData = oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          j = i;
          break;
        }
      }
      assertTrue(j >= 0);
      
      fCount = 0;
      oVector = (ModelVector) oNCITerm.getDataObject(j);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      oVector = (ModelVector) oNCITerm.getDataObject(j + 1);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      oVector = (ModelVector) oNCITerm.getDataObject(j + 2);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001); fCount++;
      
      // Shading Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof ShadingDefault);
      ShadingDefault oShadingEffect = (ShadingDefault) oGrowth.mp_oEffects.get(3);
      
      assertEquals(fCount, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(2)).floatValue(), 0.0001); fCount++;
                        
      // Damage Effect
      fCount = 0;
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oGrowth.mp_oEffects.get(4);
      
      assertEquals(fCount, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      //Precipitation Effect
      assertTrue(oGrowth.mp_oEffects.get(5) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(5);
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(1, oPrecipEffect.m_iPrecipType.getValue());
      
      //Temperature Effect
      assertTrue(oGrowth.mp_oEffects.get(6) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(6);
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempA.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempB.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempC.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      //Nitrogen Effect
      assertTrue(oGrowth.mp_oEffects.get(7) instanceof NitrogenEffectGaussian);
      NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(7);
      
      assertEquals(fCount, ((Double) oNEffect.mp_fX0.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oNEffect.mp_fX0.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Double) oNEffect.mp_fXb.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oNEffect.mp_fXb.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
      //Infection Effect
      assertTrue(oGrowth.mp_oEffects.get(8) instanceof InfectionEffect);
      InfectionEffect oInfEffect = (InfectionEffect) oGrowth.mp_oEffects.get(8);
      
      assertEquals(fCount, ((Double) oInfEffect.mp_fA.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oInfEffect.mp_fA.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Double) oInfEffect.mp_fB.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oInfEffect.mp_fB.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  
  /**
   * Tests the edit window when NCI is first added
   */
  public void testNewQuadratBehaviorAdd() {
    
    String sFileName = null;
    try {
      
      GUIManager oManager = new GUIManager(new MainWindow());
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Add the NCI behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(5); //Growth behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(13);  //NCI quadrat growth
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      DisplayBehaviorComboEdit oEdit2 = oSetup.new DisplayBehaviorComboEdit(oBehEdit, 
          oBehEdit.m_jEnabledBehaviorListModel.get(2), oManager.getHelpBroker());
      //Add combos
      oEdit2.m_jSpecies.setSelectedIndices(new int[] {0, 2});
      oEdit2.m_jTypes.setSelectedIndices(new int[] {
          TreePopulation.SAPLING, 
          TreePopulation.ADULT});
      oEdit2.actionPerformed(new ActionEvent(this, 0, "Add"));
      //Simulate OK button click
      oEdit2.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      //Open the parameters window
      NCIParameterEdit oNCIEdit = new NCIParameterEdit(null, oManager, oManager.getMainWindow(), oGrowth);
      //Verify that there's only the right parameters in the 
      //parameters window
      assertEquals(1, oNCIEdit.mp_oAllTables.size());
      assertEquals(2, oNCIEdit.mp_oAllTables.get(0).size());
      
      String[] p_sExpected = {
          "NCI Maximum Potential Growth, cm/yr",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Growth Increment Adjustment PDF"};
      BehaviorParameterDisplay oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(0))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Verify the default effects 
      NCIEffectsEditor oNCIEffEdit = new NCIEffectsEditor(oGrowth, oNCIEdit);
      assertEquals("No crowding", oNCIEffEdit.m_jCrowdingEffect.getSelectedItem().toString());
      assertNull(oNCIEffEdit.m_jShadingEffect);
      assertEquals("No NCI term", oNCIEffEdit.m_jNCITerm.getSelectedItem().toString());
      assertNull(oNCIEffEdit.m_jSizeEffect);
      assertNull(oNCIEffEdit.m_jDamageEffect);
      assertEquals("No precipitation effect", oNCIEffEdit.m_jPrecipitationEffect.getSelectedItem().toString());
      assertEquals("No temperature effect", oNCIEffEdit.m_jTemperatureEffect.getSelectedItem().toString());
      assertEquals("No nitrogen effect", oNCIEffEdit.m_jNitrogenEffect.getSelectedItem().toString());
      assertEquals("No infection effect", oNCIEffEdit.m_jInfectionEffect.getSelectedItem().toString());
      
      //Set effects and verify that the table updates
      oNCIEffEdit.m_jCrowdingEffect.setSelectedIndex(2);
      //oNCIEffEdit.m_jShadingEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jNCITerm.setSelectedIndex(1);
      //oNCIEffEdit.m_jSizeEffect.setSelectedIndex(1);
      //oNCIEffEdit.m_jDamageEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jPrecipitationEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jTemperatureEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jNitrogenEffect.setSelectedIndex(1);
      oNCIEffEdit.m_jInfectionEffect.setSelectedIndex(0); //no valid infection effects
      oNCIEffEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      assertEquals(6, oNCIEdit.mp_oAllTables.size());
      p_sExpected = new String[]{"NCI Maximum Potential Growth, cm/yr",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Growth Increment Adjustment PDF"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(0))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
            
      //Crowding effect no size
      p_sExpected = new String[]{
          "NCI Crowding Effect Slope (C)",
          "NCI Crowding Effect Steepness (D)"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(1))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //NCI term
      p_sExpected = new String[]{
          "NCI Maximum Crowding Distance, in meters",
          "NCI Minimum Neighbor DBH, in cm",
          "NCI Alpha",
          "NCI Beta",
          "Species 1 NCI Lambda Neighbors",
          "Species 2 NCI Lambda Neighbors",
          "Species 3 NCI Lambda Neighbors",
          "NCI DBH Divisor (q)",
          "Include Snags in NCI Calculations"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(2))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
                  
      //Precipitation effect
      p_sExpected = new String[]{
          "Weibull Precip Effect \"A\"",
          "Weibull Precip Effect \"B\"",
          "Weibull Precip Effect \"C\"",
          "Precipitation Type to Use"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(3))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Temperature effect
      p_sExpected = new String[]{
          "Weibull Temperature Effect \"A\"",
          "Weibull Temperature Effect \"B\"",
          "Weibull Temperature Effect \"C\""};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(4))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
      
      //Nitrogen effect
      p_sExpected = new String[]{
          "Nitrogen Effect X0",
          "Nitrogen Effect Xb"};
      oDisp = new BehaviorParameterDisplay();
      oDisp.mp_oTableData = new ArrayList<TableData>(0);
      for (EnhancedTable oTable : oNCIEdit.mp_oAllTables.get(5))
        oDisp.mp_oTableData.add(oTable.getData());
      TestTable(oDisp, p_sExpected);
            
      //*********************************************
      //Put values in the tables
      
      assertEquals(6, oNCIEdit.mp_oAllTables.size());
      
      //PDF
      EnhancedTable oEditTable = oNCIEdit.mp_oAllTables.get(0).get(0);
      int i, j;
      oEditTable.setValueAt("&&Normal|None,Normal,Lognormal", 1, 1);
      
      //Max growth and rand parameter
      float fCount = 1;
      oEditTable = oNCIEdit.mp_oAllTables.get(0).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Maximum Potential Growth, cm/yr")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Std Deviation for Normal or Lognormal Adjustment")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
              
      //Crowding effect
      oEditTable = oNCIEdit.mp_oAllTables.get(1).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Crowding Effect Slope (C)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Crowding Effect Steepness (D)")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      } 
      
      //NCI term
      oEditTable = oNCIEdit.mp_oAllTables.get(2).get(2);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Minimum Neighbor DBH, in cm")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(2).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Maximum Crowding Distance, in meters")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
            
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Alpha")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI Beta")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount++;
          } 
        }
      }
      
      fCount = 0;
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 1 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 2 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Species 3 NCI Lambda Neighbors")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(2).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("NCI DBH Divisor (q)")) {
          oEditTable.setValueAt(String.valueOf(fCount), i, 1);
          fCount++;           
        }
      }
                  
      //Precipitation effect
      oEditTable = oNCIEdit.mp_oAllTables.get(3).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Precipitation Type to Use")) {
          oEditTable.setValueAt(1, i, 1);           
        }
      }
      
      oEditTable = oNCIEdit.mp_oAllTables.get(3).get(1);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"A\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"B\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Precip Effect \"C\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Temperature effect
      oEditTable = oNCIEdit.mp_oAllTables.get(4).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"A\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"B\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Weibull Temperature Effect \"C\"")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      //Nitrogen effect
      oEditTable = oNCIEdit.mp_oAllTables.get(5).get(0);
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Nitrogen Effect X0")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
      
      for (i = 0; i < oEditTable.getData().mp_oTableData.length; i++) {
        if (oEditTable.getData().mp_oTableData[i][0].toString().equals("Nitrogen Effect Xb")) {
          for (j = 1; j < oEditTable.getData().mp_oTableData[i].length; j++) {
            oEditTable.setValueAt(String.valueOf(fCount), i, j);
            fCount += 0.1;
          } 
        }
      }
            
      //Click OK
      oNCIEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //**********************************
      // Check that the values got to the behavior
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      fCount = 1;
      
      assertEquals(fCount, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oGrowth.mp_fRandParameter.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oGrowth.mp_fRandParameter.getValue().get(2)).floatValue(), 0.0001); fCount++;
            
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);
      
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCITermDefault);
      NCITermDefault oNCITerm = (NCITermDefault) oGrowth.mp_oEffects.get(1);
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001); fCount++;
      assertEquals(fCount, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(2)).floatValue(), 0.0001); fCount++;
      
      j = -1;
      ModelData oData;
      ModelVector oVector;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData = oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          j = i;
          break;
        }
      }
      assertTrue(j >= 0);
      
      fCount = 0;
      oVector = (ModelVector) oNCITerm.getDataObject(j);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      oVector = (ModelVector) oNCITerm.getDataObject(j + 1);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      oVector = (ModelVector) oNCITerm.getDataObject(j + 2);
      assertEquals(fCount, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oVector.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001); fCount++;
            
      //Precipitation Effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(2);
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(1, oPrecipEffect.m_iPrecipType.getValue());
      
      //Temperature Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(3);
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempA.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempB.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempC.getValue().get(0)).floatValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Float) oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001); fCount += 0.1;
      
      //Nitrogen Effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof NitrogenEffectGaussian);
      NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(4);
      
      assertEquals(fCount, ((Double) oNEffect.mp_fX0.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oNEffect.mp_fX0.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
      assertEquals(fCount, ((Double) oNEffect.mp_fXb.getValue().get(0)).doubleValue(), 0.0001); fCount += 0.1;
      assertEquals(fCount, ((Double) oNEffect.mp_fXb.getValue().get(2)).doubleValue(), 0.0001); fCount += 0.1;
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
}
