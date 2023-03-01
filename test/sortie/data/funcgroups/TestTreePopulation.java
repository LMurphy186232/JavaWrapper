package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

import sortie.ModelTestCase;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.sax.ParameterFileParser;

/**
 * Tests the TreePopulation class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestTreePopulation extends ModelTestCase {

  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;  
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      TreeBehavior oTreeBeh = oPop.getTreeBehavior();
      oPop.validateData(oPop);
      
      assertEquals(0.1, oTreeBeh.m_fInitialSeedlingSize.getValue(), 0.0001);

      assertEquals(10.0, ((Float) oTreeBeh.mp_fMinAdultDbh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.0, ((Float) oTreeBeh.mp_fMinAdultDbh.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(12.0, ((Float) oTreeBeh.mp_fMinAdultDbh.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1.35, ((Float) oTreeBeh.mp_fMaxSeedlingHeight.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.35, ((Float) oTreeBeh.mp_fMaxSeedlingHeight.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.35, ((Float) oTreeBeh.mp_fMaxSeedlingHeight.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(5, oTreeBeh.mp_fSizeClasses.getValue().size());
      assertEquals(0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(0)).floatValue(), 0.0001); 
      assertEquals(2.5, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(20.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(30.0, ((Float)oTreeBeh.mp_fSizeClasses.getValue().get(4)).floatValue(), 0.0001);
      
      //Seedling
      ModelVector oVec = (ModelVector)oTreeBeh.mp_fInitialDensities.getValue().get(0);
      assertEquals(20.0, ((Float)oVec.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(23.0, ((Float)oVec.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(26.0, ((Float)oVec.getValue().get(2)).floatValue(), 0.0001);
      
      //5.0
      oVec = (ModelVector)oTreeBeh.mp_fInitialDensities.getValue().get(2);
      assertEquals(21.0, ((Float)oVec.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.0, ((Float)oVec.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(27.0, ((Float)oVec.getValue().get(2)).floatValue(), 0.0001);
      
      //30.0
      oVec = (ModelVector)oTreeBeh.mp_fInitialDensities.getValue().get(4);
      assertEquals(22.0, ((Float)oVec.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(25.0, ((Float)oVec.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(28.0, ((Float)oVec.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(50.0, oTreeBeh.m_fSeedlingHeightClass1.getValue(), 0.0001);
      assertEquals(100.0, oTreeBeh.m_fSeedlingHeightClass2.getValue(), 0.0001);
      
      assertEquals(12, ((Float)oTreeBeh.mp_fSeedlingClass1Density.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(14, ((Float)oTreeBeh.mp_fSeedlingClass1Density.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(16, ((Float)oTreeBeh.mp_fSeedlingClass1Density.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(18, ((Float)oTreeBeh.mp_fSeedlingClass2Density.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(20, ((Float)oTreeBeh.mp_fSeedlingClass2Density.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(22, ((Float)oTreeBeh.mp_fSeedlingClass2Density.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(24, ((Float)oTreeBeh.mp_fSeedlingClass3Density.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(26, ((Float)oTreeBeh.mp_fSeedlingClass3Density.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(28, ((Float)oTreeBeh.mp_fSeedlingClass3Density.getValue().get(2)).floatValue(), 0.0001);
      
      //Verify - total number of trees
      assertEquals(30, oPop.mp_oTrees.size());
      int iCount = 0;

      Tree oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(146.43, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(28.03, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(30, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(60.21, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(103.08, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(169.07, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(2, oTree.m_iType);
      assertEquals(96.09, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(68.36, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(5.48, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(25.91, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Height")).floatValue(), 0.001);
      assertEquals(6.28, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Diam10")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(122.11, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(196.53, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(158.09, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(3.71, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(129.5, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(39.63, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(42.45, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(146.42, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(66.7, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(65.09, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(67.14, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(134.2, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(129.73, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(29.78, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(14.32, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(2, oTree.m_iType);
      assertEquals(56.68, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(102.56, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(5.99, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(56.72, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Height")).floatValue(), 0.001);
      assertEquals(8.09, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Diam10")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(0, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(5.4, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(18.2, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(113.62, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      //10 trees - species 2, no age specified
      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(198.33, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(75.31, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(123.58, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(11.39, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(27.76, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(148.61, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(155.96, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(182.99, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(36.37, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(75.94, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(16.16, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(193.42, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(54.51, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(177.38, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(37.45, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(194.04, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(142.94, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(175.12, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(13.89, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(48.47, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(45.64, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(46.96, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(9.69, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(172.22, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(168.69, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(85.4, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(23.13, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(1, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(34.34, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(161.95, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(141.95, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      iCount++;

      //10 trees - species 3, age
      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(155.79, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(193.57, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(34.39, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(10, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(65.52, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(147.45, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(139.3, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(11, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(146.23, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(145, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(184.3, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(12, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(20.93, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(53.83, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(101.73, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(13, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(30.76, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(15.95, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(74.42, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(14, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(148.47, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(85.93, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(75.76, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(15, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(114.69, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(160.07, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(40.55, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(16, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(20.62, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(37.63, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(44.82, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(17, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(6.27, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(102.24, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(136.63, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(18, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;

      oTree = oPop.mp_oTrees.get(iCount);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(3, oTree.m_iType);
      assertEquals(62.6, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).floatValue(), 0.001);
      assertEquals(170.46, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).floatValue(), 0.001);
      assertEquals(134.38, oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).floatValue(), 0.001);
      assertEquals(19, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Tree Age")).intValue());
      iCount++;
    }
    catch (ModelException oErr) {
      fail("Version 6 file reading failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  /**
   * Tests the ValidateData method.
   */
  public void testValidateData() {
    String sFileName = "";
    GUIManager oManager;
    TreePopulation oPopBeh;
    TreeBehavior oPop;
    try {
      //Neutral case
      oManager = new GUIManager(null);
      sFileName = writeXMLTreeMap1();
      oManager.inputXMLFile(sFileName, null);
      oPopBeh = oManager.getTreePopulation();
      oPopBeh.validateData(oPopBeh);
      oPop = oPopBeh.getTreeBehavior();

      //Set up seedling initial densities
      oPop.m_fSeedlingHeightClass1.setValue((float)10.0);
      oPop.m_fSeedlingHeightClass2.setValue((float)100.0);
      Float[] p_fDensities = new Float[]{Float.valueOf((float)0), Float.valueOf((float)0.1), Float.valueOf((float)0.2), Float.valueOf((float)0.4), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0)};
      Behavior.setVectorValues(oPop.mp_fSeedlingClass1Density, p_fDensities);
      oPopBeh.validateData(oPopBeh);
    } catch (ModelException oErr) {
      fail("Test ValidateData failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    try {
      //Test the case where there is data for seedling densities of size class
      //1, but the size class bounds fields are both 0
      oManager = new GUIManager(null);
      sFileName = writeXMLTreeMap1();
      oManager.inputXMLFile(sFileName, null);
      oPopBeh = oManager.getTreePopulation();
      oPopBeh.validateData(oPopBeh);
      oPopBeh = oManager.getTreePopulation();
      oPop = oPopBeh.getTreeBehavior();

      oPop.m_fSeedlingHeightClass1.setValue((float)0.0);
      oPop.m_fSeedlingHeightClass2.setValue((float)0.0);
      Float[] p_fDensities = new Float[]{Float.valueOf((float)0), Float.valueOf((float)0.1), Float.valueOf((float)0.2), Float.valueOf((float)0.4), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0)};
      Behavior.setVectorValues(oPop.mp_fSeedlingClass1Density, p_fDensities);
      oPopBeh.validateData(oPopBeh);
      fail("Test ValidateData didn't catch lack of seedling bounds.");
    } catch (ModelException oErr) {
      ;
    }
    finally {
      new File(sFileName).delete();
    }

    try {
      //Test the case where there is data for seedling densities of size class
      //2, but the size class bounds fields are both 0
      oManager = new GUIManager(null);
      sFileName = writeXMLTreeMap1();
      oManager.inputXMLFile(sFileName, null);
      oPopBeh = oManager.getTreePopulation();
      oPopBeh.validateData(oPopBeh);
      oPop = oPopBeh.getTreeBehavior();

      oPop.m_fSeedlingHeightClass1.setValue((float)0.0);
      oPop.m_fSeedlingHeightClass2.setValue((float)0.0);
      Float[] p_fDensities = new Float[]{Float.valueOf((float)0), Float.valueOf((float)0.1), Float.valueOf((float)0.2), Float.valueOf((float)0.4), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0)};
      Behavior.setVectorValues(oPop.mp_fSeedlingClass2Density, p_fDensities);
      oPopBeh.validateData(oPopBeh);
      fail("Test ValidateData didn't catch lack of seedling bounds.");
    } catch (ModelException oErr) {
      ;
    }
    finally {
      new File(sFileName).delete();
    }

    try {
      //Test the case where there is data for seedling densities of size class
      //3, but the size class bounds fields are both 0
      oManager = new GUIManager(null);
      sFileName = writeXMLTreeMap1();
      oManager.inputXMLFile(sFileName, null);
      oPopBeh = oManager.getTreePopulation();
      oPopBeh.validateData(oPopBeh);
      oPop = oPopBeh.getTreeBehavior();

      oPop.m_fSeedlingHeightClass1.setValue((float)0.0);
      oPop.m_fSeedlingHeightClass2.setValue((float)0.0);
      Float[] p_fDensities = new Float[]{Float.valueOf((float)0), Float.valueOf((float)0.1), Float.valueOf((float)0.2), Float.valueOf((float)0.4), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0), Float.valueOf((float)0)};
      Behavior.setVectorValues(oPop.mp_fSeedlingClass3Density, p_fDensities);
      oPopBeh.validateData(oPopBeh);
      fail("Test ValidateData didn't catch lack of seedling bounds.");
    } catch (ModelException oErr) {
      ;
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests reading a tab delimited tree map.
   */
  public void testTabDelimTreeMapReading() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLFile(writeTreeMapParFile(), null);
      TreePopulation oPop = oManager.getTreePopulation();

      /************************
       Map file 1 - no trees
       *************************/
      javax.swing.JOptionPane.showMessageDialog(null,
          "In all dialog boxes, say \"Add trees to parameter file\".",
          "TESTING MESSAGE",
          javax.swing.JOptionPane.
          INFORMATION_MESSAGE);
      String sFileName = writeTabTreeMap1();
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(0, oPop.mp_oTrees.size());
      new File(sFileName).delete();

      /************************
        Map file 2 - one tree
       *************************/
      sFileName = writeTabTreeMap2();
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(1, oPop.mp_oTrees.size());
      //Verify tree
      Tree oTree = oPop.mp_oTrees.get(0);
      int iSpecies = oTree.m_iSpecies,
          iType = oTree.m_iType;
      assertEquals(2, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 5.58472,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 9.69238,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 14.06372,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));
      oPop.clearTrees();

      new File(sFileName).delete();

      /************************
        Map file 3 - several trees
       *************************/
      sFileName = writeTabTreeMap3();
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(7, oPop.mp_oTrees.size());
      //Verify trees
      //Tree 1
      oTree = oPop.mp_oTrees.get(0);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SEEDLING, iType);
      assertEquals( (float) 5.58472,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 9.69238,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 0.12,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Diam10")).floatValue(),
          0.001);
      assertEquals( (float) 1.2,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 2
      oTree = oPop.mp_oTrees.get(1);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.SAPLING, iType);
      assertEquals( (float) 4.01001,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 10.553,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 8.50342,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 3
      oTree = oPop.mp_oTrees.get(2);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(2, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 7.47681,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 29.0466,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 33.36319,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 5.05853,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 4
      oTree = oPop.mp_oTrees.get(3);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SNAG, iType);
      assertEquals( (float) 4.93164,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 20.4529,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 13.21426,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 5
      oTree = oPop.mp_oTrees.get(4);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.SAPLING, iType);
      assertEquals( (float) 9.11865,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 33.8013,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 2.77977,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 5.12386,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 6
      oTree = oPop.mp_oTrees.get(5);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(2, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 9.31396,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 41.3818,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 39.6077,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 7
      oTree = oPop.mp_oTrees.get(6);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SNAG, iType);
      assertEquals( (float) 3.92456,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 45.7397,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 7.78549,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 8.53078,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      oPop.clearTrees();

      new File(sFileName).delete();

      /************************
       Map file 4 - several trees, including some outside the plot;
       for this, the user is warned and does not elect to continue
       *************************/
      sFileName = writeTabTreeMap4();
      //Tell the tester to accept the warning about the trees outside of the
      //plot
      javax.swing.JOptionPane.showMessageDialog(null,
          "You will be warned ONE TIME about trees outside the plot.\n" +
              "Please tell it NOT to continue.",
              "TESTING MESSAGE",
              javax.swing.JOptionPane.
              INFORMATION_MESSAGE);
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(0, oPop.mp_oTrees.size());

      new File(sFileName).delete();

      /************************
       Map file 4 - several trees, including some outside the plot;
       for this, the user is warned and elects to continue
       *************************/
      sFileName = writeTabTreeMap4();
      //Tell the tester to accept the warning about the trees outside of the
      //plot
      javax.swing.JOptionPane.showMessageDialog(null,
          "You will be warned ONE TIME about trees outside the plot.\n" +
              "Please tell it to continue.",
              "TESTING MESSAGE",
              javax.swing.JOptionPane.
              INFORMATION_MESSAGE);
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(2, oPop.mp_oTrees.size());
      //Verify trees
      //Tree 1
      oTree = oPop.mp_oTrees.get(0);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 9.33838,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 99.5117,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 28.63892,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 2
      oTree = oPop.mp_oTrees.get(1);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 8.80127,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 94.9768,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 29.42276,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //oPop.ClearTrees(); DON'T CLEAR THIS TIME

      new File(sFileName).delete();

      /************************
       Read in another map file while there are trees; user elects to
       replace those trees
       *************************/
      sFileName = writeTabTreeMap3();
      //Tell the tester to replace trees
      javax.swing.JOptionPane.showMessageDialog(null,
          "Please tell SORTIE to replace trees in the next dialog.",
          "TESTING MESSAGE",
          javax.swing.JOptionPane.
          INFORMATION_MESSAGE);
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(7, oPop.mp_oTrees.size());
      //Verify trees
      //Tree 1
      oTree = oPop.mp_oTrees.get(0);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SEEDLING, iType);
      assertEquals( (float) 5.58472,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 9.69238,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 0.12,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Diam10")).floatValue(),
          0.001);
      assertEquals( (float) 1.2,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 2
      oTree = oPop.mp_oTrees.get(1);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.SAPLING, iType);
      assertEquals( (float) 4.01001,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 10.553,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 8.50342,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 3
      oTree = oPop.mp_oTrees.get(2);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(2, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 7.47681,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 29.0466,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 33.36319,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 5.05853,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 4
      oTree = oPop.mp_oTrees.get(3);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SNAG, iType);
      assertEquals( (float) 4.93164,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 20.4529,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 13.21426,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 5
      oTree = oPop.mp_oTrees.get(4);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.SAPLING, iType);
      assertEquals( (float) 9.11865,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 33.8013,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 2.77977,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 5.12386,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //Tree 6
      oTree = oPop.mp_oTrees.get(5);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(2, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 9.31396,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 41.3818,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 39.6077,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 7
      oTree = oPop.mp_oTrees.get(6);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.SNAG, iType);
      assertEquals( (float) 3.92456,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 45.7397,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 7.78549,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertEquals( (float) 8.53078,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")).floatValue(),
          0.001);

      //oPop.ClearTrees(); DON'T CLEAR TREES

      new File(sFileName).delete();

      /************************
       Read in another map file while there are trees; user elects to
       add to those trees
       *************************/
      sFileName = writeTabTreeMap4();
      //Tell the tester to accept the warning about the trees outside of the
      //plot
      javax.swing.JOptionPane.showMessageDialog(null,
          "You will be warned ONE TIME about trees outside the plot.\n" +
              "Please tell it to continue.\n" +
              "Also, ADD TO existing trees.",
              "TESTING MESSAGE",
              javax.swing.JOptionPane.
              INFORMATION_MESSAGE);
      oPop.addTabDelimTreeMapFile(null, sFileName);

      //Make sure the right number of trees were read
      assertEquals(9, oPop.mp_oTrees.size());
      //Verify trees
      //Tree 1
      oTree = oPop.mp_oTrees.get(7);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(0, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 9.33838,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 99.5117,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 28.63892,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //Tree 2
      oTree = oPop.mp_oTrees.get(8);
      iSpecies = oTree.m_iSpecies;
      iType = oTree.m_iType;
      assertEquals(1, iSpecies);
      assertEquals(TreePopulation.ADULT, iType);
      assertEquals( (float) 8.80127,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "X")).floatValue(), 0.001);
      assertEquals( (float) 94.9768,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Y")).floatValue(), 0.001);
      assertEquals( (float) 29.42276,
          oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "DBH")).floatValue(),
          0.001);
      assertNull(oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(iSpecies).get(iType), "Height")));

      //oPop.ClearTrees(); DON'T CLEAR THIS TIME

      new File(sFileName).delete();

      /************************
       Error processing - invalid species
       *************************/
      try {
        sFileName = writeTabTreeMap5();
        //Tell the user to add to existing trees
        javax.swing.JOptionPane.showMessageDialog(null,
            "Please tell it to ADD TO existing trees.",
            "TESTING MESSAGE",
            javax.swing.JOptionPane.
            INFORMATION_MESSAGE);
        oPop.addTabDelimTreeMapFile(null, sFileName);
        fail("Didn't catch unrecognized species in tab-delimited tree map testing.");
      }
      catch (ModelException oErr) {
        //Make sure that no trees were added
        assertEquals(9, oPop.mp_oTrees.size());
        //Make sure the error was right
        assertTrue(oErr.getMessage().indexOf("invalid species") > 0);
        oPop.clearTrees();
      }
      finally {
        new File(sFileName).delete();
      }

      /************************
       Error processing - invalid life history stage
       *************************/
      try {
        sFileName = writeTabTreeMap6();
        oPop.addTabDelimTreeMapFile(null, sFileName);
        fail("Didn't catch unrecognized life history stage in tab-delimited tree map testing.");
      }
      catch (ModelException oErr) {
        //Make sure that no trees were added
        assertEquals(0, oPop.mp_oTrees.size());
        //Make sure the error was right
        assertTrue(oErr.getMessage().indexOf("invalid tree type") > 0);
      }
      finally {
        new File(sFileName).delete();
      }

      /************************
       Error processing - negative diameter
       *************************/
      try {
        sFileName = writeTabTreeMap7();
        oPop.addTabDelimTreeMapFile(null, sFileName);
        fail("Didn't catch negative diameter in tab-delimited tree map testing.");
      }
      catch (ModelException oErr) {
        //Make sure that no trees were added
        assertEquals(0, oPop.mp_oTrees.size());
        //Make sure the error was right
        assertTrue(oErr.getMessage().indexOf("invalid tree diameter") > 0);
      }
      finally {
        new File(sFileName).delete();
      }

      /************************
       Error processing - negative height
       *************************/
      try {
        sFileName = writeTabTreeMap8();
        oPop.addTabDelimTreeMapFile(null, sFileName);
        fail("Didn't catch negative height in tab-delimited tree map testing.");
      }
      catch (ModelException oErr) {
        //Make sure that no trees were added
        assertEquals(0, oPop.mp_oTrees.size());
        //Make sure the error was right
        assertTrue(oErr.getMessage().indexOf("invalid tree height") > 0);
      }
      finally {
        new File(sFileName).delete();
      }

      /************************
       Error processing - bad format
       *************************/
      try {
        sFileName = writeTabTreeMap9();
        oPop.addTabDelimTreeMapFile(null, sFileName);
        fail("Didn't catch bad format in tab-delimited tree map testing.");
      }
      catch (ModelException oErr) {
        //Make sure that no trees were added
        assertEquals(0, oPop.mp_oTrees.size());
        //Make sure the error was right
        assertTrue(oErr.getMessage().indexOf("does not have all required columns") > 0);
      }
      finally {
        new File(sFileName).delete();
      }

      System.out.println("Tab-delimited tree map reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Tab-delimited tree map reading failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oErr) {
      fail("IO failure for tab-delimited tree map reading.  Message: " +
          oErr.getMessage());
    }
  }

  /**
   * Tests reading multiple Tree maps.
   */
  /*  public void testComboMapReading() {
      GUIManager oManager = null;
      try {
        oManager = new GUIManager(null);
        oManager.ClearCurrentData();

        //Set up and parse our test file
        //Create our parser
        XMLReader oParser = XMLReaderFactory.createXMLReader(
            "org.apache.xerces.parsers.SAXParser");
        //Create the handler that looks for setup data and register it
        DefaultHandler oHandler = new ParameterFileParser(oManager);
        oParser.setContentHandler(oHandler);
        //Parse the file
        String sFileName = WriteXMLTreeMap1();
        InputSource oToParse = new InputSource(sFileName);
        oParser.parse(oToParse);

        //Enable an absolute growth behavior so there will be suppression/release
        //data read in
   oManager.getGrowthBehaviors().getBehaviorByKey("absba").m_bIsEnabled = true;

        TreePopulation oPop = oManager.getTreePopulation();

        //Verify - total number of trees
        assertEquals(8, oPop.mp_oTrees.size());

        //Validate each Tree
        VerifyXMLTreeMap(oPop, 0);
        new File(sFileName).delete();

        //Now read in the regular Tree map
        sFileName = WriteV5TreeMap();
        oPop.AddOldTreeMapFile(sFileName, false);

        //Verify total number of trees
        assertEquals(18, oPop.mp_oTrees.size());

        //Verify each Tree
        VerifyV5TreeMap(oPop, 8);
        new File(sFileName).delete();

        //Fake rundata file
        oHandler = null;
        oHandler = new ParameterFileParser(oManager);
        oParser.setContentHandler(oHandler);
        sFileName = WriteXMLTreeMap2();
        oToParse = new InputSource(sFileName);
        oParser.parse(oToParse);

        assertEquals(26, oPop.mp_oTrees.size());
        VerifyXMLTreeMap(oPop, 18);

        new File(sFileName).delete();

        System.out.println("Multiple Tree map reading test succeeded.");
      }
      catch (ModelException oErr) {
        fail("XML Tree map reading failed with message " + oErr.getMessage());
      }
      catch (org.xml.sax.SAXException oE) {
        fail("Parse test could not parse parameter file - message: " +
             oE.getMessage());
      }
      catch (java.io.IOException oE) {
        fail("Caught IOException.  Message: " + oE.getMessage());
      }
    } */

  /**
   * Tests the Tree class.
   */
  public void testTreeClass() {    
    TreePopulation oPop = null;
    try {
      GUIManager oManager = new GUIManager(null);
      oPop = new TreePopulation(oManager);
      oPop.setSpeciesNames(new String[] {"ACSA", "ACRU", "QURU"});

      //Normal processing
      Tree oTree = new Tree(0, 2, 5, 4, 3, 1, oPop);
      assertEquals(2, oTree.m_iSpecies);
      assertEquals(0, oTree.m_iType);
      assertEquals(5, oTree.getNumberOfFloats());
      assertEquals(4, oTree.getNumberOfInts());
      assertEquals(3, oTree.getNumberOfChars());
      assertEquals(1, oTree.getNumberOfBools());

      int i;
      for (i = 0; i < oTree.getNumberOfFloats(); i++) {
        assertTrue(oTree.getFloat(i) == null);
      }

      for (i = 0; i < oTree.getNumberOfInts(); i++) {
        assertTrue(oTree.getInt(i) == null);
      }

      for (i = 0; i < oTree.getNumberOfChars(); i++) {
        assertTrue(oTree.getChar(i) == null);
      }

      for (i = 0; i < oTree.getNumberOfBools(); i++) {
        assertTrue(oTree.getBool(i) == null);
      }

      oTree.setValue(0, Boolean.valueOf(true));
      oTree.setValue(1, new String("test"));
      oTree.setValue(2, Integer.valueOf(1));
      oTree.setValue(3, Float.valueOf((float)2.3));

      assertTrue(oTree.getBool(0).equals(Boolean.valueOf(true)));
      assertTrue(oTree.getChar(1).equals("test"));
      assertTrue(oTree.getInt(2).equals(Integer.valueOf(1)));
      assertTrue(oTree.getFloat(3).equals(Float.valueOf((float)2.3)));

      System.out.println("Normal processing for Tree succeeded.");
    }
    catch (ModelException oErr) {
      fail("Tree testing failed with message " + oErr.getMessage());
    }

    //Exception processing

    try {
      //Bad species
      new Tree(2, -2, 5, 4, 3, 1, oPop);
      fail("Tree constructor failed to catch bad species.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad species
      new Tree(2, 12, 5, 4, 3, 1, oPop);
      fail("Tree constructor failed to catch bad species.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad type
      new Tree( -1, 2, 5, 4, 3, 1, oPop);
      fail("Tree constructor failed to catch bad type.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad type
      new Tree(11, 2, 5, 4, 3, 1, oPop);
      fail("Tree constructor failed to catch bad type.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad float
      Tree oTree = new Tree(1, 2, 5, 4, 3, 1, oPop);
      oTree.setValue( -1, Float.valueOf((float)2));
      fail("Tree constructor failed to catch bad float index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad float
      Tree oTree = new Tree(1, 2, 5, 6, 6, 6, oPop);
      oTree.setValue(5, Float.valueOf((float)2));
      fail("Tree constructor failed to catch bad float index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad int
      Tree oTree = new Tree(1, 2, 5, 4, 3, 1, oPop);
      oTree.setValue( -1, Integer.valueOf(2));
      fail("Tree constructor failed to catch bad int index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad int
      Tree oTree = new Tree(1, 2, 6, 5, 6, 6, oPop);
      oTree.setValue(5, Integer.valueOf(2));
      fail("Tree constructor failed to catch bad int index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad char
      Tree oTree = new Tree(1, 2, 5, 4, 3, 1, oPop);
      oTree.setValue( -1, new String("2"));
      fail("Tree constructor failed to catch bad char index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad char
      Tree oTree = new Tree(1, 2, 6, 6, 5, 6, oPop);
      oTree.setValue(5, new String("2"));
      fail("Tree constructor failed to catch bad char index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad bool
      Tree oTree = new Tree(1, 2, 5, 4, 3, 1, oPop);
      oTree.setValue( -1, Boolean.valueOf(true));
      fail("Tree constructor failed to catch bad bool index.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Bad bool
      Tree oTree = new Tree(1, 2, 6, 6, 6, 5, oPop);
      oTree.setValue(5, Boolean.valueOf(true));
      fail("Tree constructor failed to catch bad bool index.");
    }
    catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Tests reading XML Tree maps.
   */
  public void testXMLTreeMapReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeXMLTreeMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      TreePopulation oPop = oManager.getTreePopulation();
      oPop.validateData(oPop);      

      //Verify - total number of trees
      assertEquals(8, oPop.mp_oTrees.size());

      //Validate each Tree
      verifyXMLTreeMap(oPop, 0);

      new File(sFileName).delete();
      System.out.println(
          "XML Tree map reading normal processing test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML Tree map reading failed with message " + oErr.getMessage());
    }
    catch (org.xml.sax.SAXException oE) {
      fail("Parse test could not parse parameter file - message: " +
          oE.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    try {
      //Exception processing
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
    }
    catch (ModelException oErr) {
      fail("XML Tree map reading failed with message " + oErr.getMessage());
    }

    try {
      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeXMLTreeMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
    }
    catch (ModelException oErr) {
      fail("XML Tree map reading failed with message " + oErr.getMessage());

    }
    catch (org.xml.sax.SAXException oE) {
      fail("Parse test could not parse parameter file - message: " +
          oE.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    try {
      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeErrorXMLTreeMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("Did not catch X value bigger than plot.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    try {
      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeErrorXMLTreeMap2();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("Did not catch negative X value.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    try {
      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeErrorXMLTreeMap3();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("Did not catch Y value bigger than plot.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    try {
      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeErrorXMLTreeMap4();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("Did not catch negative Y value.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    new File(sFileName).delete();
    System.out.println(
        "XML Tree map reading exception processing test succeeded.");

  }

  /**
   * Tests reading XML Tree maps.
   */
  /*public void testXMLTreeMapReadingFromOutput() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeNeutralFile();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      sFileName = writeXMLOutputTreeMap1();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      TreePopulation oPop = oManager.getTreePopulation();
      oPop.validateData(oPop);      

      //Verify - total number of trees
      assertEquals(8, oPop.mp_oTrees.size());

      //Validate each Tree
      verifyXMLTreeMap(oPop, 0);

      System.out.println(
          "XML Tree map reading normal processing test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML Tree map reading failed with message " + oErr.getMessage());
    }
    catch (org.xml.sax.SAXException oE) {
      fail("Parse test could not parse parameter file - message: " +
          oE.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
    
      new File(sFileName).delete();
    }
    System.out.println(
        "XML Tree map reading exception processing test succeeded.");

  }*/

  
  /**
   * Verifies the trees added by the XML Tree map.
   * @param oPop TreePopulation object containing the trees.
   * @param iStartIndex The index at which to start verifying the trees.
   */
  protected void verifyXMLTreeMap(TreePopulation oPop, int iStartIndex) throws ModelException {

    //Tree 1
    Tree oTree = oPop.mp_oTrees.get(iStartIndex);
    assertEquals(0, oTree.m_iSpecies);
    assertEquals(2, oTree.m_iType);
    assertTrue(4 <= oTree.getNumberOfFloats());
    assertTrue(3 <= oTree.getNumberOfInts());
    assertTrue(2 <= oTree.getNumberOfChars());
    assertTrue(2 <= oTree.getNumberOfBools());
    assertEquals(0.981445,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(41.499001,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(89.027000,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).
        floatValue(), 0.001);
    assertEquals(1.1,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "float 1")).
        floatValue(), 0.001);
    assertEquals(1, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 1")).
        intValue());
    assertEquals(2, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 2")).
        intValue());
    assertEquals(3, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 3")).
        intValue());
    assertEquals("char val 1", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 1")));
    assertEquals("char val 2", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 2")));
    assertEquals(true, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 1")).booleanValue());
    assertEquals(false, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 2")).booleanValue());

    //Tree 2
    oTree = oPop.mp_oTrees.get(iStartIndex + 1);
    assertEquals(1, oTree.m_iSpecies);
    assertEquals(2, oTree.m_iType);
    assertTrue(4 <= oTree.getNumberOfFloats());
    assertTrue(3 <= oTree.getNumberOfInts());
    assertTrue(2 <= oTree.getNumberOfChars());
    assertTrue(2 <= oTree.getNumberOfBools());
    assertEquals(3.149410,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(11.313500,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(89.988998,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).
        floatValue(), 0.001);
    assertEquals(2.1,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "float 1")).
        floatValue(), 0.001);
    assertEquals(4, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 1")).
        intValue());
    assertEquals(5, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 2")).
        intValue());
    assertEquals(6, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 3")).
        intValue());
    assertEquals("char val 2.1", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 1")));
    assertEquals("char val 2.2", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 2")));
    assertEquals(true, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 1")).booleanValue());
    assertEquals(false, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 2")).booleanValue());

    //Tree 3
    oTree = oPop.mp_oTrees.get(iStartIndex + 2);
    assertEquals(2, oTree.m_iSpecies);
    assertEquals(2, oTree.m_iType);
    assertTrue(4 <= oTree.getNumberOfFloats());
    assertTrue(3 <= oTree.getNumberOfInts());
    assertTrue(2 <= oTree.getNumberOfChars());
    assertTrue(2 <= oTree.getNumberOfBools());
    assertEquals(10.981445,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(51.499001,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(9.027000,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Diam10")).
        floatValue(), 0.001);
    assertEquals(3.1,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "float 1")).
        floatValue(), 0.001);
    assertEquals(7, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 1")).
        intValue());
    assertEquals(8, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 2")).
        intValue());
    assertEquals(9, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 3")).
        intValue());
    assertEquals("char val 3.1", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 1")));
    assertEquals("char val 3.2", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 2")));
    assertEquals(false, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 1")).booleanValue());
    assertEquals(true, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 2")).booleanValue());

    //Tree 4
    oTree = oPop.mp_oTrees.get(iStartIndex + 3);
    assertEquals(1, oTree.m_iSpecies);
    assertEquals(1, oTree.m_iType);
    assertTrue(3 <= oTree.getNumberOfFloats());
    assertTrue(1 <= oTree.getNumberOfInts());
    assertTrue(1 <= oTree.getNumberOfChars());
    assertTrue(1 <= oTree.getNumberOfBools());
    assertEquals(1.923830,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(55.781200,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(43.942001,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Height")).floatValue(), 0.001);
    assertEquals(10, oTree.getInt(oPop.getCodeForDataMember(oPop.mp_sTreeIntDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "int 1")).intValue());
    assertEquals("char val 4.1", oTree.getChar(oPop.getCodeForDataMember(oPop.mp_sTreeCharDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "char 1")));
    assertEquals(false, oTree.getBool(oPop.getCodeForDataMember(oPop.mp_sTreeBoolDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "bool 1")).booleanValue());

    //Tree 5
    oTree = oPop.mp_oTrees.get(iStartIndex + 4);
    assertEquals(2, oTree.m_iSpecies);
    assertEquals(1, oTree.m_iType);
    assertTrue(3 <= oTree.getNumberOfFloats());
    assertTrue(0 <= oTree.getNumberOfInts());
    assertTrue(0 <= oTree.getNumberOfChars());
    assertTrue(0 <= oTree.getNumberOfBools());
    assertEquals(0.810547,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(54.584999,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(81.760002,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Height")).
        floatValue(), 0.001);

    //Tree 6
    oTree = oPop.mp_oTrees.get(iStartIndex + 5);
    assertEquals(2, oTree.m_iSpecies);
    assertEquals(3, oTree.m_iType);
    assertTrue(3 <= oTree.getNumberOfFloats());
    assertTrue(0 <= oTree.getNumberOfInts());
    assertTrue(0 <= oTree.getNumberOfChars());
    assertTrue(0 <= oTree.getNumberOfBools());
    assertEquals(7.470700,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(93.603500,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(41.171001,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).
        floatValue(), 0.001);

    //Tree 7
    oTree = oPop.mp_oTrees.get(iStartIndex + 6);
    assertEquals(1, oTree.m_iSpecies);
    assertEquals(3, oTree.m_iType);
    assertTrue(3 <= oTree.getNumberOfFloats());
    assertTrue(0 <= oTree.getNumberOfInts());
    assertTrue(0 <= oTree.getNumberOfChars());
    assertTrue(0 <= oTree.getNumberOfBools());
    assertEquals(6.528320,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(102.182999,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(43.853001,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).
        floatValue(), 0.001);

    //Tree 8
    oTree = oPop.mp_oTrees.get(iStartIndex + 7);
    assertEquals(0, oTree.m_iSpecies);
    assertEquals(3, oTree.m_iType);
    assertTrue(3 <= oTree.getNumberOfFloats());
    assertTrue(0 <= oTree.getNumberOfInts());
    assertTrue(0 <= oTree.getNumberOfChars());
    assertTrue(0 <= oTree.getNumberOfBools());
    assertEquals(0.732422,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "X")).
        floatValue(), 0.001);
    assertEquals(99.418900,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "Y")).
        floatValue(), 0.001);
    assertEquals(83.000999,
        oTree.getFloat(oPop.getCodeForDataMember(oPop.mp_sTreeFloatDataMembers.get(oTree.m_iSpecies).get(oTree.m_iType), "DBH")).
        floatValue(), 0.001);

  }

  /**
   * Writes a map to create trees.  This file has no trees.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap1() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map to create trees.  This file has one tree.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap2() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tBeech\tAdult\t14.06372\t0\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map to create trees.  This file has several trees.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap3() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tRed Maple\tSeedling\t0.12\t1.2\n");
    oOut.write("4.01001\t10.553\tOak\tSapling\t8.50342\t0\n");
    oOut.write("7.47681\t29.0466\tBeech\tADULT\t33.36319\t5.05853\n");
    oOut.write("4.93164\t20.4529\tRed Maple\tSNAG\t13.21426\t0\n");
    oOut.write("9.11865\t33.8013\tOak\tsapling\t2.77977\t5.12386\n");
    oOut.write("9.31396\t41.3818\tBeech\taduLT\t39.6077\t0\n");
    oOut.write("3.92456\t45.7397\tRed Maple\tsnag\t7.78549\t8.53078\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map to create trees.  This file has several trees, including
   * some outside the plot.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap4() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("9.33838\t99.5117\tRed Maple\tAdult\t28.63892\t0\n");
    oOut.write("8.80127\t94.9768\tOak\tAdult\t29.42276\t0\n");
    oOut.write("104.865\t5.16968\tBeech\tAdult\t25.93903\t0\n");
    oOut.write("4.32129\t159.867\tRed Maple\tAdult\t14.06372\t0\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map with an error.  This file has an unrecognized species.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap5() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tRed Maple\tSeedling\t0.12\t1.2\n");
    oOut.write("4.01001\t10.553\tOak\tSapling\t8.50342\t0\n");
    oOut.write("7.47681\t29.0466\tCherry\tADULT\t33.36319\t5.05853\n");
    oOut.write("4.93164\t20.4529\tRed Maple\tSNAG\t13.21426\t0\n");
    oOut.write("9.11865\t33.8013\tOak\tsapling\t2.77977\t5.12386\n");
    oOut.write("9.31396\t41.3818\tBeech\taduLT\t39.6077\t0\n");
    oOut.write("3.92456\t45.7397\tRed Maple\tsnag\t7.78549\t8.53078\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map with an error.  This file has an unrecognized life history
   * stage.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap6() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tRed Maple\tSeedling\t0.12\t1.2\n");
    oOut.write("4.01001\t10.553\tOak\tSapling\t8.50342\t0\n");
    oOut.write("7.47681\t29.0466\tBeech\tCoffee_Table\t33.36319\t5.05853\n");
    oOut.write("4.93164\t20.4529\tRed Maple\tSNAG\t13.21426\t0\n");
    oOut.write("9.11865\t33.8013\tOak\tsapling\t2.77977\t5.12386\n");
    oOut.write("9.31396\t41.3818\tBeech\taduLT\t39.6077\t0\n");
    oOut.write("3.92456\t45.7397\tRed Maple\tsnag\t7.78549\t8.53078\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map with an error.  This file has a negative diameter.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap7() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tRed Maple\tSeedling\t0.12\t1.2\n");
    oOut.write("4.01001\t10.553\tOak\tSapling\t8.50342\t0\n");
    oOut.write("7.47681\t29.0466\tBeech\tADULT\t33.36319\t5.05853\n");
    oOut.write("4.93164\t20.4529\tRed Maple\tSNAG\t-13.21426\t0\n");
    oOut.write("9.11865\t33.8013\tOak\tsapling\t2.77977\t5.12386\n");
    oOut.write("9.31396\t41.3818\tBeech\taduLT\t39.6077\t0\n");
    oOut.write("3.92456\t45.7397\tRed Maple\tsnag\t7.78549\t8.53078\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map with an error.  This file has a negative height.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap8() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("X\tY\tSpecies\tType\tDiam\tHeight\n");
    oOut.write("5.58472\t9.69238\tRed Maple\tSeedling\t0.12\t1.2\n");
    oOut.write("4.01001\t10.553\tOak\tSapling\t8.50342\t0\n");
    oOut.write("7.47681\t29.0466\tBeech\tADULT\t33.36319\t5.05853\n");
    oOut.write("4.93164\t20.4529\tRed Maple\tSNAG\t13.21426\t0\n");
    oOut.write("9.11865\t33.8013\tOak\tsapling\t2.77977\t-5.12386\n");
    oOut.write("9.31396\t41.3818\tBeech\taduLT\t39.6077\t0\n");
    oOut.write("3.92456\t45.7397\tRed Maple\tsnag\t7.78549\t8.53078\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a map with an error.  This file has an invalid format.
   * @return Name of file.
   * @throws IOException if there is an IO problem.
   */
  private String writeTabTreeMap9() throws java.io.IOException {
    String sFileName = "\\testfile.txt";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("200 200 9 0\n");
    oOut.write("0 0 C:\\SORTIE\\Documentation\\SORTIEBC.par\n");
    oOut.write("5.58472\t9.69238\t5.12222\t6.87094\t4\t0\t0\t0\n");
    oOut.write("4.01001\t10.553\t8.50342\t11.0904\t7\t0\t0\t0\n");
    oOut.write("7.47681\t29.0466\t3.36319\t5.05853\t4\t0\t0\t0\n");
    oOut.write("4.93164\t20.4529\t3.21426\t4.84396\t0\t0\t0\t0\n");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a Tree map file.  There are all four kinds of data members.  This
   * is actually a parameter file with a Tree map in it. It also contains 
   * incompletely defined trees (don't have X, Y, and diameter), and those
   * trees should be eliminated.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLTreeMap1() throws ModelException {
    try {
      String sFileName = "\\testtreemap1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot - so we can test reading an old tree map
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");

      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("<tr_species speciesName=\"QURU\"/>");
      oOut.write("<tr_species speciesName=\"PRSE\"/>");
      oOut.write("<tr_species speciesName=\"PIST\"/>");
      oOut.write("<tr_species speciesName=\"TSCA\"/>");
      oOut.write("<tr_species speciesName=\"FRAM\"/>");
      oOut.write("<tr_species speciesName=\"BEAL\"/>");
      oOut.write("<tr_species speciesName=\"FAGR\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ACSA\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"ACRU\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"QURU\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"PRSE\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"PIST\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"TSCA\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"FRAM\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"BEAL\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"FAGR\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");

      oOut.write("<tr_treemap>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">1</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">0</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">1</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">3</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">0</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">1</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">0</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">1</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">3</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">3</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Diam10\">4</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">3</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Diam10\">3</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">0</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"3\">0.981445</fl>"); //X
      oOut.write("<fl c=\"2\">41.499001</fl>"); //Y
      oOut.write("<fl c=\"0\">89.027000</fl>"); //DBH
      oOut.write("<fl c=\"1\">1.1</fl>"); //float 1
      oOut.write("<int c=\"0\">1</int>"); //int 1
      oOut.write("<int c=\"1\">2</int>"); //int 2
      oOut.write("<int c=\"3\">3</int>"); //int 3
      oOut.write("<ch c=\"0\">char val 1</ch>"); //char 1
      oOut.write("<ch c=\"1\">char val 2</ch>"); //char 2
      oOut.write("<bl c=\"0\">true</bl>"); //bool 1
      oOut.write("<bl c=\"1\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 2
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">3.149410</fl>"); //X
      oOut.write("<fl c=\"1\">11.313500</fl>"); //Y
      oOut.write("<fl c=\"2\">89.988998</fl>"); //DBH
      oOut.write("<fl c=\"3\">2.1</fl>"); //float 1
      oOut.write("<int c=\"3\">4</int>"); //int 1
      oOut.write("<int c=\"2\">5</int>"); //int 2
      oOut.write("<int c=\"1\">6</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 2.1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 2.2</ch>"); //char 2
      oOut.write("<bl c=\"1\">true</bl>"); //bool 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Sapling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">37.149410</fl>"); //X
      oOut.write("<fl c=\"1\">11.313500</fl>"); //Y
      oOut.write("</tree>");

      //Sapling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">39.149410</fl>"); //X
      oOut.write("<fl c=\"2\">89.988998</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 3
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"0\">10.981445</fl>"); //X
      oOut.write("<fl c=\"1\">51.499001</fl>"); //Y
      oOut.write("<fl c=\"4\">9.027000</fl>"); //Diam10
      oOut.write("<fl c=\"3\">3.1</fl>"); //float 1
      oOut.write("<int c=\"3\">7</int>"); //int 1
      oOut.write("<int c=\"2\">8</int>"); //int 2
      oOut.write("<int c=\"1\">9</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 3.1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 3.2</ch>"); //char 2
      oOut.write("<bl c=\"1\">false</bl>"); //bool 1
      oOut.write("<bl c=\"0\">true</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 4
      oOut.write("<tree sp=\"1\" tp=\"1\">");
      oOut.write("<fl c=\"0\">1.923830</fl>"); //X
      oOut.write("<fl c=\"1\">55.781200</fl>"); //Y
      oOut.write("<fl c=\"2\">43.942001</fl>"); //Height
      oOut.write("<int c=\"0\">10</int>"); //int 1
      oOut.write("<ch c=\"0\">char val 4.1</ch>"); //char 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 1
      oOut.write("</tree>");

      //Seedling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"2\" tp=\"1\">");
      oOut.write("<fl c=\"0\">0.810547</fl>"); //X
      oOut.write("<fl c=\"1\">54.584999</fl>"); //Y
      oOut.write("</tree>");

      //Tree 5
      oOut.write("<tree sp=\"2\" tp=\"1\">");
      oOut.write("<fl c=\"0\">0.810547</fl>"); //X
      oOut.write("<fl c=\"1\">54.584999</fl>"); //Y
      oOut.write("<fl c=\"2\">81.760002</fl>"); //Height
      oOut.write("</tree>");

      //Tree 6
      oOut.write("<tree sp=\"2\" tp=\"3\">");
      oOut.write("<fl c=\"0\">7.470700</fl>"); //X
      oOut.write("<fl c=\"1\">93.603500</fl>"); //Y
      oOut.write("<fl c=\"2\">41.171001</fl>"); //DBH
      oOut.write("</tree>");

      //Adult with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"2\" tp=\"3\">");
      oOut.write("<fl c=\"0\">79.470700</fl>"); //X
      oOut.write("<fl c=\"1\">93.603500</fl>"); //Y
      oOut.write("</tree>");

      //Tree 7
      oOut.write("<tree sp=\"1\" tp=\"3\">");
      oOut.write("<fl c=\"0\">6.528320</fl>"); //X
      oOut.write("<fl c=\"1\">102.182999</fl>"); //Y
      oOut.write("<fl c=\"2\">43.853001</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 8
      oOut.write("<tree sp=\"0\" tp=\"3\">");
      oOut.write("<fl c=\"0\">0.732422</fl>"); //X
      oOut.write("<fl c=\"1\">99.418900</fl>"); //Y
      oOut.write("<fl c=\"2\">83.000999</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</trees>");

      oOut.write("<allometry>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ACSA\">39.48</tr_chVal>");
      oOut.write("<tr_chVal species=\"ACRU\">39.54</tr_chVal>");
      oOut.write("<tr_chVal species=\"QURU\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"PRSE\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"PIST\">45.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"TSCA\">40.0</tr_chVal>");
      oOut.write("<tr_chVal species=\"FRAM\">39.14</tr_chVal>");
      oOut.write("<tr_chVal species=\"BEAL\">39.47</tr_chVal>");
      oOut.write("<tr_chVal species=\"FAGR\">33.18</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ACSA\">0.0549</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"ACRU\">0.0614</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"QURU\">0.0242</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"PRSE\">0.0251</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"PIST\">0.0239</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"TSCA\">0.0303</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"FRAM\">0.0328</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"BEAL\">0.0247</tr_sacrVal>");
      oOut.write("<tr_sacrVal species=\"FAGR\">0.0484</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ACSA\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"ACRU\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"QURU\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"PRSE\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"PIST\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"TSCA\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"FRAM\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"BEAL\">1.0</tr_screVal>");
      oOut.write("<tr_screVal species=\"FAGR\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ACSA\">0.8008</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"ACRU\">0.5944</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"QURU\">0.7059</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"PRSE\">0.7776</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"PIST\">0.6933</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"TSCA\">0.8014</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"FRAM\">0.7992</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"BEAL\">0.7926</tr_cdtdVal>");
      oOut.write("<tr_cdtdVal species=\"FAGR\">0.7803</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ACSA\">0.389</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"ACRU\">0.368</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"QURU\">0.464</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"PRSE\">0.454</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"PIST\">0.405</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"TSCA\">0.201</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"FRAM\">0.301</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"BEAL\">0.42</tr_sachVal>");
      oOut.write("<tr_sachVal species=\"FAGR\">0.315</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ACSA\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"ACRU\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"QURU\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"PRSE\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"PIST\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"TSCA\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"FRAM\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"BEAL\">1.0</tr_scheVal>");
      oOut.write("<tr_scheVal species=\"FAGR\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ACSA\">0.03418</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"ACRU\">0.0269</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"QURU\">0.02871</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"PRSE\">0.02815</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"PIST\">0.02871</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"TSCA\">0.03224</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"FRAM\">0.04796</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"BEAL\">0.04681</tr_sohdVal>");
      oOut.write("<tr_sohdVal species=\"FAGR\">0.04101</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ACSA\">0.0299</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"ACRU\">0.0241</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"QURU\">0.0263</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"PRSE\">0.0264</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"PIST\">0.0264</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"TSCA\">0.0333</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"FRAM\">0.0352</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"BEAL\">0.0347</tr_soahVal>");
      oOut.write("<tr_soahVal species=\"FAGR\">0.0454</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("</allometry>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>DensitySelfThinning</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<DensitySelfThinning1>");
      oOut.write("<mo_selfThinRadius>");
      oOut.write("<mo_strVal species=\"ACRU\">9</mo_strVal>");
      oOut.write("</mo_selfThinRadius>");
      oOut.write("<mo_minDensityForMort>");
      oOut.write("<mo_mdfmVal species=\"ACRU\">0</mo_mdfmVal>");
      oOut.write("</mo_minDensityForMort>");
      oOut.write("<mo_selfThinAsymptote>");
      oOut.write("<mo_staVal species=\"ACRU\">0.1019</mo_staVal>");
      oOut.write("</mo_selfThinAsymptote>");
      oOut.write("<mo_selfThinDiamEffect>");
      oOut.write("<mo_stdieVal species=\"ACRU\">0.5391</mo_stdieVal>");
      oOut.write("</mo_selfThinDiamEffect>");
      oOut.write("<mo_selfThinDensityEffect>");
      oOut.write("<mo_stdeeVal species=\"ACRU\">0.00000877</mo_stdeeVal>");
      oOut.write("</mo_selfThinDensityEffect>");
      oOut.write("</DensitySelfThinning1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  
  /**
   * Writes a Tree map file.  There are all four kinds of data members.  This
   * is actually a parameter file with a Tree map in it. It also contains 
   * incompletely defined trees (don't have X, Y, and diameter), and those
   * trees should be eliminated.
   * @return The file name.
   * @throws ModelException
   */
  /*private String writeXMLOutputTreeMap1() throws ModelException {
    try {
      String sFileName = "\\testtreemap1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"07010701\">");
      oOut.write("<rt_timestep>10</rt_timestep>");
      oOut.write("<tr_treemap>");
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("<tm_species speciesName=\"BEAL\"/>");
      oOut.write("<tm_species speciesName=\"FAGR\"/>");
      oOut.write("<tm_species speciesName=\"TSCA\"/>");
      oOut.write("<tm_species speciesName=\"FRAM\"/>");
      oOut.write("<tm_species speciesName=\"PIST\"/>");
      oOut.write("<tm_species speciesName=\"PRSE\"/>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("</tm_speciesList>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">1</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">0</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">1</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">3</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">0</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">1</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">0</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">1</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">3</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">3</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Diam10\">4</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">3</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Diam10\">3</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">0</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"3\">0.981445</fl>"); //X
      oOut.write("<fl c=\"2\">41.499001</fl>"); //Y
      oOut.write("<fl c=\"0\">89.027000</fl>"); //DBH
      oOut.write("<fl c=\"1\">1.1</fl>"); //float 1
      oOut.write("<int c=\"0\">1</int>"); //int 1
      oOut.write("<int c=\"1\">2</int>"); //int 2
      oOut.write("<int c=\"3\">3</int>"); //int 3
      oOut.write("<ch c=\"0\">char val 1</ch>"); //char 1
      oOut.write("<ch c=\"1\">char val 2</ch>"); //char 2
      oOut.write("<bl c=\"0\">true</bl>"); //bool 1
      oOut.write("<bl c=\"1\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 2
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">3.149410</fl>"); //X
      oOut.write("<fl c=\"1\">11.313500</fl>"); //Y
      oOut.write("<fl c=\"2\">89.988998</fl>"); //DBH
      oOut.write("<fl c=\"3\">2.1</fl>"); //float 1
      oOut.write("<int c=\"3\">4</int>"); //int 1
      oOut.write("<int c=\"2\">5</int>"); //int 2
      oOut.write("<int c=\"1\">6</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 2.1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 2.2</ch>"); //char 2
      oOut.write("<bl c=\"1\">true</bl>"); //bool 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Sapling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">37.149410</fl>"); //X
      oOut.write("<fl c=\"1\">11.313500</fl>"); //Y
      oOut.write("</tree>");

      //Sapling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"0\">39.149410</fl>"); //X
      oOut.write("<fl c=\"2\">89.988998</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 3
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"0\">10.981445</fl>"); //X
      oOut.write("<fl c=\"1\">51.499001</fl>"); //Y
      oOut.write("<fl c=\"4\">9.027000</fl>"); //Diam10
      oOut.write("<fl c=\"3\">3.1</fl>"); //float 1
      oOut.write("<int c=\"3\">7</int>"); //int 1
      oOut.write("<int c=\"2\">8</int>"); //int 2
      oOut.write("<int c=\"1\">9</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 3.1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 3.2</ch>"); //char 2
      oOut.write("<bl c=\"1\">false</bl>"); //bool 1
      oOut.write("<bl c=\"0\">true</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 4
      oOut.write("<tree sp=\"1\" tp=\"1\">");
      oOut.write("<fl c=\"0\">1.923830</fl>"); //X
      oOut.write("<fl c=\"1\">55.781200</fl>"); //Y
      oOut.write("<fl c=\"2\">43.942001</fl>"); //Height
      oOut.write("<int c=\"0\">10</int>"); //int 1
      oOut.write("<ch c=\"0\">char val 4.1</ch>"); //char 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 1
      oOut.write("</tree>");

      //Seedling with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"2\" tp=\"1\">");
      oOut.write("<fl c=\"0\">0.810547</fl>"); //X
      oOut.write("<fl c=\"1\">54.584999</fl>"); //Y
      oOut.write("</tree>");

      //Tree 5
      oOut.write("<tree sp=\"2\" tp=\"1\">");
      oOut.write("<fl c=\"0\">0.810547</fl>"); //X
      oOut.write("<fl c=\"1\">54.584999</fl>"); //Y
      oOut.write("<fl c=\"2\">81.760002</fl>"); //Height
      oOut.write("</tree>");

      //Tree 6
      oOut.write("<tree sp=\"2\" tp=\"3\">");
      oOut.write("<fl c=\"0\">7.470700</fl>"); //X
      oOut.write("<fl c=\"1\">93.603500</fl>"); //Y
      oOut.write("<fl c=\"2\">41.171001</fl>"); //DBH
      oOut.write("</tree>");

      //Adult with incomplete definition - should be eliminated
      oOut.write("<tree sp=\"2\" tp=\"3\">");
      oOut.write("<fl c=\"0\">79.470700</fl>"); //X
      oOut.write("<fl c=\"1\">93.603500</fl>"); //Y
      oOut.write("</tree>");

      //Tree 7
      oOut.write("<tree sp=\"1\" tp=\"3\">");
      oOut.write("<fl c=\"0\">6.528320</fl>"); //X
      oOut.write("<fl c=\"1\">102.182999</fl>"); //Y
      oOut.write("<fl c=\"2\">43.853001</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 8
      oOut.write("<tree sp=\"0\" tp=\"3\">");
      oOut.write("<fl c=\"0\">0.732422</fl>"); //X
      oOut.write("<fl c=\"1\">99.418900</fl>"); //Y
      oOut.write("<fl c=\"2\">83.000999</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }*/

  /**
   * Writes a tree map file.  There are all four kinds of data members,
   * with different codes from those written in WriteXMLTreeMap1.  The
   * species list is also in a different order.  This
   * does not write a full parameter file, but a fake timestep file.
   * @return The file name.
   * @throws ModelException
   */
  /*private String writeXMLTreeMap2() throws ModelException {
    try {
      String sFileName = "\\testtreemap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      oOut.write("<tr_treemap>");

      //Species list
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("</tm_speciesList>");

      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">3</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">0</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">0</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">0</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">1</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">2</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">0</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">0</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"float 1\">1</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">2</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 2\">1</tm_intCode>");
      oOut.write("<tm_intCode label=\"int 3\">3</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">0</tm_charCode>");
      oOut.write("<tm_charCode label=\"char 2\">2</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">2</tm_boolCode>");
      oOut.write("<tm_boolCode label=\"bool 2\">3</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">2</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">0</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("<tm_intCodes>");
      oOut.write("<tm_intCode label=\"int 1\">1</tm_intCode>");
      oOut.write("</tm_intCodes>");
      oOut.write("<tm_charCodes>");
      oOut.write("<tm_charCode label=\"char 1\">1</tm_charCode>");
      oOut.write("</tm_charCodes>");
      oOut.write("<tm_boolCodes>");
      oOut.write("<tm_boolCode label=\"bool 1\">1</tm_boolCode>");
      oOut.write("</tm_boolCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"1\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"QURU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACRU\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"3\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"0\">0.981445</fl>"); //X
      oOut.write("<fl c=\"1\">41.499001</fl>"); //Y
      oOut.write("<fl c=\"2\">89.027000</fl>"); //DBH
      oOut.write("<fl c=\"3\">1.1</fl>"); //float 1
      oOut.write("<int c=\"2\">1</int>"); //int 1
      oOut.write("<int c=\"0\">2</int>"); //int 2
      oOut.write("<int c=\"1\">3</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 2</ch>"); //char 2
      oOut.write("<bl c=\"1\">true</bl>"); //bool 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 2
      oOut.write("<tree sp=\"1\" tp=\"2\">");
      oOut.write("<fl c=\"3\">3.149410</fl>"); //X
      oOut.write("<fl c=\"2\">11.313500</fl>"); //Y
      oOut.write("<fl c=\"1\">89.988998</fl>"); //DBH
      oOut.write("<fl c=\"0\">2.1</fl>"); //float 1
      oOut.write("<int c=\"0\">4</int>"); //int 1
      oOut.write("<int c=\"1\">5</int>"); //int 2
      oOut.write("<int c=\"2\">6</int>"); //int 3
      oOut.write("<ch c=\"1\">char val 2.1</ch>"); //char 1
      oOut.write("<ch c=\"0\">char val 2.2</ch>"); //char 2
      oOut.write("<bl c=\"1\">true</bl>"); //bool 1
      oOut.write("<bl c=\"0\">false</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 3
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"2\">10.981445</fl>"); //X
      oOut.write("<fl c=\"0\">51.499001</fl>"); //Y
      oOut.write("<fl c=\"3\">9.027000</fl>"); //DBH
      oOut.write("<fl c=\"1\">3.1</fl>"); //float 1
      oOut.write("<int c=\"2\">7</int>"); //int 1
      oOut.write("<int c=\"1\">8</int>"); //int 2
      oOut.write("<int c=\"3\">9</int>"); //int 3
      oOut.write("<ch c=\"0\">char val 3.1</ch>"); //char 1
      oOut.write("<ch c=\"2\">char val 3.2</ch>"); //char 2
      oOut.write("<bl c=\"2\">false</bl>"); //bool 1
      oOut.write("<bl c=\"3\">true</bl>"); //bool 2
      oOut.write("</tree>");

      //Tree 4
      oOut.write("<tree sp=\"1\" tp=\"1\">");
      oOut.write("<fl c=\"2\">1.923830</fl>"); //X
      oOut.write("<fl c=\"1\">55.781200</fl>"); //Y
      oOut.write("<fl c=\"0\">43.942001</fl>"); //Height
      oOut.write("<int c=\"1\">10</int>"); //int 1
      oOut.write("<ch c=\"1\">char val 4.1</ch>"); //char 1
      oOut.write("<bl c=\"1\">false</bl>"); //bool 1
      oOut.write("</tree>");

      //Tree 5
      oOut.write("<tree sp=\"0\" tp=\"1\">");
      oOut.write("<fl c=\"0\">0.810547</fl>"); //X
      oOut.write("<fl c=\"1\">54.584999</fl>"); //Y
      oOut.write("<fl c=\"2\">81.760002</fl>"); //Height
      oOut.write("</tree>");

      //Tree 6
      oOut.write("<tree sp=\"0\" tp=\"3\">");
      oOut.write("<fl c=\"0\">7.470700</fl>"); //X
      oOut.write("<fl c=\"1\">93.603500</fl>"); //Y
      oOut.write("<fl c=\"2\">41.171001</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 7
      oOut.write("<tree sp=\"1\" tp=\"3\">");
      oOut.write("<fl c=\"0\">6.528320</fl>"); //X
      oOut.write("<fl c=\"1\">102.182999</fl>"); //Y
      oOut.write("<fl c=\"2\">43.853001</fl>"); //DBH
      oOut.write("</tree>");

      //Tree 8
      oOut.write("<tree sp=\"2\" tp=\"3\">");
      oOut.write("<fl c=\"0\">0.732422</fl>"); //X
      oOut.write("<fl c=\"1\">99.418900</fl>"); //Y
      oOut.write("<fl c=\"2\">83.000999</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }*/

  /**
   * Writes a bad Tree map file. One of the X values is beyond the plot.
   * @return The file name.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLTreeMap1() throws ModelException {
    try {
      String sFileName = "\\testtreemap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      oOut.write("<tr_treemap>");

      //Species list
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("</tm_speciesList>");

      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"3\">200.981445</fl>"); //X
      oOut.write("<fl c=\"1\">41.499001</fl>"); //Y
      oOut.write("<fl c=\"2\">89.027000</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a bad tree map file. One of the X values is negative the plot.
   * @return The file name.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLTreeMap2() throws ModelException {
    try {
      String sFileName = "\\testtreemap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      oOut.write("<tr_treemap>");

      //Species list
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("</tm_speciesList>");

      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"3\">-0.981445</fl>"); //X
      oOut.write("<fl c=\"1\">41.499001</fl>"); //Y
      oOut.write("<fl c=\"2\">89.027000</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a bad Tree map file. One of the Y values is beyond the plot.
   * @return The file name.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLTreeMap3() throws ModelException {
    try {
      String sFileName = "\\testtreemap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      oOut.write("<tr_treemap>");

      //Species list
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("</tm_speciesList>");

      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"0\">0.981445</fl>"); //X
      oOut.write("<fl c=\"3\">241.499001</fl>"); //Y
      oOut.write("<fl c=\"2\">89.027000</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a bad tree map file. One of the Y values is beyond the plot.
   * @return The file name.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLTreeMap4() throws ModelException {
    try {
      String sFileName = "\\testtreemap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      oOut.write("<tr_treemap>");

      //Species list
      oOut.write("<tm_speciesList>");
      oOut.write("<tm_species speciesName=\"QURU\"/>");
      oOut.write("<tm_species speciesName=\"ACRU\"/>");
      oOut.write("<tm_species speciesName=\"ACSA\"/>");
      oOut.write("</tm_speciesList>");

      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">3</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");

      //Trees
      //One of each type/species combo with each data member
      //Tree 1
      oOut.write("<tree sp=\"2\" tp=\"2\">");
      oOut.write("<fl c=\"0\">0.981445</fl>"); //X
      oOut.write("<fl c=\"3\">-41.499001</fl>"); //Y
      oOut.write("<fl c=\"2\">89.027000</fl>"); //DBH
      oOut.write("</tree>");

      oOut.write("</tr_treemap>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a parameter file for validating tree map reading.
   * @return String Filename written.
   * @throws IOException if there is a problem writing to the file.
   */
  private String writeTreeMapParFile() throws java.io.IOException {

    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>150.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Red_Maple\" />");
    oOut.write("<tr_species speciesName=\"Oak\" />");
    oOut.write("<tr_species speciesName=\"Beech\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Red_Maple\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Oak\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Beech\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Red_Maple\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Oak\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Beech\">45</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Red_Maple\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Oak\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Beech\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Red_Maple\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Oak\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Beech\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Red_Maple\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Oak\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Beech\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Red_Maple\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Oak\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Beech\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Red_Maple\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Oak\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Beech\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Red_Maple\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Oak\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Beech\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Red_Maple\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Oak\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Beech\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Red_Maple\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Red_Maple\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Oak\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Oak\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Beech\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Beech\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">11.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">12.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">2.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">3.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"Seedling\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s2.5\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s5.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s30.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_initialDensity sizeClass=\"Seedling\">20.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">21.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">22.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_initialDensity sizeClass=\"Seedling\">23.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">24.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">25.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_initialDensity sizeClass=\"Seedling\">26.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">27.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s30.0\">28.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_seedlingHeightClass1>50.0</tr_seedlingHeightClass1>");
    oOut.write("<tr_seedlingHeightClass2>100.0</tr_seedlingHeightClass2>");
    oOut.write("<tr_seedlingHeight1Density>");
    oOut.write("<tr_sh1dVal species=\"Species_1\">12</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Species_2\">14</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Species_3\">16</tr_sh1dVal>");
    oOut.write("</tr_seedlingHeight1Density>");
    oOut.write("<tr_seedlingHeight2Density>");
    oOut.write("<tr_sh2dVal species=\"Species_1\">18</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Species_2\">20</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Species_3\">22</tr_sh2dVal>");
    oOut.write("</tr_seedlingHeight2Density>");
    oOut.write("<tr_seedlingHeight3Density>");
    oOut.write("<tr_sh3dVal species=\"Species_1\">24</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Species_2\">26</tr_sh3dVal>");
    oOut.write("<tr_sh3dVal species=\"Species_3\">28</tr_sh3dVal>");
    oOut.write("</tr_seedlingHeight3Density>");
    
    oOut.write("<tr_treemap>");
    oOut.write("<tm_treeSettings sp=\"Species_1\" tp=\"2\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Height\">4</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Diam10\">5</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tm_treeSettings sp=\"Species_1\" tp=\"3\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tm_treeSettings sp=\"Species_2\" tp=\"3\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("<tm_intCodes>");
    oOut.write("<tm_intCode label=\"Tree Age\">0</tm_intCode>");
    oOut.write("</tm_intCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tm_treeSettings sp=\"Species_3\" tp=\"3\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("<tm_intCodes>");
    oOut.write("<tm_intCode label=\"Tree Age\">0</tm_intCode>");
    oOut.write("</tm_intCodes>");
    oOut.write("</tm_treeSettings>");
    //10 trees - species 1, age behavior not applied
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">146.43</fl>");
    oOut.write("<fl c=\"1\">28.03</fl>");
    oOut.write("<fl c=\"3\">30</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">60.21</fl>");
    oOut.write("<fl c=\"1\">103.08</fl>");
    oOut.write("<fl c=\"3\">169.07</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"2\">");
    oOut.write("<fl c=\"0\">96.09</fl>");
    oOut.write("<fl c=\"1\">68.36</fl>");
    oOut.write("<fl c=\"3\">5.48</fl>");
    oOut.write("<fl c=\"4\">25.91</fl>");
    oOut.write("<fl c=\"5\">6.28</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">122.11</fl>");
    oOut.write("<fl c=\"1\">196.53</fl>");
    oOut.write("<fl c=\"3\">158.09</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">3.71</fl>");
    oOut.write("<fl c=\"1\">129.5</fl>");
    oOut.write("<fl c=\"3\">39.63</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">42.45</fl>");
    oOut.write("<fl c=\"1\">146.42</fl>");
    oOut.write("<fl c=\"3\">66.7</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">65.09</fl>");
    oOut.write("<fl c=\"1\">67.14</fl>");
    oOut.write("<fl c=\"3\">134.2</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">129.73</fl>");
    oOut.write("<fl c=\"1\">29.78</fl>");
    oOut.write("<fl c=\"3\">14.32</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"2\">");
    oOut.write("<fl c=\"0\">56.68</fl>");
    oOut.write("<fl c=\"1\">102.56</fl>");
    oOut.write("<fl c=\"3\">5.99</fl>");
    oOut.write("<fl c=\"4\">56.72</fl>");
    oOut.write("<fl c=\"5\">8.09</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"3\">");
    oOut.write("<fl c=\"0\">5.4</fl>");
    oOut.write("<fl c=\"1\">18.2</fl>");
    oOut.write("<fl c=\"3\">113.62</fl>");
    oOut.write("</tree>");
    //10 trees - species 2, no age specified
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">198.33</fl>");
    oOut.write("<fl c=\"1\">75.31</fl>");
    oOut.write("<fl c=\"3\">123.58</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">11.39</fl>");
    oOut.write("<fl c=\"1\">27.76</fl>");
    oOut.write("<fl c=\"3\">148.61</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">155.96</fl>");
    oOut.write("<fl c=\"1\">182.99</fl>");
    oOut.write("<fl c=\"3\">36.37</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">75.94</fl>");
    oOut.write("<fl c=\"1\">16.16</fl>");
    oOut.write("<fl c=\"3\">193.42</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">54.51</fl>");
    oOut.write("<fl c=\"1\">177.38</fl>");
    oOut.write("<fl c=\"3\">37.45</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">194.04</fl>");
    oOut.write("<fl c=\"1\">142.94</fl>");
    oOut.write("<fl c=\"3\">175.12</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">13.89</fl>");
    oOut.write("<fl c=\"1\">48.47</fl>");
    oOut.write("<fl c=\"3\">45.64</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">46.96</fl>");
    oOut.write("<fl c=\"1\">9.69</fl>");
    oOut.write("<fl c=\"3\">172.22</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">168.69</fl>");
    oOut.write("<fl c=\"1\">85.4</fl>");
    oOut.write("<fl c=\"3\">23.13</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"3\">");
    oOut.write("<fl c=\"0\">34.34</fl>");
    oOut.write("<fl c=\"1\">161.95</fl>");
    oOut.write("<fl c=\"3\">141.95</fl>");
    oOut.write("</tree>");
    //10 trees - species 3, age
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">155.79</fl>");
    oOut.write("<fl c=\"1\">193.57</fl>");
    oOut.write("<fl c=\"3\">34.39</fl>");
    oOut.write("<int c=\"0\">10</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">65.52</fl>");
    oOut.write("<fl c=\"1\">147.45</fl>");
    oOut.write("<fl c=\"3\">139.3</fl>");
    oOut.write("<int c=\"0\">11</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">146.23</fl>");
    oOut.write("<fl c=\"1\">145</fl>");
    oOut.write("<fl c=\"3\">184.3</fl>");
    oOut.write("<int c=\"0\">12</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">20.93</fl>");
    oOut.write("<fl c=\"1\">53.83</fl>");
    oOut.write("<fl c=\"3\">101.73</fl>");
    oOut.write("<int c=\"0\">13</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">30.76</fl>");
    oOut.write("<fl c=\"1\">15.95</fl>");
    oOut.write("<fl c=\"3\">74.42</fl>");
    oOut.write("<int c=\"0\">14</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">148.47</fl>");
    oOut.write("<fl c=\"1\">85.93</fl>");
    oOut.write("<fl c=\"3\">75.76</fl>");
    oOut.write("<int c=\"0\">15</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">114.69</fl>");
    oOut.write("<fl c=\"1\">160.07</fl>");
    oOut.write("<fl c=\"3\">40.55</fl>");
    oOut.write("<int c=\"0\">16</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">20.62</fl>");
    oOut.write("<fl c=\"1\">37.63</fl>");
    oOut.write("<fl c=\"3\">44.82</fl>");
    oOut.write("<int c=\"0\">17</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">6.27</fl>");
    oOut.write("<fl c=\"1\">102.24</fl>");
    oOut.write("<fl c=\"3\">136.63</fl>");
    oOut.write("<int c=\"0\">18</int>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"2\" tp=\"3\">");
    oOut.write("<fl c=\"0\">62.6</fl>");
    oOut.write("<fl c=\"1\">170.46</fl>");
    oOut.write("<fl c=\"3\">134.38</fl>");
    oOut.write("<int c=\"0\">19</int>");
    oOut.write("</tree>");
    oOut.write("</tr_treemap>");
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
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Tree Age Calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
