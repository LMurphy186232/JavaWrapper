package sortie.data.funcgroups;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Allometry;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**Tests the Allometry class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2005</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class TestAllometry
extends ModelTestCase {
  
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
      Allometry oAllom = oManager.getTreePopulation().getAllometry();
      
      assertEquals(39.48, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(0)).floatValue(), 0.001);
      assertEquals(39.54, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(1)).floatValue(), 0.001);
      assertEquals(45, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(2)).floatValue(), 0.001);
      assertEquals(39.48, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(3)).floatValue(), 0.001);
      assertEquals(39.54, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(4)).floatValue(), 0.001);
      assertEquals(45, ((Float)oAllom.mp_fMaxCanopyHeight.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.0549, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.0614, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.02418, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.0549, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.0614, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.02418, ((Float)oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.9, ((Float)oAllom.mp_fCrownRadExp.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.0, ((Float)oAllom.mp_fCrownRadExp.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.1, ((Float)oAllom.mp_fCrownRadExp.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.9, ((Float)oAllom.mp_fCrownRadExp.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.0, ((Float)oAllom.mp_fCrownRadExp.getValue().get(4)).floatValue(), 0.001);
      assertEquals(1.1, ((Float)oAllom.mp_fCrownRadExp.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(0)).floatValue(), 0.001);
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(1)).floatValue(), 0.001);
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(2)).floatValue(), 0.001);
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(3)).floatValue(), 0.001);
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(4)).floatValue(), 0.001);
      assertEquals(10.0, ((Float)oAllom.mp_fMaxCrownRad.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.8008, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.5944, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.7059, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.8008, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.5944, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.7059, ((Float)oAllom.mp_fDiam10ToDbhSlope.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-0.2, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.3, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(4)).floatValue(), 0.001);
      assertEquals(-3, ((Float)oAllom.mp_fDiam10ToDbhIntercept.getValue().get(5)).floatValue(), 0.001);

      assertEquals(0.389, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.368, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.34, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.389, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.368, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.34, ((Float)oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(1.0, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.5, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.9, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.0, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.5, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.9, ((Float)oAllom.mp_fCrownDepthExp.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.03418, ((Float)oAllom.mp_fSlopeOfHeightDiam10.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.2871, ((Float)oAllom.mp_fSlopeOfHeightDiam10.getValue().get(2)).floatValue(), 0.001);

      assertEquals(0.0299, ((Float)oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.0241, ((Float)oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.0263, ((Float)oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.0263, ((Float)oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(5)).floatValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(2, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(2, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSeedlingHDFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(2, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(3, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(2, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingHDFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(2, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultHDFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCRDFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatAdultCDHFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCRDFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(0)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(1)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(2)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(3)).getValue(), 0.001);
      assertEquals(1, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(4)).getValue(), 0.001);
      assertEquals(0, ((ModelEnum)oAllom.mp_iWhatSaplingCDHFunction.getValue().get(5)).getValue(), 0.001);

      assertEquals(0.96, ((Float)oAllom.mp_fAdultLinearSlope.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.3, ((Float)oAllom.mp_fAdultLinearSlope.getValue().get(5)).floatValue(), 0.001);

      assertEquals(0, ((Float)oAllom.mp_fAdultLinearIntercept.getValue().get(3)).floatValue(), 0.001);
      assertEquals(-0.9, ((Float)oAllom.mp_fAdultLinearIntercept.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.492, ((Float)oAllom.mp_fSaplingLinearSlope.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.0549, ((Float)oAllom.mp_fSaplingLinearSlope.getValue().get(3)).floatValue(), 0.001);

      assertEquals(1.2, ((Float)oAllom.mp_fSaplingLinearIntercept.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0, ((Float)oAllom.mp_fSaplingLinearIntercept.getValue().get(3)).floatValue(), 0.001);

      assertEquals(0.9629, ((Float)oAllom.mp_fSeedlingLinearSlope.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.228, ((Float)oAllom.mp_fSeedlingLinearSlope.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0, ((Float)oAllom.mp_fSeedlingLinearIntercept.getValue().get(3)).floatValue(), 0.001);
      assertEquals(-0.9, ((Float)oAllom.mp_fSeedlingLinearIntercept.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(2.1, ((Float)oAllom.mp_fAdultReverseLinearSlope.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(0.02418, ((Float)oAllom.mp_fAdultReverseLinearIntercept.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(1.5, ((Float)oAllom.mp_fSaplingReverseLinearSlope.getValue().get(4)).floatValue(), 0.001);
      assertEquals(1.1, ((Float)oAllom.mp_fSaplingReverseLinearSlope.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(0.761, ((Float)oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(4)).floatValue(), 0.001);
      assertEquals(-0.847, ((Float)oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(1.5, ((Float)oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.02, ((Float)oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(0.761, ((Float)oAllom.mp_fSeedlingReverseLinearIntercept.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.758, ((Float)oAllom.mp_fSeedlingReverseLinearIntercept.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(0.3, ((Float)oAllom.mp_fCRCrownRadIntercept.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.4, ((Float)oAllom.mp_fCRCrownRadIntercept.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.5, ((Float)oAllom.mp_fCRCrownRadIntercept.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.7, ((Float)oAllom.mp_fCRCrownRadIntercept.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(8, ((Float)oAllom.mp_fCRAsympCrownRad.getValue().get(1)).floatValue(), 0.001);
      assertEquals(7, ((Float)oAllom.mp_fCRAsympCrownRad.getValue().get(2)).floatValue(), 0.001);
      assertEquals(10, ((Float)oAllom.mp_fCRAsympCrownRad.getValue().get(3)).floatValue(), 0.001);
      assertEquals(9.2, ((Float)oAllom.mp_fCRAsympCrownRad.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.09, ((Float)oAllom.mp_fCRCrownRadShape1.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.1, ((Float)oAllom.mp_fCRCrownRadShape1.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.11, ((Float)oAllom.mp_fCRCrownRadShape1.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.2, ((Float)oAllom.mp_fCRCrownRadShape1.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(1.5, ((Float)oAllom.mp_fCRCrownRadShape2.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.4, ((Float)oAllom.mp_fCRCrownRadShape2.getValue().get(2)).floatValue(), 0.001);
      assertEquals(2.8, ((Float)oAllom.mp_fCRCrownRadShape2.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.9, ((Float)oAllom.mp_fCRCrownRadShape2.getValue().get(5)).floatValue(), 0.001);
      
      assertEquals(0.6, ((Float)oAllom.mp_fCRCrownHtIntercept.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.5, ((Float)oAllom.mp_fCRCrownHtIntercept.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.4, ((Float)oAllom.mp_fCRCrownHtIntercept.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.3, ((Float)oAllom.mp_fCRCrownHtIntercept.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(60, ((Float)oAllom.mp_fCRAsympCrownHt.getValue().get(1)).floatValue(), 0.001);
      assertEquals(12, ((Float)oAllom.mp_fCRAsympCrownHt.getValue().get(2)).floatValue(), 0.001);
      assertEquals(13, ((Float)oAllom.mp_fCRAsympCrownHt.getValue().get(3)).floatValue(), 0.001);
      assertEquals(14, ((Float)oAllom.mp_fCRAsympCrownHt.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(0.3, ((Float)oAllom.mp_fCRCrownHtShape1.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.1, ((Float)oAllom.mp_fCRCrownHtShape1.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.12, ((Float)oAllom.mp_fCRCrownHtShape1.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.16, ((Float)oAllom.mp_fCRCrownHtShape1.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(3.2, ((Float)oAllom.mp_fCRCrownHtShape2.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.1, ((Float)oAllom.mp_fCRCrownHtShape2.getValue().get(2)).floatValue(), 0.001);
      assertEquals(2.9, ((Float)oAllom.mp_fCRCrownHtShape2.getValue().get(3)).floatValue(), 0.001);
      assertEquals(3.5, ((Float)oAllom.mp_fCRCrownHtShape2.getValue().get(4)).floatValue(), 0.001);
      
      assertEquals(1.7019, ((Float)oAllom.mp_fPowerA.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.75, ((Float)oAllom.mp_fPowerB.getValue().get(3)).floatValue(), 0.001);
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
   * Test parameter file reading for allometry
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    ModelData oData;
    ModelVector oVector;
    int i, iIndex = 0; 
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = WriteXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      Allometry oAllom = oManager.getTreePopulation().getAllometry();

      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(0)).floatValue(), 45, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(1)).floatValue(), 39.48, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(0)).floatValue(), 0.02418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(1)).floatValue(), 0.0549, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(0)).floatValue(), 1.1, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(1)).floatValue(), 0.9, 0.001);
      
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(0)).floatValue(), 22, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(1)).floatValue(), 33, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(0)).floatValue(), 0.7059, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(1)).floatValue(), 0.8008, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(0)).floatValue(), 0.0, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(1)).floatValue(), 1.3, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(0)).floatValue(), 0.34, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(1)).floatValue(), 0.389, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(0)).floatValue(), 0.9, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(1)).floatValue(), 1.0, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(0)).floatValue(), 0.03418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(1)).floatValue(), 0.2871, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(0)).floatValue(), 0.0263, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(1)).floatValue(), 0.0163, 0.001);

      ModelEnum oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 2);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(((Float) oAllom.mp_fAdultLinearSlope.getValue().get(0)).floatValue(), 0.96, 0.001);
      assertEquals(((Float) oAllom.mp_fAdultLinearSlope.getValue().get(1)).floatValue(), 1.3, 0.001);

      assertEquals(((Float) oAllom.mp_fAdultLinearIntercept.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oAllom.mp_fAdultLinearIntercept.getValue().get(1)).floatValue(), -0.9, 0.001);

      assertEquals(((Float) oAllom.mp_fSaplingLinearSlope.getValue().get(0)).floatValue(), 0.492, 0.001);
      assertEquals(((Float) oAllom.mp_fSaplingLinearSlope.getValue().get(1)).floatValue(), 0.0549, 0.001);

      assertEquals(((Float) oAllom.mp_fSaplingLinearIntercept.getValue().get(0)).floatValue(), 1.2, 0.001);
      assertEquals(((Float) oAllom.mp_fSaplingLinearIntercept.getValue().get(1)).floatValue(), 0, 0.001);

      assertEquals(((Float) oAllom.mp_fSeedlingLinearSlope.getValue().get(0)).floatValue(), 0.9629, 0.001);
      assertEquals(((Float) oAllom.mp_fSeedlingLinearSlope.getValue().get(1)).floatValue(), 1.228, 0.001);

      assertEquals(((Float) oAllom.mp_fSeedlingLinearIntercept.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oAllom.mp_fSeedlingLinearIntercept.getValue().get(1)).floatValue(), -0.9, 0.001);

      assertEquals(((Float) oAllom.mp_fAdultReverseLinearSlope.getValue().get(0)).floatValue(), 2.1, 0.001);
      assertEquals(((Float) oAllom.mp_fAdultReverseLinearSlope.getValue().get(1)).floatValue(), 1.9, 0.001);

      assertEquals(((Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().get(0)).floatValue(), 0.02418, 0.001);
      assertEquals(((Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().get(1)).floatValue(), 0.034, 0.001);

      assertEquals(((Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().get(0)).floatValue(), 1.5, 0.001);
      assertEquals(((Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().get(1)).floatValue(), 1.1, 0.001);

      assertEquals(((Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(0)).floatValue(), 0.761, 0.001);
      assertEquals(((Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(1)).floatValue(), -0.847, 0.001);

      assertEquals(((Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(0)).floatValue(), 1.5, 0.001);
      assertEquals(((Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(1)).floatValue(), 0.02, 0.001);

      assertEquals(((Float) oAllom.mp_fSeedlingReverseLinearIntercept.getValue().get(0)).floatValue(), 0.761, 0.001);
      assertEquals(((Float) oAllom.mp_fSeedlingReverseLinearIntercept.getValue().get(1)).floatValue(), 0.758, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownRadIntercept.getValue().get(0)).floatValue(), 0.3, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownRadIntercept.getValue().get(1)).floatValue(), 0.4, 0.001);

      assertEquals(((Float) oAllom.mp_fCRAsympCrownRad.getValue().get(0)).floatValue(), 8, 0.001);
      assertEquals(((Float) oAllom.mp_fCRAsympCrownRad.getValue().get(1)).floatValue(), 7, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownRadShape1.getValue().get(0)).floatValue(), 0.09, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownRadShape1.getValue().get(1)).floatValue(), 0.1, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownRadShape2.getValue().get(0)).floatValue(), 1.5, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownRadShape2.getValue().get(1)).floatValue(), 2.4, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownHtIntercept.getValue().get(0)).floatValue(), 0.6, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownHtIntercept.getValue().get(1)).floatValue(), 0.5, 0.001);

      assertEquals(((Float) oAllom.mp_fCRAsympCrownHt.getValue().get(0)).floatValue(), 60, 0.001);
      assertEquals(((Float) oAllom.mp_fCRAsympCrownHt.getValue().get(1)).floatValue(), 12, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownHtShape1.getValue().get(0)).floatValue(), 0.3, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownHtShape1.getValue().get(1)).floatValue(), 0.1, 0.001);

      assertEquals(((Float) oAllom.mp_fCRCrownHtShape2.getValue().get(0)).floatValue(), 3.2, 0.001);
      assertEquals(((Float) oAllom.mp_fCRCrownHtShape2.getValue().get(1)).floatValue(), 4.1, 0.001);

      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(), (float) 0.6640108, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(), (float) 0.71, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(), (float) 0.00442797, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(), (float) 0.12, 0.01);

      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().get(0)).floatValue(), 3.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().get(0)).floatValue(), 2.17031683, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().get(1)).floatValue(), 2.81, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().get(0)).floatValue(), 0.69994199, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().get(1)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().get(1)).floatValue(), -0.13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().get(0)).floatValue(), 0.00163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().get(1)).floatValue(), 0.000126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().get(1)).floatValue(), 12, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().get(0)).floatValue(), 0.163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().get(1)).floatValue(), 0.126, 0.001);

      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(), (float) 0.83, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(), (float) 0.33, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(), (float) 0.54, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(), (float) 0.27, 0.01);

      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().get(0)).floatValue(), 65.67, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().get(1)).floatValue(), 9.52, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().get(0)).floatValue(), 1.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().get(1)).floatValue(), 1.531, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().get(0)).floatValue(), 0.698, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().get(1)).floatValue(), 0.457, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().get(0)).floatValue(), -0.0163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().get(1)).floatValue(), -0.0126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().get(0)).floatValue(), 0.0034, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().get(1)).floatValue(), 0.00526, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().get(0)).floatValue(), 11, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().get(1)).floatValue(), 13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().get(0)).floatValue(), 0.042, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().get(1)).floatValue(), 0.034, 0.001);
    }
    catch (ModelException oErr) {
      fail("Allometry parameter file reading failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Test setup when species have changed.
   */
  public void testDoSetup() {
    GUIManager oManager = null;
    String sFileName = null;
    ModelData oData;
    ModelVector oVector;
    int i, iIndex = 0; 
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = WriteXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Now change the species by adding another
      String[] sNewSpecies = new String[] {
          "Species 1",
          "Species 2",
          "Species 3"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      Allometry oAllom = oManager.getTreePopulation().getAllometry();

      //Verify that all the enums have enums where they're supposed to
      assertEquals(3, oAllom.mp_iWhatAdultHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSeedlingHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatAdultCRDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingCRDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatAdultCDHFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingCDHFunction.getValue().size());

      for (i = 0; i < 3; i++) {
        assertTrue(null != oAllom.mp_iWhatAdultHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSeedlingHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatAdultCRDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingCRDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatAdultCDHFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingCDHFunction.getValue().get(i));
      }

      //Verify that the first two species values are still good
      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(0)).floatValue(), 45, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(1)).floatValue(), 39.48, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(0)).floatValue(), 0.02418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(1)).floatValue(), 0.0549, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(0)).floatValue(), 1.1, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(1)).floatValue(), 0.9, 0.001);
      
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(0)).floatValue(), 22, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(1)).floatValue(), 33, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(0)).floatValue(), 0.7059, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(1)).floatValue(), 0.8008, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(0)).floatValue(), 0.0, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(1)).floatValue(), 1.3, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(0)).floatValue(), 0.34, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(1)).floatValue(), 0.389, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(0)).floatValue(), 0.9, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(1)).floatValue(), 1.0, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(0)).floatValue(), 0.03418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(1)).floatValue(), 0.2871, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(0)).floatValue(), 0.0263, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(1)).floatValue(), 0.0163, 0.001);

      ModelEnum oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 2);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals( ( (Float) oAllom.mp_fAdultLinearSlope.getValue().get(0)).floatValue(), 0.96, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultLinearSlope.getValue().get(1)).floatValue(), 1.3, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultLinearIntercept.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultLinearIntercept.getValue().get(1)).floatValue(), -0.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingLinearSlope.getValue().get(0)).floatValue(), 0.492, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingLinearSlope.getValue().get(1)).floatValue(), 0.0549, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingLinearIntercept.getValue().get(0)).floatValue(), 1.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingLinearIntercept.getValue().get(1)).floatValue(), 0, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearSlope.getValue().get(0)).floatValue(), 0.9629, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearSlope.getValue().get(1)).floatValue(), 1.228, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearIntercept.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearIntercept.getValue().get(1)).floatValue(), -0.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearSlope.getValue().get(0)).floatValue(), 2.1, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearSlope.getValue().get(1)).floatValue(), 1.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().get(0)).floatValue(), 0.02418, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().get(1)).floatValue(), 0.034, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().get(0)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().get(1)).floatValue(), 1.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(0)).floatValue(), 0.761, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().get(1)).floatValue(), -0.847, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(0)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().get(1)).floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearIntercept.
          getValue().get(0)).floatValue(), 0.761, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearIntercept.
          getValue().get(1)).floatValue(), 0.758, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadIntercept.getValue().
          get(0)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadIntercept.getValue().
          get(1)).floatValue(), 0.4, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownRad.getValue().
          get(0)).floatValue(), 8, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownRad.getValue().
          get(1)).floatValue(), 7, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape1.getValue().
          get(0)).floatValue(), 0.09, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape1.getValue().
          get(1)).floatValue(), 0.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape2.getValue().
          get(0)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape2.getValue().
          get(1)).floatValue(), 2.4, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtIntercept.getValue().
          get(0)).floatValue(), 0.6, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtIntercept.getValue().
          get(1)).floatValue(), 0.5, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownHt.getValue().
          get(0)).floatValue(), 60, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownHt.getValue().
          get(1)).floatValue(), 12, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape1.getValue().
          get(0)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape1.getValue().
          get(1)).floatValue(), 0.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape2.getValue().
          get(0)).floatValue(), 3.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape2.getValue().
          get(1)).floatValue(), 4.1, 0.001);     
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().
          get(0)).floatValue(), 3.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().
          get(1)).floatValue(), 5.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().
          get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().
          get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().
          get(0)).floatValue(), 2.17031683, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().
          get(1)).floatValue(), 2.81, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().
          get(0)).floatValue(), 0.69994199, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().
          get(1)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().
          get(0)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().
          get(1)).floatValue(), -0.13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().
          get(0)).floatValue(), 0.00163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().
          get(1)).floatValue(), 0.000126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().
          get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().
          get(1)).floatValue(), 12, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().
          get(0)).floatValue(), 0.163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().
          get(1)).floatValue(), 0.126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().
          get(0)).floatValue(), 65.67, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().
          get(1)).floatValue(), 9.52, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().
          get(0)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().
          get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().
          get(0)).floatValue(), 1.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().
          get(1)).floatValue(), 1.531, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().
          get(0)).floatValue(), 0.698, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().
          get(1)).floatValue(), 0.457, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().
          get(0)).floatValue(), -0.0163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().
          get(1)).floatValue(), -0.0126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().
          get(0)).floatValue(), 0.0034, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().
          get(1)).floatValue(), 0.00526, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().
          get(0)).floatValue(), 11, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().
          get(1)).floatValue(), 13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().
          get(0)).floatValue(), 0.042, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().
          get(1)).floatValue(), 0.034, 0.001);

      //Verify lambdas
      int iCount = 0;
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);
      iCount = 0;
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iCount++;
        }
      }

      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.6640108, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.71, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.00442797, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.12, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 2);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().toLowerCase().indexOf("nci crown radius lambda"));
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.83, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.33, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.54, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.27, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 2);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().toLowerCase().indexOf("nci crown depth lambda"));
    }
    catch (ModelException oErr) {
      fail("Allometry setup testing failed with message " +
          oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests species changing.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    ModelData oData;
    ModelVector oVector;
    int i, iIndex = 0;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = WriteXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Now change the species by adding another
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      Allometry oAllom = oManager.getTreePopulation().getAllometry();

      //Verify that all the enums have enums where they're supposed to
      assertEquals(3, oAllom.mp_iWhatAdultHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSeedlingHDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatAdultCRDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingCRDFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatAdultCDHFunction.getValue().size());
      assertEquals(3, oAllom.mp_iWhatSaplingCDHFunction.getValue().size());

      for (i = 0; i < 3; i++) {
        assertTrue(null != oAllom.mp_iWhatAdultHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSeedlingHDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatAdultCRDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingCRDFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatAdultCDHFunction.getValue().get(i));
        assertTrue(null != oAllom.mp_iWhatSaplingCDHFunction.getValue().get(i));
      }

      //Verify that the first two species values are still good
      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(2)).floatValue(), 45, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCanopyHeight.getValue().get(1)).floatValue(), 39.48, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(2)).floatValue(), 0.02418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownRad.getValue().get(1)).floatValue(), 0.0549, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(2)).floatValue(), 1.1, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownRadExp.getValue().get(1)).floatValue(), 0.9, 0.001);
      
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(2)).floatValue(), 22, 0.001);
      assertEquals(((Float) oAllom.mp_fMaxCrownRad.getValue().get(1)).floatValue(), 33, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(2)).floatValue(), 0.7059, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhSlope.getValue().get(1)).floatValue(), 0.8008, 0.001);

      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(2)).floatValue(), 0.0, 0.001);
      assertEquals(((Float) oAllom.mp_fDiam10ToDbhIntercept.getValue().get(1)).floatValue(), 1.3, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(2)).floatValue(), 0.34, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsympCrownDpth.getValue().get(1)).floatValue(), 0.389, 0.001);

      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(2)).floatValue(), 0.9, 0.001);
      assertEquals(((Float) oAllom.mp_fCrownDepthExp.getValue().get(1)).floatValue(), 1.0, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(2)).floatValue(), 0.03418, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfHeightDiam10.getValue().get(1)).floatValue(), 0.2871, 0.001);

      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(2)).floatValue(), 0.0263, 0.001);
      assertEquals(((Float) oAllom.mp_fSlopeOfAsymptoticHeight.getValue().get(1)).floatValue(), 0.0163, 0.001);

      ModelEnum oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 2);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCRDFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultCDHFunction.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().
          get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingCDHFunction.getValue().
          get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals( ( (Float) oAllom.mp_fAdultLinearSlope.getValue().
          get(2)).floatValue(), 0.96, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultLinearSlope.getValue().
          get(1)).floatValue(), 1.3, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultLinearIntercept.getValue().
          get(2)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultLinearIntercept.getValue().
          get(1)).floatValue(), -0.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingLinearSlope.getValue().
          get(2)).floatValue(), 0.492, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingLinearSlope.getValue().
          get(1)).floatValue(), 0.0549, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingLinearIntercept.getValue().
          get(2)).floatValue(), 1.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingLinearIntercept.getValue().
          get(1)).floatValue(), 0, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearSlope.getValue().
          get(2)).floatValue(), 0.9629, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearSlope.getValue().
          get(1)).floatValue(), 1.228, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearIntercept.getValue().
          get(2)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingLinearIntercept.getValue().
          get(1)).floatValue(), -0.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearSlope.getValue().
          get(2)).floatValue(), 2.1, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearSlope.getValue().
          get(1)).floatValue(), 1.9, 0.001);

      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().
          get(2)).floatValue(), 0.02418, 0.001);
      assertEquals( ( (Float) oAllom.mp_fAdultReverseLinearIntercept.getValue().
          get(1)).floatValue(), 0.034, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().
          get(2)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearSlope.getValue().
          get(1)).floatValue(), 1.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().
          get(2)).floatValue(), 0.761, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSaplingReverseLinearIntercept.getValue().
          get(1)).floatValue(), -0.847, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().
          get(2)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearSlope.getValue().
          get(1)).floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearIntercept.
          getValue().get(2)).floatValue(), 0.761, 0.001);
      assertEquals( ( (Float) oAllom.mp_fSeedlingReverseLinearIntercept.
          getValue().get(1)).floatValue(), 0.758, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadIntercept.getValue().
          get(2)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadIntercept.getValue().
          get(1)).floatValue(), 0.4, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownRad.getValue().
          get(2)).floatValue(), 8, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownRad.getValue().
          get(1)).floatValue(), 7, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape1.getValue().
          get(2)).floatValue(), 0.09, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape1.getValue().
          get(1)).floatValue(), 0.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape2.getValue().
          get(2)).floatValue(), 1.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownRadShape2.getValue().
          get(1)).floatValue(), 2.4, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtIntercept.getValue().
          get(2)).floatValue(), 0.6, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtIntercept.getValue().
          get(1)).floatValue(), 0.5, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownHt.getValue().
          get(2)).floatValue(), 60, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRAsympCrownHt.getValue().
          get(1)).floatValue(), 12, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape1.getValue().
          get(2)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape1.getValue().
          get(1)).floatValue(), 0.1, 0.001);

      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape2.getValue().
          get(2)).floatValue(), 3.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fCRCrownHtShape2.getValue().
          get(1)).floatValue(), 4.1, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().
          get(2)).floatValue(), 3.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownRadius.getValue().
          get(1)).floatValue(), 5.2, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().
          get(2)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMaxCrowdingRadius.getValue().
          get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().
          get(2)).floatValue(), 2.17031683, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRAlpha.getValue().
          get(1)).floatValue(), 2.81, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().
          get(2)).floatValue(), 0.69994199, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRBeta.getValue().
          get(1)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().
          get(2)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRGamma.getValue().
          get(1)).floatValue(), -0.13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().
          get(2)).floatValue(), 0.00163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRN.getValue().
          get(1)).floatValue(), 0.000126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().
          get(2)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRMinNeighborDBH.getValue().
          get(1)).floatValue(), 12, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().
          get(2)).floatValue(), 0.163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICRD.getValue().
          get(1)).floatValue(), 0.126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().
          get(2)).floatValue(), 65.67, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCIMaxCrownDepth.getValue().
          get(1)).floatValue(), 9.52, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().
          get(2)).floatValue(), 10, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMaxCrowdingRadius.getValue().
          get(1)).floatValue(), 15, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().
          get(2)).floatValue(), 1.052587488, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDAlpha.getValue().
          get(1)).floatValue(), 1.531, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().
          get(2)).floatValue(), 0.698, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDBeta.getValue().
          get(1)).floatValue(), 0.457, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().
          get(2)).floatValue(), -0.0163, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDGamma.getValue().
          get(1)).floatValue(), -0.0126, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().
          get(2)).floatValue(), 0.0034, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDN.getValue().
          get(1)).floatValue(), 0.00526, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().
          get(2)).floatValue(), 11, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDMinNeighborDBH.getValue().
          get(1)).floatValue(), 13, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().
          get(2)).floatValue(), 0.042, 0.001);
      assertEquals( ( (Float) oAllom.mp_fNCICDD.getValue().
          get(1)).floatValue(), 0.034, 0.001);

      //Verify lambdas
      int iCount = 0;
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);
      iCount = 0;
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex+2);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.6640108, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.71, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.00442797, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.12, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().toLowerCase().indexOf("nci crown radius lambda"));
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex+2);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.83, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.33, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.54, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.27, 0.01);
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().toLowerCase().indexOf("nci crown depth lambda"));
    }
    catch (ModelException oErr) {
      fail("Allometry setup testing failed with message " +
          oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  
  /**
   * Tests data validation
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = WriteXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      Allometry oAllom = oManager.getTreePopulation().getAllometry();
      TreePopulation oPop = oManager.getTreePopulation();

      //Case:  Everything's OK
      oAllom.validateData(oPop);

      //Case:  adult linear slope is 0 but linear isn't used - not an error
      ModelEnum oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().
          get(0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(1);
      Behavior.setVectorValues(oAllom.mp_fAdultLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  adult linear slope is 0 and linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(1);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch adult linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fAdultLinearSlope,
          new Float[] {new Float(0.1), new Float(1.3)});

      //Case:  sapling linear slope is 0 but linear isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(1);
      Behavior.setVectorValues(oAllom.mp_fSaplingLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  sapling linear slope is 0 and linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch sapling linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSaplingLinearSlope,
          new Float[] {new Float(10), new Float(1.3)});

      //Case:  seedling linear slope is 0 but linear isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(1);
      oEnum.setValue(1);
      Behavior.setVectorValues(oAllom.mp_fSeedlingLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  seedling linear slope is 0 and linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(0);
      oEnum.setValue(1);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch seedling linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSeedlingLinearSlope,
          new Float[] {new Float(10), new Float(1.3)});

      //Case:  adult reverse linear slope is 0 but reverse linear isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(2);
      Behavior.setVectorValues(oAllom.mp_fAdultReverseLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  adult reverse linear slope is 0 and reverse linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch adult reverse linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fAdultReverseLinearSlope,
          new Float[] {new Float(10), new Float(1.3)});

      //Case:  sapling reverse linear slope is 0 but reverse linear isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(2);
      Behavior.setVectorValues(oAllom.mp_fSaplingReverseLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  sapling reverse linear slope is 0 and reverse linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch sapling reverse linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSaplingReverseLinearSlope,
          new Float[] {new Float(10), new Float(1.3)});

      //Case:  seedling reverse linear slope is 0 but reverse linear isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(1);
      oEnum.setValue(2);
      Behavior.setVectorValues(oAllom.mp_fSeedlingReverseLinearSlope,
          new Float[] {new Float(0), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  seedling reverse linear slope is 0 and reverse linear is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSeedlingHDFunction.getValue().
          get(1);
      oEnum.setValue(0);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch seedling reverse linear slope of 0");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSeedlingReverseLinearSlope,
          new Float[] {new Float(10), new Float(1.3)});

      //Case:  Max tree height less than 0
      Behavior.setVectorValues(oAllom.mp_fMaxCanopyHeight,
          new Float[] {new Float( -10), new Float(1.3)});
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't negative max canopy height");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fMaxCanopyHeight,
          new Float[] {new Float(10), new Float(10)});
      oAllom.validateData(oPop);

      //Case:  slope of asymptotic height is 0 but standard isn't used - not an error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(2);
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsymptoticHeight,
          new Float[] {new Float( -0.1), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  slope of asymptotic height is 0 and adult standard is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(2);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch negative slope of asymptotic height");
      }
      catch (ModelException oErr) {
        ;
      }

      //Case:  slope of asymptotic height is 0 and sapling standard is used - error
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(0);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatAdultHDFunction.getValue().get(1);
      oEnum.setValue(2);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          0);
      oEnum.setValue(0);
      oEnum = (ModelEnum) oAllom.mp_iWhatSaplingHDFunction.getValue().get(
          1);
      oEnum.setValue(2);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch negative slope of asymptotic height");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsymptoticHeight,
          new Float[] {new Float(0.1), new Float(1.3)});
      oAllom.validateData(oPop);

      //Case:  Canrad to dbh ratio less than 0
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsympCrownRad,
          new Float[] {new Float( -10), new Float(1.3)});
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't negative canrad to dbh ratio");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsympCrownRad,
          new Float[] {new Float(10), new Float(10)});
      oAllom.validateData(oPop);
      
      //Case:  Max canrad less than 0
      Behavior.setVectorValues(oAllom.mp_fMaxCrownRad, new Float[] {new Float( -10), new Float(1.3)});
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't negative max canrad");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fMaxCrownRad, new Float[] {new Float(10), new Float(10)});
      oAllom.validateData(oPop);

      //Case:  diam 10 to dbh ratio less than 0
      Behavior.setVectorValues(oAllom.mp_fDiam10ToDbhSlope,
          new Float[] {new Float( -10), new Float(1.3)});
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't negative diam10 to dbh ratio");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fDiam10ToDbhSlope,
          new Float[] {new Float(10), new Float(10)});
      oAllom.validateData(oPop);

      //Case:  asymptotic slope of canopy depth less than 0
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsympCrownDpth,
          new Float[] {new Float( -10), new Float(1.3)});
      oEnum = new ModelEnum(new int[] {1, 0},
          new String[] {"Chapman-Richards", "Standard"},
          "Function used", "");
      oEnum.setValue("Standard");
      oAllom.mp_iWhatAdultCDHFunction.getValue().add(0, oEnum);
      try {
        oAllom.validateData(oPop);
        fail("ValidateData didn't catch negative asymptotic slope of canopy depth");
      }
      catch (ModelException oErr) {
        ;
      }
      Behavior.setVectorValues(oAllom.mp_fSlopeOfAsympCrownDpth,
          new Float[] {new Float(10), new Float(10)});
      oAllom.validateData(oPop);
    }
    catch (ModelException oErr) {
      fail("Allometry validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

  }

  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      TreePopulation oPop;
      Allometry oAllom = null;
      String[] p_sExpected;
      ArrayList<String> p_oVals = new ArrayList<String>(0);
      boolean[] p_bAppliesTo = new boolean[4];
      int i, iNumSp = 4;

      //-----------------------------------------------
      //Case: all standard height-diameter relationships, standard crown shape
      //relationships for adults, Chapman-Richards crown shape relationships
      //for saplings
      oManager.inputXMLParameterFile(WriteXMLFile2());
      oPop = oManager.getTreePopulation();
      oAllom = oManager.getTreePopulation().getAllometry();

      for (i = 0; i < iNumSp; i++) {p_bAppliesTo[i] = true;}  

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("1");}
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "Chapman-Richards Crown Radius Intercept",
          "Chapman-Richards Asymptotic Crown Radius",
          "Chapman-Richards Crown Radius Shape 1 (b)",
          "Chapman-Richards Crown Radius Shape 2 (c)",
          "Chapman-Richards Crown Depth Intercept",
          "Chapman-Richards Asymptotic Crown Depth",
          "Chapman-Richards Crown Depth Shape 1 (b)",
          "Chapman-Richards Crown Depth Shape 2 (c)"};
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all linear height diameter relationships,
      //Chapman-Richards crown shape functions for adults, standard crown
      //shape functions for saplings
      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("1");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Adult Linear Function Slope",
          "Adult Linear Function Intercept",
          "Sapling Linear Function Slope",
          "Sapling Linear Function Intercept",
          "Seedling Linear Function Slope",
          "Seedling Linear Function Intercept",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Chapman-Richards Crown Radius Intercept",
          "Chapman-Richards Asymptotic Crown Radius",
          "Chapman-Richards Crown Radius Shape 1 (b)",
          "Chapman-Richards Crown Radius Shape 2 (c)",
          "Chapman-Richards Crown Depth Intercept",
          "Chapman-Richards Asymptotic Crown Depth",
          "Chapman-Richards Crown Depth Shape 1 (b)",
          "Chapman-Richards Crown Depth Shape 2 (c)"
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all reverse linear height diameter relationships, non-spatial
      //density dependent crown radius function for adults, standard crown
      //shape functions for all else
      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("2");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Adult Reverse Linear Function Slope",
          "Adult Reverse Linear Function Intercept",
          "Sapling Reverse Linear Function Slope",
          "Sapling Reverse Linear Function Intercept",
          "Seedling Reverse Linear Function Slope",
          "Seedling Reverse Linear Function Intercept",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Non-Spatial Density Dep. Inst. Crown Depth \"a\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"b\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"c\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"d\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"e\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"f\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"g\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"h\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"i\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"j\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"D1\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"a\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"b\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"c\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"d\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"e\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"f\""
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: power sapling height diameter relationships, non-spatial
      //density dependent crown height function for adults, standard 
      //functions for all else
      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("2");}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("3");}
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "Power Function \"a\"",
          "Power Function Exponent \"b\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"a\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"b\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"c\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"d\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"e\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"f\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"g\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"h\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"i\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"j\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"a\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"b\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"c\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"d\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"e\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"f\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"g\""
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all standard height-diameter relationships, standard crown shape
      //relationships for adults, NCI crown radius relationship for saplings 
      oManager.inputXMLParameterFile(WriteXMLFile2());
      oPop = oManager.getTreePopulation();
      oAllom = oManager.getTreePopulation().getAllometry();

      for (i = 0; i < iNumSp; i++) {p_bAppliesTo[i] = true;}  

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("3");}
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "NCI Crown Radius - Max Potential Radius (m)", 
          "NCI Crown Radius - Alpha", 
          "NCI Crown Radius - Beta", 
          "NCI Crown Radius - Gamma", 
          "NCI Crown Radius - Max Search Distance for Neighbors (m)", 
          "NCI Crown Radius - Crowding Effect \"n\"", 
          "NCI Crown Radius - Size Effect \"d\"", 
          "NCI Crown Radius - Minimum Neighbor DBH (cm)",
          "NCI Crown Radius Lambda for Species 1 Neighbors",
          "NCI Crown Radius Lambda for Species 2 Neighbors",
          "NCI Crown Radius Lambda for Species 3 Neighbors",
          "NCI Crown Radius Lambda for Species 4 Neighbors",
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all standard height-diameter relationships, standard crown shape
      //relationships for saplings, NCI crown radius relationship for adults 
      oManager.inputXMLParameterFile(WriteXMLFile2());
      oPop = oManager.getTreePopulation();
      oAllom = oManager.getTreePopulation().getAllometry();

      for (i = 0; i < iNumSp; i++) {p_bAppliesTo[i] = true;}  

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("3");}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "NCI Crown Radius - Max Potential Radius (m)", 
          "NCI Crown Radius - Alpha", 
          "NCI Crown Radius - Beta", 
          "NCI Crown Radius - Gamma", 
          "NCI Crown Radius - Max Search Distance for Neighbors (m)", 
          "NCI Crown Radius - Crowding Effect \"n\"", 
          "NCI Crown Radius - Size Effect \"d\"", 
          "NCI Crown Radius - Minimum Neighbor DBH (cm)",
          "NCI Crown Radius Lambda for Species 1 Neighbors",
          "NCI Crown Radius Lambda for Species 2 Neighbors",
          "NCI Crown Radius Lambda for Species 3 Neighbors",
          "NCI Crown Radius Lambda for Species 4 Neighbors",
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all standard height-diameter relationships, standard crown shape
      //relationships for adults, NCI crown Height relationship for saplings 
      oManager.inputXMLParameterFile(WriteXMLFile2());
      oPop = oManager.getTreePopulation();
      oAllom = oManager.getTreePopulation().getAllometry();

      for (i = 0; i < iNumSp; i++) {p_bAppliesTo[i] = true;}  

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("3");}
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "NCI Crown Depth - Max Potential Depth (m)", 
          "NCI Crown Depth - Alpha", 
          "NCI Crown Depth - Beta", 
          "NCI Crown Depth - Gamma", 
          "NCI Crown Depth - Max Search Distance for Neighbors (m)", 
          "NCI Crown Depth - Crowding Effect \"n\"", 
          "NCI Crown Depth - Size Effect \"d\"", 
          "NCI Crown Depth - Minimum Neighbor DBH (cm)",
          "NCI Crown Depth Lambda for Species 1 Neighbors",
          "NCI Crown Depth Lambda for Species 2 Neighbors",
          "NCI Crown Depth Lambda for Species 3 Neighbors",
          "NCI Crown Depth Lambda for Species 4 Neighbors",
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: all standard height-diameter relationships, standard crown shape
      //relationships for saplings, NCI crown Height relationship for adults  
      oManager.inputXMLParameterFile(WriteXMLFile2());
      oPop = oManager.getTreePopulation();
      oAllom = oManager.getTreePopulation().getAllometry();

      for (i = 0; i < iNumSp; i++) {p_bAppliesTo[i] = true;}  

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("0");}  
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add("3");}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);

      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Slope of Asymptotic Crown Radius",
          "Crown Radius Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Height",
          "Crown Height Exponent",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "NCI Crown Depth - Max Potential Depth (m)", 
          "NCI Crown Depth - Alpha", 
          "NCI Crown Depth - Beta", 
          "NCI Crown Depth - Gamma", 
          "NCI Crown Depth - Max Search Distance for Neighbors (m)", 
          "NCI Crown Depth - Crowding Effect \"n\"", 
          "NCI Crown Depth - Size Effect \"d\"", 
          "NCI Crown Depth - Minimum Neighbor DBH (cm)",
          "NCI Crown Depth Lambda for Species 1 Neighbors",
          "NCI Crown Depth Lambda for Species 2 Neighbors",
          "NCI Crown Depth Lambda for Species 3 Neighbors",
          "NCI Crown Depth Lambda for Species 4 Neighbors",
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      //-----------------------------------------------
      //Case: Adults get one each of the height-diameter functions, and one
      //each of the crown shape functions; saplings get the first three height-
      //diameter functions, and the first two crown shape functions.
      p_oVals.clear();
      for (i = 0; i < 3; i++) {p_oVals.add(String.valueOf(i));}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingHDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSeedlingHDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      for (i = 0; i < iNumSp; i++) {p_oVals.add(String.valueOf(i));}
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCDHFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatAdultCRDFunction, p_oVals, p_bAppliesTo);

      p_oVals.clear();
      p_oVals.add("0"); p_oVals.add("1"); p_oVals.add("0");
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCRDFunction, p_oVals, p_bAppliesTo);
      Behavior.setVectorValues(oAllom.mp_iWhatSaplingCDHFunction, p_oVals, p_bAppliesTo);
      p_sExpected = new String[] {
          "Maximum Tree Height, in meters",
          "Slope of Asymptotic Height",
          "Crown Radius Exponent",
          "Crown Height Exponent",
          "Maximum Crown Radius (Standard) (m)",
          "Slope of Asymptotic Crown Radius",
          "Slope of Asymptotic Crown Height",
          "Slope of Height-Diameter at 10 cm Relationship",
          "Slope of DBH to Diameter at 10 cm Relationship",
          "Intercept of DBH to Diameter at 10 cm Relationship",
          "Adult Linear Function Slope",
          "Adult Linear Function Intercept",
          "Sapling Linear Function Slope",
          "Sapling Linear Function Intercept",
          "Seedling Linear Function Slope",
          "Seedling Linear Function Intercept",
          "Adult Reverse Linear Function Slope",
          "Adult Reverse Linear Function Intercept",
          "Sapling Reverse Linear Function Slope",
          "Sapling Reverse Linear Function Intercept",
          "Seedling Reverse Linear Function Slope",
          "Seedling Reverse Linear Function Intercept",
          "Chapman-Richards Crown Radius Intercept",
          "Chapman-Richards Asymptotic Crown Radius",
          "Chapman-Richards Crown Radius Shape 1 (b)",
          "Chapman-Richards Crown Radius Shape 2 (c)",
          "Chapman-Richards Crown Depth Intercept",
          "Chapman-Richards Asymptotic Crown Depth",
          "Chapman-Richards Crown Depth Shape 1 (b)",
          "Chapman-Richards Crown Depth Shape 2 (c)",
          "Non-Spatial Density Dep. Inst. Crown Depth \"a\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"b\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"c\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"d\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"e\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"f\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"g\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"h\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"i\"",
          "Non-Spatial Density Dep. Inst. Crown Depth \"j\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"D1\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"a\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"b\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"c\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"d\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"e\"",
          "Non-Spatial Exp. Density Dep. Crown Radius \"f\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"a\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"b\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"c\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"d\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"e\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"f\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"g\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"h\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"i\"",
          "Non-Spatial Density Dep. Inst. Crown Radius \"j\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"a\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"b\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"c\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"d\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"e\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"f\"",
          "Non-Spatial Log. Density Dep. Crown Depth \"g\"",
          "NCI Crown Depth - Max Potential Depth (m)", 
          "NCI Crown Depth - Alpha", 
          "NCI Crown Depth - Beta", 
          "NCI Crown Depth - Gamma", 
          "NCI Crown Depth - Max Search Distance for Neighbors (m)", 
          "NCI Crown Depth - Crowding Effect \"n\"", 
          "NCI Crown Depth - Size Effect \"d\"", 
          "NCI Crown Depth - Minimum Neighbor DBH (cm)",
          "NCI Crown Depth Lambda for Species 1 Neighbors",
          "NCI Crown Depth Lambda for Species 2 Neighbors",
          "NCI Crown Depth Lambda for Species 3 Neighbors",
          "NCI Crown Depth Lambda for Species 4 Neighbors",
          "NCI Crown Radius - Max Potential Radius (m)", 
          "NCI Crown Radius - Alpha", 
          "NCI Crown Radius - Beta", 
          "NCI Crown Radius - Gamma", 
          "NCI Crown Radius - Max Search Distance for Neighbors (m)", 
          "NCI Crown Radius - Crowding Effect \"n\"", 
          "NCI Crown Radius - Size Effect \"d\"", 
          "NCI Crown Radius - Minimum Neighbor DBH (cm)",
          "NCI Crown Radius Lambda for Species 1 Neighbors",
          "NCI Crown Radius Lambda for Species 2 Neighbors",
          "NCI Crown Radius Lambda for Species 3 Neighbors",
          "NCI Crown Radius Lambda for Species 4 Neighbors",
      };
      TestTable(oAllom.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for allometry.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }  

  /**
   * Makes sure that nci lambda values are managed correctly when species
   * are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      Float fVal;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = WriteXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      Allometry oAllom = oManager.getTreePopulation().getAllometry();
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.33, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.31, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.87, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);

      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.33, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.31, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.87, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      //Lambdas
      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown radius lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.19, fVal.floatValue(), 0.001);

      for (i = 0; i < oAllom.mp_oAllData.size(); i++) {
        oData =  oAllom.mp_oAllData.get(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci crown depth lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oAllom.mp_oAllData.get(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.19, fVal.floatValue(), 0.001);

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
   * Writes a valid file for testing parameter file reading.
   * @throws IOException if the file can't be written.
   * @return String Filename.
   */
  private String WriteXMLFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.02418</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.1</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">0.9</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdMaxCrownRad>");
    oOut.write("<tr_smcrVal species=\"Species_1\">22</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"Species_2\">33</tr_smcrVal>");
    oOut.write("</tr_stdMaxCrownRad>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">1.3</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.34</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">0.9</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.2871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0163</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">2</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">1</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">1</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.96</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">1.3</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">0</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">-0.9</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.492</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.0549</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">1.2</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">1.228</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">-0.9</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_adultReverseLinearSlope>");
    oOut.write("<tr_arlsVal species=\"Species_1\">2.1</tr_arlsVal>");
    oOut.write("<tr_arlsVal species=\"Species_2\">1.9</tr_arlsVal>");
    oOut.write("</tr_adultReverseLinearSlope>");
    oOut.write("<tr_adultReverseLinearIntercept>");
    oOut.write("<tr_arliVal species=\"Species_1\">0.02418</tr_arliVal>");
    oOut.write("<tr_arliVal species=\"Species_2\">0.034</tr_arliVal>");
    oOut.write("</tr_adultReverseLinearIntercept>");
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"Species_1\">1.5</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_2\">1.1</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"Species_1\">0.761</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_2\">-0.847</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_serlsVal species=\"Species_1\">1.5</tr_serlsVal>");
    oOut.write("<tr_serlsVal species=\"Species_2\">0.02</tr_serlsVal>");
    oOut.write("</tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_serliVal species=\"Species_1\">0.761</tr_serliVal>");
    oOut.write("<tr_serliVal species=\"Species_2\">0.758</tr_serliVal>");
    oOut.write("</tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_chRichCrownRadIntercept>");
    oOut.write("<tr_crcriVal species=\"Species_1\">0.3</tr_crcriVal>");
    oOut.write("<tr_crcriVal species=\"Species_2\">0.4</tr_crcriVal>");
    oOut.write("</tr_chRichCrownRadIntercept>");
    oOut.write("<tr_chRichCrownRadAsymp>");
    oOut.write("<tr_crcraVal species=\"Species_1\">8</tr_crcraVal>");
    oOut.write("<tr_crcraVal species=\"Species_2\">7</tr_crcraVal>");
    oOut.write("</tr_chRichCrownRadAsymp>");
    oOut.write("<tr_chRichCrownRadShape1b>");
    oOut.write("<tr_crcrs1bVal species=\"Species_1\">0.09</tr_crcrs1bVal>");
    oOut.write("<tr_crcrs1bVal species=\"Species_2\">0.1</tr_crcrs1bVal>");
    oOut.write("</tr_chRichCrownRadShape1b>");
    oOut.write("<tr_chRichCrownRadShape2c>");
    oOut.write("<tr_crcrs2cVal species=\"Species_1\">1.5</tr_crcrs2cVal>");
    oOut.write("<tr_crcrs2cVal species=\"Species_2\">2.4</tr_crcrs2cVal>");
    oOut.write("</tr_chRichCrownRadShape2c>");
    oOut.write("<tr_chRichCrownHtIntercept>");
    oOut.write("<tr_crchiVal species=\"Species_1\">0.6</tr_crchiVal>");
    oOut.write("<tr_crchiVal species=\"Species_2\">0.5</tr_crchiVal>");
    oOut.write("</tr_chRichCrownHtIntercept>");
    oOut.write("<tr_chRichCrownHtAsymp>");
    oOut.write("<tr_crchaVal species=\"Species_1\">60</tr_crchaVal>");
    oOut.write("<tr_crchaVal species=\"Species_2\">12</tr_crchaVal>");
    oOut.write("</tr_chRichCrownHtAsymp>");
    oOut.write("<tr_chRichCrownHtShape1b>");
    oOut.write("<tr_crchs1bVal species=\"Species_1\">0.3</tr_crchs1bVal>");
    oOut.write("<tr_crchs1bVal species=\"Species_2\">0.1</tr_crchs1bVal>");
    oOut.write("</tr_chRichCrownHtShape1b>");
    oOut.write("<tr_chRichCrownHtShape2c>");
    oOut.write("<tr_crchs2cVal species=\"Species_1\">3.2</tr_crchs2cVal>");
    oOut.write("<tr_crchs2cVal species=\"Species_2\">4.1</tr_crchs2cVal>");
    oOut.write("</tr_chRichCrownHtShape2c>");
    oOut.write("<tr_nciCRSpecies_1NeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Species_1\">0.66401082</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Species_2\">0.71</tr_ncrlVal>");
    oOut.write("</tr_nciCRSpecies_1NeighborLambda>");
    oOut.write("<tr_nciCRSpecies_2NeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Species_1\">0.00442797</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Species_2\">0.12</tr_ncrlVal>");
    oOut.write("</tr_nciCRSpecies_2NeighborLambda>");
    oOut.write("<tr_nciCRMaxCrownRadius>");
    oOut.write("<tr_ncrmcrVal species=\"Species_1\">3.052587488</tr_ncrmcrVal>");
    oOut.write("<tr_ncrmcrVal species=\"Species_2\">5.2</tr_ncrmcrVal>");
    oOut.write("</tr_nciCRMaxCrownRadius>");
    oOut.write("<tr_nciCRMaxCrowdingRadius>");
    oOut.write("<tr_ncrmcrVal species=\"Species_1\">10</tr_ncrmcrVal>");
    oOut.write("<tr_ncrmcrVal species=\"Species_2\">15</tr_ncrmcrVal>");
    oOut.write("</tr_nciCRMaxCrowdingRadius>");
    oOut.write("<tr_nciCRAlpha>");
    oOut.write("<tr_ncraVal species=\"Species_1\">2.17031683</tr_ncraVal>");
    oOut.write("<tr_ncraVal species=\"Species_2\">2.81</tr_ncraVal>");
    oOut.write("</tr_nciCRAlpha>");
    oOut.write("<tr_nciCRBeta>");
    oOut.write("<tr_ncrbVal species=\"Species_1\">0.69994199</tr_ncrbVal>");
    oOut.write("<tr_ncrbVal species=\"Species_2\">0.5</tr_ncrbVal>");
    oOut.write("</tr_nciCRBeta>");
    oOut.write("<tr_nciCRGamma>");
    oOut.write("<tr_ncrgVal species=\"Species_1\">0</tr_ncrgVal>");
    oOut.write("<tr_ncrgVal species=\"Species_2\">-0.13</tr_ncrgVal>");
    oOut.write("</tr_nciCRGamma>");
    oOut.write("<tr_nciCRCrowdingN>");
    oOut.write("<tr_nccrnVal species=\"Species_1\">0.00163</tr_nccrnVal>");
    oOut.write("<tr_nccrnVal species=\"Species_2\">0.000126</tr_nccrnVal>");
    oOut.write("</tr_nciCRCrowdingN>");
    oOut.write("<tr_nciCRMinNeighborDBH>");
    oOut.write("<tr_ncrmndVal species=\"Species_1\">10</tr_ncrmndVal>");
    oOut.write("<tr_ncrmndVal species=\"Species_2\">12</tr_ncrmndVal>");
    oOut.write("</tr_nciCRMinNeighborDBH>");
    oOut.write("<tr_nciCRSizeEffectD>");
    oOut.write("<tr_ncrsedVal species=\"Species_1\">0.163</tr_ncrsedVal>");
    oOut.write("<tr_ncrsedVal species=\"Species_2\">0.126</tr_ncrsedVal>");
    oOut.write("</tr_nciCRSizeEffectD>");
    oOut.write("<tr_nciCDSpecies_1NeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Species_1\">0.83</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Species_2\">0.33</tr_ncdlVal>");
    oOut.write("</tr_nciCDSpecies_1NeighborLambda>");
    oOut.write("<tr_nciCDSpecies_2NeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Species_1\">0.54</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Species_2\">0.27</tr_ncdlVal>");
    oOut.write("</tr_nciCDSpecies_2NeighborLambda>");
    oOut.write("<tr_nciCDMaxCrownDepth>");
    oOut.write("<tr_ncdmcrVal species=\"Species_1\">65.67</tr_ncdmcrVal>");
    oOut.write("<tr_ncdmcrVal species=\"Species_2\">9.52</tr_ncdmcrVal>");
    oOut.write("</tr_nciCDMaxCrownDepth>");
    oOut.write("<tr_nciCDMaxCrowdingRadius>");
    oOut.write("<tr_ncdmcrVal species=\"Species_1\">10</tr_ncdmcrVal>");
    oOut.write("<tr_ncdmcrVal species=\"Species_2\">15</tr_ncdmcrVal>");
    oOut.write("</tr_nciCDMaxCrowdingRadius>");
    oOut.write("<tr_nciCDAlpha>");
    oOut.write("<tr_ncdaVal species=\"Species_1\">1.052587488</tr_ncdaVal>");
    oOut.write("<tr_ncdaVal species=\"Species_2\">1.531</tr_ncdaVal>");
    oOut.write("</tr_nciCDAlpha>");
    oOut.write("<tr_nciCDBeta>");
    oOut.write("<tr_ncdbVal species=\"Species_1\">0.698</tr_ncdbVal>");
    oOut.write("<tr_ncdbVal species=\"Species_2\">0.457</tr_ncdbVal>");
    oOut.write("</tr_nciCDBeta>");
    oOut.write("<tr_nciCDGamma>");
    oOut.write("<tr_ncdgVal species=\"Species_1\">-0.0163</tr_ncdgVal>");
    oOut.write("<tr_ncdgVal species=\"Species_2\">-0.0126</tr_ncdgVal>");
    oOut.write("</tr_nciCDGamma>");
    oOut.write("<tr_nciCDCrowdingN>");
    oOut.write("<tr_nccdnVal species=\"Species_1\">0.0034</tr_nccdnVal>");
    oOut.write("<tr_nccdnVal species=\"Species_2\">0.00526</tr_nccdnVal>");
    oOut.write("</tr_nciCDCrowdingN>");
    oOut.write("<tr_nciCDMinNeighborDBH>");
    oOut.write("<tr_ncdmndVal species=\"Species_1\">11</tr_ncdmndVal>");
    oOut.write("<tr_ncdmndVal species=\"Species_2\">13</tr_ncdmndVal>");
    oOut.write("</tr_nciCDMinNeighborDBH>");
    oOut.write("<tr_nciCDSizeEffectD>");
    oOut.write("<tr_ncdsedVal species=\"Species_1\">0.042</tr_ncdsedVal>");
    oOut.write("<tr_ncdsedVal species=\"Species_2\">0.034</tr_ncdsedVal>");
    oOut.write("</tr_nciCDSizeEffectD>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
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
   * Writes a valid file for testing parameter file reading.
   * @throws IOException if the file can't be written.
   * @return String Filename.
   */
  private String WriteXMLFile2() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
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
   * Writes a valid file for testing parameter file reading.
   * @throws IOException if the file can't be written.
   * @return String Filename.
   */
  private String WriteXMLFileForSpeciesCopy() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
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
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Western_Hemlock\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Western_Redcedar\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Western_Hemlock\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Western_Redcedar\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.02418</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Western_Hemlock\">1.1</tr_screVal>");
    oOut.write("<tr_screVal species=\"Western_Redcedar\">0.9</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Western_Redcedar\">1.3</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.34</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Western_Hemlock\">0.9</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.2871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0163</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Western_Redcedar\">2</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">1</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Western_Hemlock\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Western_Hemlock\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Western_Redcedar\">1</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_nciCRWestern_HemlockNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.66</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">0.37</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.40</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.43</tr_ncrlVal>");
    oOut.write("</tr_nciCRWestern_HemlockNeighborLambda>");
    oOut.write("<tr_nciCRWestern_RedcedarNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.44</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">0.49</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">4.58E-4</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">5.58E-4</tr_ncrlVal>");
    oOut.write("</tr_nciCRWestern_RedcedarNeighborLambda>");
    oOut.write("<tr_nciCRAmabilis_FirNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.41</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">6.21E-4</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.21</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.25</tr_ncrlVal>");
    oOut.write("</tr_nciCRAmabilis_FirNeighborLambda>");
    oOut.write("<tr_nciCRSubalpine_FirNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.41</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">6.21E-4</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.21</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.18</tr_ncrlVal>");
    oOut.write("</tr_nciCRSubalpine_FirNeighborLambda>");
    oOut.write("<tr_nciCRHybrid_SpruceNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.41</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">6.21E-4</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.27</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.22</tr_ncrlVal>");
    oOut.write("</tr_nciCRHybrid_SpruceNeighborLambda>");
    oOut.write("<tr_nciCRLodgepole_PineNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.48</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">6.21</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.52</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.18</tr_ncrlVal>");
    oOut.write("</tr_nciCRLodgepole_PineNeighborLambda>");
    oOut.write("<tr_nciCRTrembling_AspenNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.38</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">0.61</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.38</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.78</tr_ncrlVal>");
    oOut.write("</tr_nciCRTrembling_AspenNeighborLambda>");
    oOut.write("<tr_nciCRBlack_CottonwoodNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.33</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">0.66</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.31</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.87</tr_ncrlVal>");
    oOut.write("</tr_nciCRBlack_CottonwoodNeighborLambda>");
    oOut.write("<tr_nciCRPaper_BirchNeighborLambda>");
    oOut.write("<tr_ncrlVal species=\"Western_Hemlock\">0.74</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Western_Redcedar\">0.19</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Amabilis_Fir\">0.14</tr_ncrlVal>");
    oOut.write("<tr_ncrlVal species=\"Subalpine_Fir\">0.88</tr_ncrlVal>");
    oOut.write("</tr_nciCRPaper_BirchNeighborLambda>");
    oOut.write("<tr_nciCRMaxCrownRadius>");
    oOut.write("<tr_ncrmcrVal species=\"Western_Hemlock\">3.052587488</tr_ncrmcrVal>");
    oOut.write("<tr_ncrmcrVal species=\"Western_Redcedar\">5.2</tr_ncrmcrVal>");
    oOut.write("</tr_nciCRMaxCrownRadius>");
    oOut.write("<tr_nciCRMaxCrowdingRadius>");
    oOut.write("<tr_ncrmcrVal species=\"Western_Hemlock\">10</tr_ncrmcrVal>");
    oOut.write("<tr_ncrmcrVal species=\"Western_Redcedar\">15</tr_ncrmcrVal>");
    oOut.write("</tr_nciCRMaxCrowdingRadius>");
    oOut.write("<tr_nciCRAlpha>");
    oOut.write("<tr_ncraVal species=\"Western_Hemlock\">2.17031683</tr_ncraVal>");
    oOut.write("<tr_ncraVal species=\"Western_Redcedar\">2.81</tr_ncraVal>");
    oOut.write("</tr_nciCRAlpha>");
    oOut.write("<tr_nciCRBeta>");
    oOut.write("<tr_ncrbVal species=\"Western_Hemlock\">0.69994199</tr_ncrbVal>");
    oOut.write("<tr_ncrbVal species=\"Western_Redcedar\">0.5</tr_ncrbVal>");
    oOut.write("</tr_nciCRBeta>");
    oOut.write("<tr_nciCRGamma>");
    oOut.write("<tr_ncrgVal species=\"Western_Hemlock\">0</tr_ncrgVal>");
    oOut.write("<tr_ncrgVal species=\"Western_Redcedar\">-0.13</tr_ncrgVal>");
    oOut.write("</tr_nciCRGamma>");
    oOut.write("<tr_nciCRCrowdingN>");
    oOut.write("<tr_nccrnVal species=\"Western_Hemlock\">0.00163</tr_nccrnVal>");
    oOut.write("<tr_nccrnVal species=\"Western_Redcedar\">0.000126</tr_nccrnVal>");
    oOut.write("</tr_nciCRCrowdingN>");
    oOut.write("<tr_nciCRMinNeighborDBH>");
    oOut.write("<tr_ncrmndVal species=\"Western_Hemlock\">10</tr_ncrmndVal>");
    oOut.write("<tr_ncrmndVal species=\"Western_Redcedar\">12</tr_ncrmndVal>");
    oOut.write("</tr_nciCRMinNeighborDBH>");
    oOut.write("<tr_nciCRSizeEffectD>");
    oOut.write("<tr_ncrsedVal species=\"Western_Hemlock\">0.163</tr_ncrsedVal>");
    oOut.write("<tr_ncrsedVal species=\"Western_Redcedar\">0.126</tr_ncrsedVal>");
    oOut.write("</tr_nciCRSizeEffectD>");
    oOut.write("<tr_nciCDWestern_HemlockNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.66</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">0.37</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.40</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.43</tr_ncdlVal>");
    oOut.write("</tr_nciCDWestern_HemlockNeighborLambda>");
    oOut.write("<tr_nciCDWestern_RedcedarNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.44</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">0.49</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">4.58E-4</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">5.58E-4</tr_ncdlVal>");
    oOut.write("</tr_nciCDWestern_RedcedarNeighborLambda>");
    oOut.write("<tr_nciCDAmabilis_FirNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.41</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">6.21E-4</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.21</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.25</tr_ncdlVal>");
    oOut.write("</tr_nciCDAmabilis_FirNeighborLambda>");
    oOut.write("<tr_nciCDSubalpine_FirNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.41</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">6.21E-4</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.21</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.18</tr_ncdlVal>");
    oOut.write("</tr_nciCDSubalpine_FirNeighborLambda>");
    oOut.write("<tr_nciCDHybrid_SpruceNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.41</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">6.21E-4</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.27</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.22</tr_ncdlVal>");
    oOut.write("</tr_nciCDHybrid_SpruceNeighborLambda>");
    oOut.write("<tr_nciCDLodgepole_PineNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.48</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">6.21</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.52</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.18</tr_ncdlVal>");
    oOut.write("</tr_nciCDLodgepole_PineNeighborLambda>");
    oOut.write("<tr_nciCDTrembling_AspenNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.38</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">0.61</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.38</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.78</tr_ncdlVal>");
    oOut.write("</tr_nciCDTrembling_AspenNeighborLambda>");
    oOut.write("<tr_nciCDBlack_CottonwoodNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.33</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">0.66</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.31</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.87</tr_ncdlVal>");
    oOut.write("</tr_nciCDBlack_CottonwoodNeighborLambda>");
    oOut.write("<tr_nciCDPaper_BirchNeighborLambda>");
    oOut.write("<tr_ncdlVal species=\"Western_Hemlock\">0.74</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Western_Redcedar\">0.19</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Amabilis_Fir\">0.14</tr_ncdlVal>");
    oOut.write("<tr_ncdlVal species=\"Subalpine_Fir\">0.88</tr_ncdlVal>");
    oOut.write("</tr_nciCDPaper_BirchNeighborLambda>");
    oOut.write("<tr_nciCDMaxCrownDepth>");
    oOut.write("<tr_ncdmcrVal species=\"Western_Hemlock\">65.67</tr_ncdmcrVal>");
    oOut.write("<tr_ncdmcrVal species=\"Western_Redcedar\">9.52</tr_ncdmcrVal>");
    oOut.write("</tr_nciCDMaxCrownDepth>");
    oOut.write("<tr_nciCDMaxCrowdingRadius>");
    oOut.write("<tr_ncdmcrVal species=\"Western_Hemlock\">10</tr_ncdmcrVal>");
    oOut.write("<tr_ncdmcrVal species=\"Western_Redcedar\">15</tr_ncdmcrVal>");
    oOut.write("</tr_nciCDMaxCrowdingRadius>");
    oOut.write("<tr_nciCDAlpha>");
    oOut.write("<tr_ncdaVal species=\"Western_Hemlock\">1.052587488</tr_ncdaVal>");
    oOut.write("<tr_ncdaVal species=\"Western_Redcedar\">1.531</tr_ncdaVal>");
    oOut.write("</tr_nciCDAlpha>");
    oOut.write("<tr_nciCDBeta>");
    oOut.write("<tr_ncdbVal species=\"Western_Hemlock\">0.698</tr_ncdbVal>");
    oOut.write("<tr_ncdbVal species=\"Western_Redcedar\">0.457</tr_ncdbVal>");
    oOut.write("</tr_nciCDBeta>");
    oOut.write("<tr_nciCDGamma>");
    oOut.write("<tr_ncdgVal species=\"Western_Hemlock\">-0.0163</tr_ncdgVal>");
    oOut.write("<tr_ncdgVal species=\"Western_Redcedar\">-0.0126</tr_ncdgVal>");
    oOut.write("</tr_nciCDGamma>");
    oOut.write("<tr_nciCDCrowdingN>");
    oOut.write("<tr_nccdnVal species=\"Western_Hemlock\">0.0034</tr_nccdnVal>");
    oOut.write("<tr_nccdnVal species=\"Western_Redcedar\">0.00526</tr_nccdnVal>");
    oOut.write("</tr_nciCDCrowdingN>");
    oOut.write("<tr_nciCDMinNeighborDBH>");
    oOut.write("<tr_ncdmndVal species=\"Western_Hemlock\">11</tr_ncdmndVal>");
    oOut.write("<tr_ncdmndVal species=\"Western_Redcedar\">13</tr_ncdmndVal>");
    oOut.write("</tr_nciCDMinNeighborDBH>");
    oOut.write("<tr_nciCDSizeEffectD>");
    oOut.write("<tr_ncdsedVal species=\"Western_Hemlock\">0.042</tr_ncdsedVal>");
    oOut.write("<tr_ncdsedVal species=\"Western_Redcedar\">0.034</tr_ncdsedVal>");
    oOut.write("</tr_nciCDSizeEffectD>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
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
    String sFileName = "\\testFile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06040101\">"); oOut.write("<plot>");
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
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
    oOut.write("<tr_species speciesName=\"Species_6\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">45</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.02418</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.02418</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">0.9</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.1</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">0.9</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.1</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">1</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">-0.2</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0.3</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">-3</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.34</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.34</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">0.5</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">0.9</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">0.5</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">0.9</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.2871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">2</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">3</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">2</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">1</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">1</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">1</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">1</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">1</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">1</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">1</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">1</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_4\">0.96</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_6\">1.3</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_4\">0</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_6\">-0.9</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.492</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_4\">0.0549</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_2\">1.2</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_4\">0</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_4\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_6\">1.228</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_4\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_6\">-0.9</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_adultReverseLinearSlope>");
    oOut.write("<tr_arlsVal species=\"Species_5\">2.1</tr_arlsVal>");
    oOut.write("</tr_adultReverseLinearSlope>");
    oOut.write("<tr_adultReverseLinearIntercept>");
    oOut.write("<tr_arliVal species=\"Species_5\">0.02418</tr_arliVal>");
    oOut.write("</tr_adultReverseLinearIntercept>");
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"Species_5\">1.5</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_3\">1.1</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"Species_5\">0.761</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_3\">-0.847</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_serlsVal species=\"Species_2\">1.5</tr_serlsVal>");
    oOut.write("<tr_serlsVal species=\"Species_5\">0.02</tr_serlsVal>");
    oOut.write("</tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_serliVal species=\"Species_2\">0.761</tr_serliVal>");
    oOut.write("<tr_serliVal species=\"Species_5\">0.758</tr_serliVal>");
    oOut.write("</tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_chRichCrownRadIntercept>");
    oOut.write("<tr_crcriVal species=\"Species_2\">0.3</tr_crcriVal>");
    oOut.write("<tr_crcriVal species=\"Species_3\">0.4</tr_crcriVal>");
    oOut.write("<tr_crcriVal species=\"Species_4\">0.5</tr_crcriVal>");
    oOut.write("<tr_crcriVal species=\"Species_6\">0.7</tr_crcriVal>");
    oOut.write("</tr_chRichCrownRadIntercept>");
    oOut.write("<tr_chRichCrownRadAsymp>");
    oOut.write("<tr_crcraVal species=\"Species_2\">8</tr_crcraVal>");
    oOut.write("<tr_crcraVal species=\"Species_3\">7</tr_crcraVal>");
    oOut.write("<tr_crcraVal species=\"Species_4\">10</tr_crcraVal>");
    oOut.write("<tr_crcraVal species=\"Species_6\">9.2</tr_crcraVal>");
    oOut.write("</tr_chRichCrownRadAsymp>");
    oOut.write("<tr_chRichCrownRadShape1b>");
    oOut.write("<tr_crcrs1bVal species=\"Species_2\">0.09</tr_crcrs1bVal>");
    oOut.write("<tr_crcrs1bVal species=\"Species_3\">0.1</tr_crcrs1bVal>");
    oOut.write("<tr_crcrs1bVal species=\"Species_4\">0.11</tr_crcrs1bVal>");
    oOut.write("<tr_crcrs1bVal species=\"Species_6\">0.2</tr_crcrs1bVal>");
    oOut.write("</tr_chRichCrownRadShape1b>");
    oOut.write("<tr_chRichCrownRadShape2c>");
    oOut.write("<tr_crcrs2cVal species=\"Species_2\">1.5</tr_crcrs2cVal>");
    oOut.write("<tr_crcrs2cVal species=\"Species_3\">2.4</tr_crcrs2cVal>");
    oOut.write("<tr_crcrs2cVal species=\"Species_4\">2.8</tr_crcrs2cVal>");
    oOut.write("<tr_crcrs2cVal species=\"Species_6\">1.9</tr_crcrs2cVal>");
    oOut.write("</tr_chRichCrownRadShape2c>");
    oOut.write("<tr_chRichCrownHtIntercept>");
    oOut.write("<tr_crchiVal species=\"Species_2\">0.6</tr_crchiVal>");
    oOut.write("<tr_crchiVal species=\"Species_3\">0.5</tr_crchiVal>");
    oOut.write("<tr_crchiVal species=\"Species_4\">0.4</tr_crchiVal>");
    oOut.write("<tr_crchiVal species=\"Species_5\">0.3</tr_crchiVal>");
    oOut.write("</tr_chRichCrownHtIntercept>");
    oOut.write("<tr_chRichCrownHtAsymp>");
    oOut.write("<tr_crchaVal species=\"Species_2\">60</tr_crchaVal>");
    oOut.write("<tr_crchaVal species=\"Species_3\">12</tr_crchaVal>");
    oOut.write("<tr_crchaVal species=\"Species_4\">13</tr_crchaVal>");
    oOut.write("<tr_crchaVal species=\"Species_5\">14</tr_crchaVal>");
    oOut.write("</tr_chRichCrownHtAsymp>");
    oOut.write("<tr_chRichCrownHtShape1b>");
    oOut.write("<tr_crchs1bVal species=\"Species_2\">0.3</tr_crchs1bVal>");
    oOut.write("<tr_crchs1bVal species=\"Species_3\">0.1</tr_crchs1bVal>");
    oOut.write("<tr_crchs1bVal species=\"Species_4\">0.12</tr_crchs1bVal>");
    oOut.write("<tr_crchs1bVal species=\"Species_5\">0.16</tr_crchs1bVal>");
    oOut.write("</tr_chRichCrownHtShape1b>");
    oOut.write("<tr_chRichCrownHtShape2c>");
    oOut.write("<tr_crchs2cVal species=\"Species_2\">3.2</tr_crchs2cVal>");
    oOut.write("<tr_crchs2cVal species=\"Species_3\">4.1</tr_crchs2cVal>");
    oOut.write("<tr_crchs2cVal species=\"Species_4\">2.9</tr_crchs2cVal>");
    oOut.write("<tr_crchs2cVal species=\"Species_5\">3.5</tr_crchs2cVal>");
    oOut.write("</tr_chRichCrownHtShape2c>");
    oOut.write("<tr_saplingPowerA>");
    oOut.write("<tr_sapaVal species=\"Species_4\">1.7019</tr_sapaVal>");
    oOut.write("</tr_saplingPowerA>");
    oOut.write("<tr_saplingPowerB>");
    oOut.write("<tr_sapbVal species=\"Species_4\">0.75</tr_sapbVal>");
    oOut.write("</tr_saplingPowerB>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }

}
