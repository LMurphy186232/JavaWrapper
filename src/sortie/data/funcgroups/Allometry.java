package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import sortie.data.simpletypes.*;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.AllometryParameterEdit;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.TableData;

/**
 * This holds all data relating to allometry.
 * Copyright: Copyright (c) Charles D. Canham 2011
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.1
 *
 * <br>Edit history: 
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class Allometry extends Behavior {


  /**Species-specific - maximum tree height.*/
  protected ModelVector mp_fMaxCanopyHeight = new ModelVector(
      "Maximum Tree Height, in meters", "tr_canopyHeight", "tr_chVal", 0,
      ModelVector.FLOAT, true);

  /**Species-specific - slope of asymptotic height.*/
  protected ModelVector mp_fSlopeOfAsymptoticHeight = new ModelVector(
      "Slope of Asymptotic Height", "tr_slopeOfAsymHeight", "tr_soahVal",
      0, ModelVector.FLOAT, true);

  /**Species-specific - exponent in crown radius equation.*/
  protected ModelVector mp_fCrownRadExp = new ModelVector(
      "Crown Radius Exponent", "tr_stdCrownRadExp", "tr_screVal", 0,
      ModelVector.FLOAT, true);

  /**Species-specific - exponent in crown height equation.*/
  protected ModelVector mp_fCrownDepthExp = new ModelVector(
      "Crown Height Exponent", "tr_stdCrownHtExp", "tr_scheVal", 0,
      ModelVector.FLOAT, true);

  /**Species-specific - slope of asymptotic crown radius*/
  protected ModelVector mp_fSlopeOfAsympCrownRad = new ModelVector(
      "Slope of Asymptotic Crown Radius", "tr_stdAsympCrownRad", "tr_sacrVal",
      0, ModelVector.FLOAT, true);
  
  /**Species-specific - maximum crown radius in standard equation*/
  protected ModelVector mp_fMaxCrownRad = new ModelVector(
      "Maximum Crown Radius (Standard) (m)", "tr_stdMaxCrownRad", "tr_smcrVal",
      0, ModelVector.FLOAT, true);

  /**Species-specific - slope of the asymptotic crown height*/
  protected ModelVector mp_fSlopeOfAsympCrownDpth = new ModelVector(
      "Slope of Asymptotic Crown Height", "tr_stdAsympCrownHt",
      "tr_sachVal", 0, ModelVector.FLOAT, true);

  /**Species-specific - slope of the height-diam10 relationship.*/
  protected ModelVector mp_fSlopeOfHeightDiam10 = new ModelVector(
      "Slope of Height-Diameter at 10 cm Relationship",
      "tr_slopeOfHeight-Diam10", "tr_sohdVal", 0, ModelVector.FLOAT, true);

  /**Species-specific - slope of relationship of diam10 to dbh.*/
  protected ModelVector mp_fDiam10ToDbhSlope = new ModelVector(
      "Slope of DBH to Diameter at 10 cm Relationship",
      "tr_conversionDiam10ToDBH",
      "tr_cdtdVal", 0, ModelVector.FLOAT, true);

  /**Species-specific - intercept of relationship of diam10 to dbh.*/
  protected ModelVector mp_fDiam10ToDbhIntercept = new ModelVector(
      "Intercept of DBH to Diameter at 10 cm Relationship",
      "tr_interceptDiam10ToDBH", "tr_idtdVal", 0, ModelVector.FLOAT, true);

  /**Slope of linear height-DBH relationship for adults - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fAdultLinearSlope = new ModelVector(
      "Adult Linear Function Slope", "tr_adultLinearSlope",
      "tr_alsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of linear height-DBH relationship for adults - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fAdultLinearIntercept = new ModelVector(
      "Adult Linear Function Intercept", "tr_adultLinearIntercept",
      "tr_aliVal", 0, ModelVector.FLOAT, true);

  /**Slope of linear height-DBH relationship for saplings - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fSaplingLinearSlope = new ModelVector(
      "Sapling Linear Function Slope", "tr_saplingLinearSlope",
      "tr_salsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of linear height-DBH relationship for saplings - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fSaplingLinearIntercept = new ModelVector(
      "Sapling Linear Function Intercept", "tr_saplingLinearIntercept",
      "tr_saliVal", 0, ModelVector.FLOAT, true);

  /**Slope of linear height-DBH relationship for seedlings - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fSeedlingLinearSlope = new ModelVector(
      "Seedling Linear Function Slope", "tr_seedlingLinearSlope",
      "tr_selsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of linear height-DBH relationship for seedlings - not
          required if the linear relationship isn't used*/
  protected ModelVector mp_fSeedlingLinearIntercept = new ModelVector(
      "Seedling Linear Function Intercept", "tr_seedlingLinearIntercept",
      "tr_seliVal", 0, ModelVector.FLOAT, true);

  /**Slope of reverse linear height-DBH relationship for adults - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fAdultReverseLinearSlope = new ModelVector(
      "Adult Reverse Linear Function Slope", "tr_adultReverseLinearSlope",
      "tr_arlsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of reverse linear height-DBH relationship for adults - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fAdultReverseLinearIntercept = new ModelVector(
      "Adult Reverse Linear Function Intercept",
      "tr_adultReverseLinearIntercept", "tr_arliVal", 0, ModelVector.FLOAT, true);

  /**Slope of reverse linear height-DBH relationship for saplings - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fSaplingReverseLinearSlope = new ModelVector(
      "Sapling Reverse Linear Function Slope", "tr_saplingReverseLinearSlope",
      "tr_sarlsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of reverse linear height-DBH relationship for saplings - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fSaplingReverseLinearIntercept = new ModelVector(
      "Sapling Reverse Linear Function Intercept",
      "tr_saplingReverseLinearIntercept", "tr_sarliVal", 0, ModelVector.FLOAT, true);

  /**Slope of reverse linear height-DBH relationship for seedlings - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fSeedlingReverseLinearSlope = new ModelVector(
      "Seedling Reverse Linear Function Slope", "tr_seedlingReverseLinearSlope",
      "tr_serlsVal", 0, ModelVector.FLOAT, true);

  /**Intercept of reverse linear height-DBH relationship for seedlings - not
          required if the reverse linear relationship isn't used*/
  protected ModelVector mp_fSeedlingReverseLinearIntercept = new ModelVector(
      "Seedling Reverse Linear Function Intercept",
      "tr_seedlingReverseLinearIntercept", "tr_serliVal", 0, ModelVector.FLOAT, true);

  /**Which relationship to use to relate height to diam10 for
          seedlings - 0 = standard, 1 = linear, 2 = reverse linear*/
  protected ModelVector mp_iWhatSeedlingHDFunction = new ModelVector(
      "Seedling Height-Diameter Function",
      "tr_whatSeedlingHeightDiam", "tr_wsehdVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate height to DBH for
          saplings - 0 = standard, 1 = linear, 2 = reverse linear*/
  protected ModelVector mp_iWhatSaplingHDFunction = new ModelVector(
      "Sapling Height-Diameter Function",
      "tr_whatSaplingHeightDiam", "tr_wsahdVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate height to DBH for
          adults - 0 = standard, 1 = linear, 2 = reverse linear*/
  protected ModelVector mp_iWhatAdultHDFunction = new ModelVector(
      "Adult Height-Diameter Function",
      "tr_whatAdultHeightDiam", "tr_wahdVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate DBH to crown radius for
          adults - 0 = standard, 1 = Chapman-Richards, 2 = non-spatial
          density dependent*/
  protected ModelVector mp_iWhatAdultCRDFunction = new ModelVector(
      "Adult Crown Radius-Diameter Function",
      "tr_whatAdultCrownRadDiam", "tr_wacrdVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate DBH to crown radius for
          saplings - 0 = standard, 1 = Chapman-Richards*/
  protected ModelVector mp_iWhatSaplingCRDFunction = new ModelVector(
      "Sapling Crown Radius-Diameter Function",
      "tr_whatSaplingCrownRadDiam", "tr_wscrdVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate height to crown depth for
          adults - 0 = standard, 1 = Chapman-Richards, 2 = non-spatial
          density dependent*/
  protected ModelVector mp_iWhatAdultCDHFunction = new ModelVector(
      "Adult Crown Depth-Height Function",
      "tr_whatAdultCrownHeightHeight", "tr_wachhVal", 0, ModelVector.MODEL_ENUM, true);

  /**Which relationship to use to relate height to crown depth for
          saplings - 0 = standard, 1 = Chapman-Richards*/
  protected ModelVector mp_iWhatSaplingCDHFunction = new ModelVector(
      "Sapling Crown Depth-Height Function",
      "tr_whatSaplingCrownHeightHeight", "tr_wschhVal", 0,
      ModelVector.MODEL_ENUM, true);

  /**Intercept of Chapman-Richards crown radius-DBH relationship - not
   * required if the Chapman-Richards crown radius relationship isn't used*/
  protected ModelVector mp_fCRCrownRadIntercept = new ModelVector(
      "Chapman-Richards Crown Radius Intercept",
      "tr_chRichCrownRadIntercept", "tr_crcriVal", 0, ModelVector.FLOAT, true);

  /**Asymptotic crown radius of Chapman-Richards crown radius-DBH
   * relationship - not required if the Chapman-Richards crown radius
   * relationship isn't used*/
  protected ModelVector mp_fCRAsympCrownRad = new ModelVector(
      "Chapman-Richards Asymptotic Crown Radius",
      "tr_chRichCrownRadAsymp", "tr_crcraVal", 0, ModelVector.FLOAT, true);

  /**Shape parameter 1 (b) of Chapman-Richards crown radius-DBH relationship
   * - not required if the C-R crown radius relationship isn't used*/
  protected ModelVector mp_fCRCrownRadShape1 = new ModelVector(
      "Chapman-Richards Crown Radius Shape 1 (b)",
      "tr_chRichCrownRadShape1b", "tr_crcrs1bVal", 0, ModelVector.FLOAT, true);

  /**Shape parameter 2 (c) of Chapman-Richards crown radius-DBH relationship
   * - not required if the C-R crown radius relationship isn't used*/
  protected ModelVector mp_fCRCrownRadShape2 = new ModelVector(
      "Chapman-Richards Crown Radius Shape 2 (c)",
      "tr_chRichCrownRadShape2c", "tr_crcrs2cVal", 0, ModelVector.FLOAT, true);

  /**Intercept of Chapman-Richards crown depth-height relationship - not
   * required if the Chapman-Richards crown depth relationship isn't used*/
  protected ModelVector mp_fCRCrownHtIntercept = new ModelVector(
      "Chapman-Richards Crown Depth Intercept",
      "tr_chRichCrownHtIntercept", "tr_crchiVal", 0, ModelVector.FLOAT, true);

  /**Asymptotic crown radius of Chapman-Richards crown depth-height
   * relationship - not required if the Chapman-Richards crown depth
   * relationship isn't used*/
  protected ModelVector mp_fCRAsympCrownHt = new ModelVector(
      "Chapman-Richards Asymptotic Crown Depth",
      "tr_chRichCrownHtAsymp", "tr_crchaVal", 0, ModelVector.FLOAT, true);

  /**Shape parameter 1 (b) of Chapman-Richards crown depth-height relationship
   * - not required if the C-R crown depth relationship isn't used*/
  protected ModelVector mp_fCRCrownHtShape1 = new ModelVector(
      "Chapman-Richards Crown Depth Shape 1 (b)",
      "tr_chRichCrownHtShape1b", "tr_crchs1bVal", 0, ModelVector.FLOAT, true);

  /**Shape parameter 2 (c) of Chapman-Richards crown depth-height relationship
   * - not required if the C-R crown depth relationship isn't used*/
  protected ModelVector mp_fCRCrownHtShape2 = new ModelVector(
      "Chapman-Richards Crown Depth Shape 2 (c)",
      "tr_chRichCrownHtShape2c", "tr_crchs2cVal", 0, ModelVector.FLOAT, true);

  /** "a" parameter of power function height-diam relationship - not required
   * if this function isn't used*/
  protected ModelVector mp_fPowerA = new ModelVector(
      "Power Function \"a\"", "tr_saplingPowerA", "tr_sapaVal", 0,
      ModelVector.FLOAT, true);

  /** "b" parameter of power function height-diam relationship - not required
   * if this function isn't used*/
  protected ModelVector mp_fPowerB = new ModelVector(
      "Power Function Exponent \"b\"", "tr_saplingPowerB", "tr_sapbVal", 0,
      ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "a" */
  protected ModelVector mp_fNonSpatDensDepInstCDA = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"a\"", 
      "tr_nonSpatDensDepInstCHA", "tr_nsddichaVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "b" */
  protected ModelVector mp_fNonSpatDensDepInstCDB = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"b\"", 
      "tr_nonSpatDensDepInstCHB", "tr_nsddichbVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "c" */
  protected ModelVector mp_fNonSpatDensDepInstCDC = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"c\"", 
      "tr_nonSpatDensDepInstCHC", "tr_nsddichcVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "d" */
  protected ModelVector mp_fNonSpatDensDepInstCDD = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"d\"", 
      "tr_nonSpatDensDepInstCHD", "tr_nsddichdVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "e" */
  protected ModelVector mp_fNonSpatDensDepInstCDE = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"e\"", 
      "tr_nonSpatDensDepInstCHE", "tr_nsddicheVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "f" */
  protected ModelVector mp_fNonSpatDensDepInstCDF = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"f\"", 
      "tr_nonSpatDensDepInstCHF", "tr_nsddichfVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "g" */
  protected ModelVector mp_fNonSpatDensDepInstCDG = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"g\"", 
      "tr_nonSpatDensDepInstCHG", "tr_nsddichgVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "h" */
  protected ModelVector mp_fNonSpatDensDepInstCDH = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"h\"", 
      "tr_nonSpatDensDepInstCHH", "tr_nsddichhVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "i" */
  protected ModelVector mp_fNonSpatDensDepInstCDI = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"i\"", 
      "tr_nonSpatDensDepInstCHI", "tr_nsddichiVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown depth "j" */
  protected ModelVector mp_fNonSpatDensDepInstCDJ = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Depth \"j\"", 
      "tr_nonSpatDensDepInstCHJ", "tr_nsddichjVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "D1" */
  protected ModelVector mp_fNonSpatExpDensDepCRD1 = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"D1\"", 
      "tr_nonSpatExpDensDepCRD1", "tr_nseddcrd1Val", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "a" */
  protected ModelVector mp_fNonSpatExpDensDepCRA = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"a\"", 
      "tr_nonSpatExpDensDepCRA", "tr_nseddcraVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "b" */
  protected ModelVector mp_fNonSpatExpDensDepCRB = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"b\"", 
      "tr_nonSpatExpDensDepCRB", "tr_nseddcrbVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "c" */
  protected ModelVector mp_fNonSpatExpDensDepCRC = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"c\"", 
      "tr_nonSpatExpDensDepCRC", "tr_nseddcrcVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "d" */
  protected ModelVector mp_fNonSpatExpDensDepCRD = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"d\"", 
      "tr_nonSpatExpDensDepCRD", "tr_nseddcrdVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "e" */
  protected ModelVector mp_fNonSpatExpDensDepCRE = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"e\"", 
      "tr_nonSpatExpDensDepCRE", "tr_nseddcreVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial exponential density dependent crown radius "f" */
  protected ModelVector mp_fNonSpatExpDensDepCRF = new ModelVector(
      "Non-Spatial Exp. Density Dep. Crown Radius \"f\"", 
      "tr_nonSpatExpDensDepCRF", "tr_nseddcrfVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "a" */
  protected ModelVector mp_fNonSpatDensDepInstCRA = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"a\"", 
      "tr_nonSpatDensDepInstCRA", "tr_nsddicraVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "b" */
  protected ModelVector mp_fNonSpatDensDepInstCRB = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"b\"", 
      "tr_nonSpatDensDepInstCRB", "tr_nsddicrbVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "c" */
  protected ModelVector mp_fNonSpatDensDepInstCRC = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"c\"", 
      "tr_nonSpatDensDepInstCRC", "tr_nsddicrcVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "d" */
  protected ModelVector mp_fNonSpatDensDepInstCRD = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"d\"", 
      "tr_nonSpatDensDepInstCRD", "tr_nsddicrdVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "e" */
  protected ModelVector mp_fNonSpatDensDepInstCRE = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"e\"", 
      "tr_nonSpatDensDepInstCRE", "tr_nsddicreVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "f" */
  protected ModelVector mp_fNonSpatDensDepInstCRF = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"f\"", 
      "tr_nonSpatDensDepInstCRF", "tr_nsddicrfVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "g" */
  protected ModelVector mp_fNonSpatDensDepInstCRG = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"g\"", 
      "tr_nonSpatDensDepInstCRG", "tr_nsddicrgVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "h" */
  protected ModelVector mp_fNonSpatDensDepInstCRH = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"h\"", 
      "tr_nonSpatDensDepInstCRH", "tr_nsddicrhVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "i" */
  protected ModelVector mp_fNonSpatDensDepInstCRI = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"i\"", 
      "tr_nonSpatDensDepInstCRI", "tr_nsddicriVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial density dependent instrumental crown radius "j" */
  protected ModelVector mp_fNonSpatDensDepInstCRJ = new ModelVector(
      "Non-Spatial Density Dep. Inst. Crown Radius \"j\"", 
      "tr_nonSpatDensDepInstCRJ", "tr_nsddicrjVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "a" */
  protected ModelVector mp_fNonSpatLogDensDepCRDA = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"a\"", 
      "tr_nonSpatLogDensDepCHA", "tr_nslddchaVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "b" */
  protected ModelVector mp_fNonSpatLogDensDepCRDB = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"b\"", 
      "tr_nonSpatLogDensDepCHB", "tr_nslddchbVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "c" */
  protected ModelVector mp_fNonSpatLogDensDepCRDC = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"c\"", 
      "tr_nonSpatLogDensDepCHC", "tr_nslddchcVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "d" */
  protected ModelVector mp_fNonSpatLogDensDepCRDD = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"d\"", 
      "tr_nonSpatLogDensDepCHD", "tr_nslddchdVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "e" */
  protected ModelVector mp_fNonSpatLogDensDepCRDE = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"e\"", 
      "tr_nonSpatLogDensDepCHE", "tr_nslddcheVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "f" */
  protected ModelVector mp_fNonSpatLogDensDepCRDF = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"f\"", 
      "tr_nonSpatLogDensDepCHF", "tr_nslddchfVal", 0, ModelVector.FLOAT, true);

  /** Non-spatial logistic density dependent crown depth "g" */
  protected ModelVector mp_fNonSpatLogDensDepCRDG = new ModelVector(
      "Non-Spatial Log. Density Dep. Crown Depth \"g\"", 
      "tr_nonSpatLogDensDepCHG", "tr_nslddchgVal", 0, ModelVector.FLOAT, true);

  /** NCI crown radius - Maximum potential crown radius */
  protected ModelVector mp_fNCIMaxCrownRadius = new ModelVector(
      "NCI Crown Radius - Max Potential Radius (m)", 
      "tr_nciCRMaxCrownRadius", "tr_ncrmcrVal", 0, ModelVector.FLOAT);

  /**NCI crown radius alpha*/
  protected ModelVector mp_fNCICRAlpha = new ModelVector(
      "NCI Crown Radius - Alpha", 
      "tr_nciCRAlpha", "tr_ncraVal", 0, ModelVector.FLOAT);

  /**NCI crown radius beta*/
  protected ModelVector mp_fNCICRBeta = new ModelVector(
      "NCI Crown Radius - Beta", 
      "tr_nciCRBeta", "tr_ncrbVal", 0, ModelVector.FLOAT);

  /**NCI crown radius gamma*/
  protected ModelVector mp_fNCICRGamma = new ModelVector(
      "NCI Crown Radius - Gamma", 
      "tr_nciCRGamma", "tr_ncrgVal", 0, ModelVector.FLOAT);

  /**NCI crown radius maximum search distance for neighbors, in meters*/
  protected ModelVector mp_fNCICRMaxCrowdingRadius = new ModelVector(
      "NCI Crown Radius - Max Search Distance for Neighbors (m)", 
      "tr_nciCRMaxCrowdingRadius", "tr_ncrmcrVal", 0, ModelVector.FLOAT);

  /**NCI crown radius crowding effect n*/
  protected ModelVector mp_fNCICRN = new ModelVector(
      "NCI Crown Radius - Crowding Effect \"n\"", 
      "tr_nciCRCrowdingN", "tr_nccrnVal", 0, ModelVector.FLOAT);

  /**NCI crown radius size effect d*/
  protected ModelVector mp_fNCICRD = new ModelVector(
      "NCI Crown Radius - Size Effect \"d\"", 
      "tr_nciCRSizeEffectD", "tr_ncrsedVal", 0, ModelVector.FLOAT);

  /**The minimum DBH, in cm, of neighbors to be included in NCI calculations.
   * Array is sized total number of species.*/
  protected ModelVector mp_fNCICRMinNeighborDBH = new ModelVector(
      "NCI Crown Radius - Minimum Neighbor DBH (cm)", 
      "tr_nciCRMinNeighborDBH", "tr_ncrmndVal", 0, ModelVector.FLOAT, true);

  /**Maximum crown depth value*/
  protected ModelVector mp_fNCIMaxCrownDepth = new ModelVector(
      "NCI Crown Depth - Max Potential Depth (m)", 
      "tr_nciCDMaxCrownDepth", "tr_ncdmcrVal", 0, ModelVector.FLOAT);

  /**NCI crown depth alpha*/
  protected ModelVector mp_fNCICDAlpha = new ModelVector(
      "NCI Crown Depth - Alpha", 
      "tr_nciCDAlpha", "tr_ncdaVal", 0, ModelVector.FLOAT);

  /**NCI crown depth beta*/
  protected ModelVector mp_fNCICDBeta = new ModelVector(
      "NCI Crown Depth - Beta", 
      "tr_nciCDBeta", "tr_ncdbVal", 0, ModelVector.FLOAT);

  /**NCI crown depth gamma*/
  protected ModelVector mp_fNCICDGamma = new ModelVector(
      "NCI Crown Depth - Gamma", 
      "tr_nciCDGamma", "tr_ncdgVal", 0, ModelVector.FLOAT);

  /**NCI crown depth maximum search distance for neighbors, in meters.*/
  protected ModelVector mp_fNCICDMaxCrowdingRadius = new ModelVector(
      "NCI Crown Depth - Max Search Distance for Neighbors (m)", 
      "tr_nciCDMaxCrowdingRadius", "tr_ncdmcrVal", 0, ModelVector.FLOAT);

  /**NCI crown depth crowding effect n*/
  protected ModelVector mp_fNCICDN = new ModelVector(
      "NCI Crown Depth - Crowding Effect \"n\"", 
      "tr_nciCDCrowdingN", "tr_nccdnVal", 0, ModelVector.FLOAT);

  /**NCI crown depth size effect d*/
  protected ModelVector mp_fNCICDD = new ModelVector(
      "NCI Crown Depth - Size Effect \"d\"", 
      "tr_nciCDSizeEffectD", "tr_ncdsedVal", 0, ModelVector.FLOAT);

  /**The minimum DBH, in cm, of neighbors to be included in NCI calculations.
   * Array is sized total number of species.*/
  protected ModelVector mp_fNCICDMinNeighborDBH = new ModelVector(
      "NCI Crown Depth - Minimum Neighbor DBH (cm)", 
      "tr_nciCDMinNeighborDBH", "tr_ncdmndVal", 0, ModelVector.FLOAT, true);



  /**
   * Constructor
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public Allometry(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "allometry.allometry");

    m_bMustHaveTrees = false;
    m_bCanBeDuplicated = false;
    m_iBehaviorSetupType = setupType.custom_display;

    mp_oAllData.add(mp_iWhatAdultHDFunction);
    mp_oAllData.add(mp_iWhatSaplingHDFunction);
    mp_oAllData.add(mp_iWhatSeedlingHDFunction);
    mp_oAllData.add(mp_iWhatAdultCRDFunction);
    mp_oAllData.add(mp_iWhatSaplingCRDFunction);
    mp_oAllData.add(mp_iWhatAdultCDHFunction);
    mp_oAllData.add(mp_iWhatSaplingCDHFunction);
    mp_oAllData.add(mp_fMaxCanopyHeight);
    mp_oAllData.add(mp_fSlopeOfAsympCrownRad);
    mp_oAllData.add(mp_fCrownRadExp);
    mp_oAllData.add(mp_fMaxCrownRad);
    mp_oAllData.add(mp_fSlopeOfAsympCrownDpth);
    mp_oAllData.add(mp_fCrownDepthExp);
    mp_oAllData.add(mp_fDiam10ToDbhSlope);
    mp_oAllData.add(mp_fDiam10ToDbhIntercept);
    mp_oAllData.add(mp_fSlopeOfAsymptoticHeight);
    mp_oAllData.add(mp_fSlopeOfHeightDiam10);
    mp_oAllData.add(mp_fAdultLinearSlope);
    mp_oAllData.add(mp_fAdultLinearIntercept);
    mp_oAllData.add(mp_fAdultReverseLinearSlope);
    mp_oAllData.add(mp_fAdultReverseLinearIntercept);
    mp_oAllData.add(mp_fSaplingLinearSlope);
    mp_oAllData.add(mp_fSaplingLinearIntercept);
    mp_oAllData.add(mp_fSaplingReverseLinearSlope);
    mp_oAllData.add(mp_fSaplingReverseLinearIntercept);
    mp_oAllData.add(mp_fPowerA);
    mp_oAllData.add(mp_fPowerB);
    mp_oAllData.add(mp_fSeedlingLinearSlope);
    mp_oAllData.add(mp_fSeedlingLinearIntercept);
    mp_oAllData.add(mp_fSeedlingReverseLinearSlope);
    mp_oAllData.add(mp_fSeedlingReverseLinearIntercept);
    mp_oAllData.add(mp_fCRCrownRadIntercept);
    mp_oAllData.add(mp_fCRAsympCrownRad);
    mp_oAllData.add(mp_fCRCrownRadShape1);
    mp_oAllData.add(mp_fCRCrownRadShape2);
    mp_oAllData.add(mp_fCRCrownHtIntercept);
    mp_oAllData.add(mp_fCRAsympCrownHt);
    mp_oAllData.add(mp_fCRCrownHtShape1);
    mp_oAllData.add(mp_fCRCrownHtShape2);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDA);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDB);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDC);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDD);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDE);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDF);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDG);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDH);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDI);
    mp_oAllData.add(mp_fNonSpatDensDepInstCDJ);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRD1);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRA);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRB);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRC);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRD);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRE);
    mp_oAllData.add(mp_fNonSpatExpDensDepCRF);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRA);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRB);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRC);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRD);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRE);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRF);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRG);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRD);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRH);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRI);
    mp_oAllData.add(mp_fNonSpatDensDepInstCRJ);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDA);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDB);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDC);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDD);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDE);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDF);
    mp_oAllData.add(mp_fNonSpatLogDensDepCRDG);
    mp_oAllData.add(mp_fNCIMaxCrownRadius);
    mp_oAllData.add(mp_fNCICRAlpha);
    mp_oAllData.add(mp_fNCICRBeta);
    mp_oAllData.add(mp_fNCICRGamma);
    mp_oAllData.add(mp_fNCICRMaxCrowdingRadius);
    mp_oAllData.add(mp_fNCICRN);
    mp_oAllData.add(mp_fNCICRD);
    mp_oAllData.add(mp_fNCICRMinNeighborDBH);
    mp_oAllData.add(mp_fNCIMaxCrownDepth);
    mp_oAllData.add(mp_fNCICDAlpha);
    mp_oAllData.add(mp_fNCICDBeta);
    mp_oAllData.add(mp_fNCICDGamma);
    mp_oAllData.add(mp_fNCICDMaxCrowdingRadius);
    mp_oAllData.add(mp_fNCICDN);
    mp_oAllData.add(mp_fNCICDD);
    mp_oAllData.add(mp_fNCICDMinNeighborDBH);
  }

  /**
   * Does setup.  Sets up the enums for the function lists and the NCI lambdas.
   * @param oPop TreePopulation object.
   * @throws ModelException passing through from called functions.
   */
  protected void doSetup(TreePopulation oPop) throws ModelException {
    ModelEnum oEnum;
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    //boolean bAlreadyLambdas = false;

    //Set up the "which function" vector and default all functions to standard
    for (i = 0; i < iNumSpecies; i++) {
      oEnum = new ModelEnum(new int[] {2, 1, 0}, 
          new String[] {"Reverse Linear", "Linear", "Standard"}, 
          "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatAdultHDFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
          new String[] {"Power", "Reverse Linear", "Linear", "Standard"},
          "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatSaplingHDFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {2, 1, 0}, 
          new String[] {"Reverse Linear", "Linear", "Standard"},
          "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatSeedlingHDFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
          new String[] {"NCI", "Non-Spatial Density Dependent", 
          "Chapman-Richards", "Standard"}, "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatAdultCRDFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {3, 1, 0}, 
          new String[] {"NCI", "Chapman-Richards", "Standard"}, 
          "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatSaplingCRDFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
          new String[] {"NCI", "Non-Spatial Density Dependent", 
          "Chapman-Richards", "Standard"}, "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatAdultCDHFunction.getValue().add(oEnum);

      oEnum = new ModelEnum(new int[] {3, 1, 0}, 
          new String[] {"NCI", "Chapman-Richards", "Standard"}, "Function used", "");
      oEnum.setValue("Standard");
      mp_iWhatSaplingCDHFunction.getValue().add(oEnum);
    }

    //Default diam10 to DBH intercept to 0
    for (i = 0; i < iNumSpecies; i++) {
      mp_fDiam10ToDbhIntercept.getValue().add(Float.valueOf(0));
    }
    
    //Default max standad crown radius to 10
    for (i = 0; i < iNumSpecies; i++) {
      mp_fMaxCrownRad.getValue().add(Float.valueOf(10));
    }

    //Add lambdas for crown radius 

    //See if we already had lambdas
    /*for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown radius lambda") > -1) {
        bAlreadyLambdas = true;
        break;
      }
    }

    if (bAlreadyLambdas == false) {*/

      for (i = 0; i < iNumSpecies; i++) {
        String sSpName = oPop.getSpeciesNameFromCode(i);
        ModelVector oVector = new ModelVector("NCI Crown Radius Lambda for "
            + sSpName.replace('_', ' ') + " Neighbors",
            "tr_nciCR" + sSpName + "NeighborLambda", "tr_ncrlVal",
            0, ModelVector.FLOAT);

        mp_oAllData.add(oVector);
      }
    /*}
    else {
      
    }*/

    //Add lambdas for crown depth 

    //See if we already had lambdas
    /*bAlreadyLambdas = false;
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown depth lambda") > -1) {
        bAlreadyLambdas = true;
        break;
      }
    }

    if (bAlreadyLambdas == false) {*/

      for (i = 0; i < iNumSpecies; i++) {
        String sSpName = oPop.getSpeciesNameFromCode(i);
        ModelVector oVector = new ModelVector("NCI Crown Depth Lambda for "
            + sSpName.replace('_', ' ') + " Neighbors",
            "tr_nciCD" + sSpName + "NeighborLambda", "tr_ncdlVal",
            0, ModelVector.FLOAT);

        mp_oAllData.add(oVector);
      }
    /*}
    else {
      
    }*/
  }

  /**
   * This makes sure all data is valid and can be used to run the model.
   * @throws ModelException in any of the following cases:
   * <ul>
   * <li> There is any value less than or equal to zero in any of the following
   * vectors, when used:
   * <ul>
   * <li>mp_fSlopeOfAsympCrownRad</li>
   * <li>mp_fDiam10ToDbhConversion</li>
   * <li>mp_fSlopeOfAsympCrownDpth</li>
   * <li>Max crown radius</li>
   * <li>mp_fMaxCanopyHeight</li>
   * <li>mp_fSlopeOfAsymptoticHeight</li>
   * </ul></li>
   * <li>A value in any slope is 0 (if it is being used)</li>
   * </ul>
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    boolean[] p_bApplies = new boolean[iNumSpecies];

    //Validate the size of the species-specific vectors
    ValidationHelpers.makeSureRightSize(mp_fCrownRadExp, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fSlopeOfAsympCrownRad, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fDiam10ToDbhSlope, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fDiam10ToDbhIntercept, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fSlopeOfAsympCrownDpth, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fCrownDepthExp, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fMaxCanopyHeight, iNumSpecies);

    //Slope of asymptotic crown depth should not be negative
    for (i = 0; i < p_bApplies.length; i++) {
      if (1 ==
          ( (ModelEnum) mp_iWhatAdultCDHFunction.getValue().get(i)).getValue() &&
          1 ==
          ( (ModelEnum) mp_iWhatSaplingCDHFunction.getValue().get(i)).getValue()) {
        p_bApplies[i] = false;
      }
      else {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllPositive(mp_fSlopeOfAsympCrownDpth, p_bApplies);

    ValidationHelpers.makeSureAllPositive(mp_fMaxCanopyHeight);

    //Slope of asymptotic crown radius should not be negative
    for (i = 0; i < p_bApplies.length; i++) {
      if (1 ==
          ( (ModelEnum) mp_iWhatAdultCRDFunction.getValue().get(i)).getValue() &&
          1 ==
          ( (ModelEnum) mp_iWhatSaplingCRDFunction.getValue().get(i)).getValue()) {
        p_bApplies[i] = false;
      }
      else {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllPositive(mp_fSlopeOfAsympCrownRad, p_bApplies);
    ValidationHelpers.makeSureAllPositive(mp_fMaxCrownRad, p_bApplies);

    ValidationHelpers.makeSureAllPositive(mp_fDiam10ToDbhSlope);
    ValidationHelpers.makeSureAllPositive(mp_fMaxCanopyHeight);

    //Make sure the values for asymptotic height that are used are positive
    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatAdultHDFunction.getValue().get(i)).
          getValue() == 0 ||
          ( (ModelEnum) mp_iWhatSaplingHDFunction.getValue().get(i)).
          getValue() == 0) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllPositive(mp_fSlopeOfAsymptoticHeight, p_bApplies);

    //Make sure adult slopes that are used are non-zero
    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatAdultHDFunction.getValue().get(i)).
          getValue() == 1) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fAdultLinearSlope, p_bApplies);

    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatAdultHDFunction.getValue().get(i)).
          getValue() == 2) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fAdultReverseLinearSlope, p_bApplies);

    //Make sure sapling slopes that are used are non-zero
    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatSaplingHDFunction.getValue().get(i)).
          getValue() == 1) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fSaplingLinearSlope, p_bApplies);

    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatSaplingHDFunction.getValue().get(i)).
          getValue() == 2) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fSaplingReverseLinearSlope, p_bApplies);

    //Make sure seedling slopes that are used are non-zero
    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatSeedlingHDFunction.getValue().get(i)).
          getValue() == 1) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fSeedlingLinearSlope, p_bApplies);

    for (i = 0; i < p_bApplies.length; i++) {
      p_bApplies[i] = false;
    }
    for (i = 0; i < iNumSpecies; i++) {
      if ( ( (ModelEnum) mp_iWhatSeedlingHDFunction.getValue().get(i)).
          getValue() == 2) {
        p_bApplies[i] = true;
      }
    }
    ValidationHelpers.makeSureAllNonZero(mp_fSeedlingReverseLinearSlope, p_bApplies);
  }

  /**
   * Updates the lambda neighbor names when a species name is changed.
   * @param sOldSpecies String Old name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @param sNewSpecies String New name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   */
  public void changeOfSpeciesName (String sOldSpecies, String sNewSpecies) {
    int i;

    sOldSpecies = sOldSpecies.replace('_', ' ');

    //Find the lambdas in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sOldSpecies) > -1 && sKey.indexOf("nci crown radius lambda") > -1) {
        ModelVector oVector = (ModelVector) oData;
        oVector.setDescriptor("NCI Crown Radius Lambda for "
            + sNewSpecies.replace('_', ' ') + " Neighbors");
        oVector.setXMLTag("tr_nciCR" + sNewSpecies + "NeighborLambda");
      }
      if (sKey.indexOf(sOldSpecies) > -1 && sKey.indexOf("nci crown depth lambda") > -1) {
        ModelVector oVector = (ModelVector) oData;
        oVector.setDescriptor("NCI Crown Depth Lambda for "
            + sNewSpecies.replace('_', ' ') + " Neighbors");
        oVector.setXMLTag("tr_nciCD" + sNewSpecies + "NeighborLambda");
      }
    }
  }

  /**
   * Updates the lambda when a species is copied. The lambdas for that neighbor
   * are made identical to those being copied as well as entries for species
   * within the lambda.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelVector oCopyFrom = null, oCopyTo = null;
    ModelData oData;
    Float f1, f2;
    String sCopyFrom = oPop.getSpeciesNameFromCode(iSpeciesCopyFrom).replace('_', ' '),
        sCopyTo = oPop.getSpeciesNameFromCode(iSpeciesCopyTo).replace('_', ' ');
    int i;

    //Find the lambdas in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sCopyFrom) > -1 && sKey.toLowerCase().indexOf("nci crown radius lambda") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 && sKey.toLowerCase().indexOf("nci crown radius lambda") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Allometry could not find NCI lambdas for neighbor"
              + " species when copying species."));
    }

    //Do it differently depending on whether there are existing values or not
    //in the target array
    if (oCopyTo.getValue().size() > 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      }
    }

    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sCopyFrom) > -1 && sKey.toLowerCase().indexOf("nci crown depth lambda") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 && sKey.toLowerCase().indexOf("nci crown depth lambda") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Allometry could not find NCI lambdas for neighbor"
              + " species when copying species."));
    }

    //Do it differently depending on whether there are existing values or not
    //in the target array
    if (oCopyTo.getValue().size() > 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      }
    }

    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
  }

  /**
   * Overridden to set up enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);

    ModelEnum oEnum;
    int iNumSpecies = p_sNewSpecies.length, i;

    //This is a change of species - add enums to any null spots
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iWhatAdultHDFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {
            2, 1, 0}, new String[] {"Reverse Linear",
            "Linear", "Standard"}, "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatAdultHDFunction.getValue().remove(i);
        mp_iWhatAdultHDFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatSaplingHDFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
            new String[] {"Power", "Reverse Linear", "Linear", "Standard"},
            "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatSaplingHDFunction.getValue().remove(i);
        mp_iWhatSaplingHDFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatSeedlingHDFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {2, 1, 0}, 
            new String[] {"Reverse Linear", "Linear", "Standard"},
            "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatSeedlingHDFunction.getValue().remove(i);
        mp_iWhatSeedlingHDFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatAdultCRDFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
            new String[] {"NCI", "Non-Spatial Density Dependent", 
            "Chapman-Richards", "Standard"}, "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatAdultCRDFunction.getValue().remove(i);
        mp_iWhatAdultCRDFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatSaplingCRDFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {3, 1, 0}, 
            new String[] {"NCI", "Chapman-Richards", "Standard"}, 
            "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatSaplingCRDFunction.getValue().remove(i);
        mp_iWhatSaplingCRDFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatAdultCDHFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {3, 2, 1, 0}, 
            new String[] {"NCI", "Non-Spatial Density Dependent", 
            "Chapman-Richards", "Standard"}, "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatAdultCDHFunction.getValue().remove(i);
        mp_iWhatAdultCDHFunction.getValue().add(i, oEnum);
      }

      if (null == mp_iWhatSaplingCDHFunction.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {3, 1, 0}, 
            new String[] {"NCI", "Chapman-Richards", "Standard"}, 
            "Function used", "");
        oEnum.setValue("Standard");
        mp_iWhatSaplingCDHFunction.getValue().remove(i);
        mp_iWhatSaplingCDHFunction.getValue().add(i, oEnum);
      }
    }
    
    //We already had lambdas.  What we want to do is preserve any for species
    //that didn't change, because they may have values.

    //Create an array of the lambda arrays, placed in the new species
    //order
    ModelData[] p_oNCI = new ModelData[iNumSpecies];
    int iCode;

    for (i = 0; i < iNumSpecies; i++) {
      p_oNCI[i] = null;
    }

    //Get the existing lambdas and put them where they go in our arrays -
    //any for species that were removed will be left behind
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown radius lambda") > -1) {
        //Get the species
        String sSpecies;
        sKey = oData.getDescriptor();
        sSpecies = sKey.substring(28, sKey.indexOf(" Neighbors")).replace(' ', '_');
        iCode = -1;
        for (int iSp = 0; iSp < iNumSpecies; iSp++) {
          if (p_sNewSpecies[iSp].equals(sSpecies)) {
            iCode = iSp; break;
          }
        }
        //iCode = oPop.getSpeciesCodeFromName(sSpecies);
        if (iCode > -1) {
          p_oNCI[iCode] = oData;
        }
      }
    }

    //Now remove all lambdas from the required data and from all behaviors
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown radius lambda") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }


    //Now add back the lambdas, creating new ones for any new species
    for (i = 0; i < p_oNCI.length; i++) {
      if (p_oNCI[i] == null) {
        String sSpName = p_sNewSpecies[i];
        p_oNCI[i] = new ModelVector("NCI Crown Radius Lambda for "
            + sSpName.replace('_', ' ') + " Neighbors",
            "tr_nciCR" + sSpName + "NeighborLambda", "tr_ncrlVal",
            0, ModelVector.FLOAT);
      }
      mp_oAllData.add(p_oNCI[i]);
    }
    
  
    //Create an array of the lambda arrays, placed in the new species
    //order
    
    for (i = 0; i < iNumSpecies; i++) {
      p_oNCI[i] = null;
    }

    //Get the existing lambdas and put them where they go in our arrays -
    //any for species that were removed will be left behind
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown depth lambda") > -1) {
        //get the species
        String sSpecies;
        sKey = oData.getDescriptor();
        sSpecies = sKey.substring(27, sKey.indexOf(" Neighbors")).replace(' ', '_');
        iCode = -1;
        for (int iSp = 0; iSp < iNumSpecies; iSp++) {
          if (p_sNewSpecies[iSp].equals(sSpecies)) {
            iCode = iSp; break;
          }
        }
        //iCode = oPop.getSpeciesCodeFromName(sSpecies);
        if (iCode > -1) {
          p_oNCI[iCode] = oData;
        }
      }
    }

    //Now remove all lambdas from the required data and from all behaviors
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("nci crown depth lambda") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }


    //Now add back the lambdas, creating new ones for any new species
    for (i = 0; i < p_oNCI.length; i++) {
      if (p_oNCI[i] == null) {
        String sSpName = p_sNewSpecies[i];
        p_oNCI[i] = new ModelVector("NCI Crown Depth Lambda for "
            + sSpName.replace('_', ' ') + " Neighbors",
            "tr_nciCD" + sSpName + "NeighborLambda", "tr_ncdlVal",
            0, ModelVector.FLOAT);
      }
      mp_oAllData.add(p_oNCI[i]);
    }
  }

  /**
   * Formats data for display in a set of JTables. This is overridden so that
   * only those values appropriate to the allometry functions that have been
   * chosen are used.
   * @param oPop TreePopulation object.
   * @return Vector of vectors suitable for creating a set of JTables, or null
   * if there is no data to display.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(TreePopulation oPop) {

    ArrayList<ModelData> p_oSingles = new ArrayList<ModelData>(); //single data objects
    ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific = new ArrayList<ArrayList<SpeciesSpecific>>(); //SpeciesSpecific objects - vector of vectors
    //grouped by what species they apply to
    ModelEnum oEnum;

    boolean[] p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    int i;
    boolean bAny;

    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    addDataObjectToDisplayArrays(mp_fDiam10ToDbhSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    addDataObjectToDisplayArrays(mp_fDiam10ToDbhIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);

    //------------------------------------------
    //Adult height-diameter function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultHDFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSlopeOfAsymptoticHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult height-diameter function - linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultHDFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fAdultLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fAdultLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult height-diameter function - reverse linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultHDFunction.getValue().get(i);
      if (oEnum.getValue() == 2) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fAdultReverseLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fAdultReverseLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling height-diameter function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSlopeOfAsymptoticHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling height-diameter function - linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSaplingLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSaplingLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling height-diameter function - reverse linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 2) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSaplingReverseLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSaplingReverseLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling height-diameter function - power
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 3) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fMaxCanopyHeight, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fPowerA, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fPowerB, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Seedling height-diameter function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSeedlingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSeedlingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSlopeOfHeightDiam10, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Seedling height-diameter function - linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSeedlingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSeedlingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSeedlingLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSeedlingLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Seedling height-diameter function - reverse linear
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSeedlingHDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSeedlingHDFunction.getValue().get(i);
      if (oEnum.getValue() == 2) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSeedlingReverseLinearSlope, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fSeedlingReverseLinearIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown radius function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSlopeOfAsympCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCrownRadExp, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fMaxCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown radius function - Chapman-Richards
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fCRCrownRadIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRAsympCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownRadShape1, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownRadShape2, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown radius function - non-spatial density dependent
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 2) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDA, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDB, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDC, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDE, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDF, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDG, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDH, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDI, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCDJ, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRD1, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRA, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRB, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRC, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRE, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatExpDensDepCRF, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown radius function - NCI
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 3) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNCIMaxCrownRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRAlpha, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRBeta, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRGamma, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRMaxCrowdingRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRN, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      for (i = 0; i < mp_oAllData.size(); i++) {
        ModelData oData = (ModelData) mp_oAllData.get(i);
        String sKey = oData.getDescriptor().toLowerCase();
        if (sKey.indexOf("nci crown radius lambda") > -1) {
          addDataObjectToDisplayArrays(oData, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
        }
      }
      //Minimum neighbor DBH applies to all species 
      boolean[] p_bApplyAll = new boolean[oPop.getNumberOfSpecies()];
      for (i = 0; i < p_bApplyAll.length; i++) {
        p_bApplyAll[i] = true;
      }
      addDataObjectToDisplayArrays(mp_fNCICRMinNeighborDBH, p_oSingles, p_oSpeciesSpecific, p_bApplyAll);
    }

    //------------------------------------------
    //Sapling crown radius function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSlopeOfAsympCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCrownRadExp, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fMaxCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling crown radius function - Chapman-Richards
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fCRCrownRadIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRAsympCrownRad, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownRadShape1, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownRadShape2, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling crown radius function - NCI
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCRDFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCRDFunction.getValue().get(i);
      if (oEnum.getValue() == 3) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNCIMaxCrownRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRAlpha, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRBeta, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRGamma, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRMaxCrowdingRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRN, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICRD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      for (i = 0; i < mp_oAllData.size(); i++) {
        ModelData oData = mp_oAllData.get(i);
        String sKey = oData.getDescriptor().toLowerCase();
        if (sKey.indexOf("nci crown radius lambda") > -1) {
          addDataObjectToDisplayArrays(oData, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
        }
      }
      //Minimum neighbor DBH applies to all species 
      boolean[] p_bApplyAll = new boolean[oPop.getNumberOfSpecies()];
      for (i = 0; i < p_bApplyAll.length; i++) {
        p_bApplyAll[i] = true;
      }
      addDataObjectToDisplayArrays(mp_fNCICRMinNeighborDBH, p_oSingles, p_oSpeciesSpecific, p_bApplyAll);
    }

    //------------------------------------------
    //Adult crown depth function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSlopeOfAsympCrownDpth, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCrownDepthExp, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown depth function - Chapman-Richards
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fCRCrownHtIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRAsympCrownHt, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownHtShape1, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownHtShape2, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown depth function - non-spatial density dependent
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 2) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRA, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRB, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRC, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRE, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRF, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRG, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRH, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRI, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatDensDepInstCRJ, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDA, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDB, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDC, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDE, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDF, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNonSpatLogDensDepCRDG, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Adult crown depth function - NCI
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatAdultCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatAdultCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 3) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNCIMaxCrownDepth, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDAlpha, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDBeta, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDGamma, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDMaxCrowdingRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDN, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      for (i = 0; i < mp_oAllData.size(); i++) {
        ModelData oData = mp_oAllData.get(i);
        String sKey = oData.getDescriptor().toLowerCase();
        if (sKey.indexOf("nci crown depth lambda") > -1) {
          addDataObjectToDisplayArrays(oData, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
        }
      }
      //Minimum neighbor DBH applies to all species 
      boolean[] p_bApplyAll = new boolean[oPop.getNumberOfSpecies()];
      for (i = 0; i < p_bApplyAll.length; i++) {
        p_bApplyAll[i] = true;
      }
      addDataObjectToDisplayArrays(mp_fNCICDMinNeighborDBH, p_oSingles, p_oSpeciesSpecific, p_bApplyAll);
    }

    //------------------------------------------
    //Sapling crown depth function - standard
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 0) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fSlopeOfAsympCrownDpth, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCrownDepthExp, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling crown depth function - Chapman-Richards
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 1) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fCRCrownHtIntercept, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRAsympCrownHt, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownHtShape1, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fCRCrownHtShape2, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    }

    //------------------------------------------
    //Sapling crown depth function - NCI
    bAny = false;
    p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = false;
    }
    for (i = 0; i < mp_iWhatSaplingCDHFunction.getValue().size(); i++) {
      oEnum = (ModelEnum)mp_iWhatSaplingCDHFunction.getValue().get(i);
      if (oEnum.getValue() == 3) {
        p_bAppliesTo[i] = true;
        bAny = true;
      }
    }
    if (bAny) {
      addDataObjectToDisplayArrays(mp_fNCIMaxCrownDepth, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDAlpha, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDBeta, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDGamma, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDMaxCrowdingRadius, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDN, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      addDataObjectToDisplayArrays(mp_fNCICDD, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
      for (i = 0; i < mp_oAllData.size(); i++) {
        ModelData oData = mp_oAllData.get(i);
        String sKey = oData.getDescriptor().toLowerCase();
        if (sKey.indexOf("nci crown depth lambda") > -1) {
          addDataObjectToDisplayArrays(oData, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
        }
      }
      //Minimum neighbor DBH applies to all species 
      boolean[] p_bApplyAll = new boolean[oPop.getNumberOfSpecies()];
      for (i = 0; i < p_bApplyAll.length; i++) {
        p_bApplyAll[i] = true;
      }
      addDataObjectToDisplayArrays(mp_fNCICDMinNeighborDBH, p_oSingles, p_oSpeciesSpecific, p_bApplyAll);
    }

    BehaviorParameterDisplay oDisp = formatTable(p_oSingles, p_oSpeciesSpecific, oPop);
    ArrayList<BehaviorParameterDisplay> jReturn = new ArrayList<BehaviorParameterDisplay>(1);
    jReturn.add(oDisp);
    return jReturn;
  }

  public ModelVector getWhatSeedlingHDFunction() {
    return mp_iWhatSeedlingHDFunction;
  }

  public void setWhatSeedlingHDFunction(ModelVector p_iWhatSeedlingHDFunction) {
    this.mp_iWhatSeedlingHDFunction = p_iWhatSeedlingHDFunction;
  }

  public ModelVector getWhatSaplingHDFunction() {
    return mp_iWhatSaplingHDFunction;
  }

  public void setWhatSaplingHDFunction(ModelVector p_iWhatSaplingHDFunction) {
    this.mp_iWhatSaplingHDFunction = p_iWhatSaplingHDFunction;
  }

  public ModelVector getWhatAdultHDFunction() {
    return mp_iWhatAdultHDFunction;
  }

  public void setWhatAdultHDFunction(ModelVector p_iWhatAdultHDFunction) {
    this.mp_iWhatAdultHDFunction = p_iWhatAdultHDFunction;
  }

  public ModelVector getWhatAdultCRDFunction() {
    return mp_iWhatAdultCRDFunction;
  }

  public void setWhatAdultCRDFunction(ModelVector p_iWhatAdultCRDFunction) {
    this.mp_iWhatAdultCRDFunction = p_iWhatAdultCRDFunction;
  }

  public ModelVector getWhatSaplingCRDFunction() {
    return mp_iWhatSaplingCRDFunction;
  }

  public void setWhatSaplingCRDFunction(ModelVector p_iWhatSaplingCRDFunction) {
    this.mp_iWhatSaplingCRDFunction = p_iWhatSaplingCRDFunction;
  }

  public ModelVector getWhatAdultCDHFunction() {
    return mp_iWhatAdultCDHFunction;
  }

  public void setWhatAdultCDHFunction(ModelVector p_iWhatAdultCDHFunction) {
    this.mp_iWhatAdultCDHFunction = p_iWhatAdultCDHFunction;
  }

  public ModelVector getWhatSaplingCDHFunction() {
    return mp_iWhatSaplingCDHFunction;
  }

  public void setWhatSaplingCDHFunction(ModelVector p_iWhatSaplingCDHFunction) {
    this.mp_iWhatSaplingCDHFunction = p_iWhatSaplingCDHFunction;
  }

  /**
   * Override to call allometry's dialog 
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    AllometryParameterEdit oWindow = new AllometryParameterEdit(jParent, m_oManager, oMain, this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }

  /**
   * Overridden to make sure function choices show up as well.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {
    
    boolean[] p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];
    String sVal;
    int i, iRow, iCol;        
    
    jOut.write("\n" + m_sDescriptor + "\n");
    
    //Write out function choices
    for (i = 0; i < p_bAppliesTo.length; i++) p_bAppliesTo[i] = true;
    Object[] p_oHeader = Behavior.formatSpeciesHeaderRow(p_bAppliesTo, oPop);
    jOut.write(p_oHeader[0].toString());
    for (iCol = 1; iCol < p_oHeader.length; iCol++) jOut.write("\t" + p_oHeader[iCol].toString());
    jOut.write("\n");
    
    Object[][] p_oTableData = null;
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatAdultHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatSaplingHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatSeedlingHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatAdultCRDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatSaplingCRDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatAdultCDHFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, getWhatSaplingCDHFunction());
    
    for (iRow = 0; iRow < p_oTableData.length; iRow++) {
      sVal = String.valueOf(p_oTableData[iRow][0]);
      //If a combo box - strip out the text
      if (sVal.startsWith("&&")) {
        sVal = EnhancedTable.getComboValue(sVal);
      }
      jOut.write(sVal);
      for (iCol = 1; iCol < p_oTableData[iRow].length; iCol++) {
        sVal = String.valueOf(p_oTableData[iRow][iCol]);
        //If a combo box - strip out the text
        if (sVal.startsWith("&&")) {
          sVal = EnhancedTable.getComboValue(sVal);
        }
        jOut.write("\t" + sVal);
      }
      jOut.write("\n");
    }
  
    //Write the rest of the parameters  
    ArrayList<BehaviorParameterDisplay> p_oAllObjects = formatDataForDisplay(oPop);
    if (null == p_oAllObjects || p_oAllObjects.size() == 0) {
      jOut.write("\n" + m_sDescriptor + "\nNo parameters.\n");
      return;
    }
    
    
    for (BehaviorParameterDisplay oDisp : p_oAllObjects) {
      jOut.write("\n" + oDisp.m_sBehaviorName + "\n");
      jOut.write(oDisp.m_sAppliesTo + "\n");
            
      for (TableData oTable : oDisp.mp_oTableData) {
        //Header row
        jOut.write(oTable.mp_oHeaderRow[0].toString());
        for (iCol = 1; iCol < oTable.mp_oHeaderRow.length; iCol++) {
          jOut.write("\t" + oTable.mp_oHeaderRow[iCol].toString());
        }
        jOut.write("\n");
        
        //Write each row
        for (iRow = 0; iRow < oTable.mp_oTableData.length; iRow++) {
          sVal = String.valueOf(oTable.mp_oTableData[iRow][0]);
          //If a combo box - strip out the text
          if (sVal.startsWith("&&")) {
            sVal = EnhancedTable.getComboValue(sVal);
          }
          jOut.write(sVal);
          for (iCol = 1; iCol < oTable.mp_oTableData[iRow].length; iCol++) {
            sVal = String.valueOf(oTable.mp_oTableData[iRow][iCol]);
            //If a combo box - strip out the text
            if (sVal.startsWith("&&")) {
              sVal = EnhancedTable.getComboValue(sVal);
            }
            jOut.write("\t" + sVal);
          }
          jOut.write("\n");
        }
      }
    }
  }
  
  
}