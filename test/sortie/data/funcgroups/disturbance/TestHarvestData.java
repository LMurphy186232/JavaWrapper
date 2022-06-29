package sortie.data.funcgroups.disturbance;

import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Tests the HarvestData class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestHarvestData
    extends ModelTestCase {

  /** Object upon which the tests will be conducted */
  private HarvestData m_oHarvestData = null;

  /**
   * Does setup by creating the test object.
   * @throws Exception Doesn't, really.
   */
  protected void setUp() throws Exception {
    super.setUp();
    m_oHarvestData = new HarvestData(8, 8);
  }

  /**
   * Does teardown.
   * @throws Exception Doesn't, really.
   */
  protected void tearDown() throws Exception {
    m_oHarvestData = null;
    super.tearDown();
  }

  /**
   * Tests the GetCutAmountType and SetCutAmountType methods.
   */
  public void testGetSetCutAmountType() {
    int iCut,
        iActual;

    try {
      //No value has been set
      iCut = -1;
      iActual = m_oHarvestData.getCutType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.ABSOLUTE_BASAL_AREA;
      m_oHarvestData.setCutAmountType(iCut);
      iActual = m_oHarvestData.getCutAmountType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.ABSOLUTE_DENSITY;
      m_oHarvestData.setCutAmountType(iCut);
      iActual = m_oHarvestData.getCutAmountType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.PERCENTAGE_BASAL_AREA;
      m_oHarvestData.setCutAmountType(iCut);
      iActual = m_oHarvestData.getCutAmountType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.PERCENTAGE_DENSITY;
      m_oHarvestData.setCutAmountType(iCut);
      iActual = m_oHarvestData.getCutAmountType();
      assertEquals(iActual, iCut);

      System.out.println(
          "Normal processing succeeded for SetCutAmountType and GetCutAmountType.");
    }
    catch (ModelException oErr) {
      fail("Normal processing for SetCutAmountType failed with message " +
           oErr.getMessage());
    }

    try {
      iCut = 17;
      m_oHarvestData.setCutAmountType(iCut);
      fail("SetCutAmountType did not catch a cut type value of " +
           String.valueOf(iCut));
    }
    catch (ModelException oErr) {
      System.out.println("Exception processing succeeded for SetCutAmountType.");
    }
  }

  /**
   * Tests the SetCutType and GetCutType methods.
   */
  public void testGetSetCutType() {
    int iCut,
        iActual;

    try {
      //No value has been set
      iCut = -1;
      iActual = m_oHarvestData.getCutType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.PARTIAL_CUT;
      m_oHarvestData.setCutType(iCut);
      iActual = m_oHarvestData.getCutType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.GAP_CUT;
      m_oHarvestData.setCutType(iCut);
      iActual = m_oHarvestData.getCutType();
      assertEquals(iActual, iCut);

      iCut = HarvestData.CLEAR_CUT;
      m_oHarvestData.setCutType(iCut);
      iActual = m_oHarvestData.getCutType();
      assertEquals(iActual, iCut);

      System.out.println(
          "Normal processing succeeded for SetCutType and GetCutType.");
    }
    catch (ModelException oErr) {
      fail("Normal processing for SetCutType failed with message " +
           oErr.getMessage());
    }

    try {
      iCut = 17;
      m_oHarvestData.setCutType(iCut);
      fail("SetCutType did not catch a cut type value of " +
           String.valueOf(iCut));
    }
    catch (ModelException oErr) {
      System.out.println("Exception processing succeeded for SetCutType.");
    }
  }

  /**
   * Tests the GetTimestep and SetTimestep methods.
   */
  public void testGetSetTimestep() {
    int iTimestep, iActual;

    try {
      iTimestep = 0;
      m_oHarvestData.setTimestep(iTimestep);
      iActual = m_oHarvestData.getTimestep();
      assertEquals(iActual, iTimestep);

      iTimestep = 10;
      m_oHarvestData.setTimestep(iTimestep);
      iActual = m_oHarvestData.getTimestep();
      assertEquals(iActual, iTimestep);

    }
    catch (ModelException oErr) {
      fail("Normal processing for SetCutTimestep failed with message " +
           oErr.getMessage());
    }

    try {
      iTimestep = -3;
      m_oHarvestData.setTimestep(iTimestep);
      fail("SetTimestep did not catch a timestep value of " +
           String.valueOf(iTimestep));
    }
    catch (ModelException oErr) {
      System.out.println("Exception processing succeeded for SetTimestep.");
    }

  }

  /**
   * Tests the AddSpecies method
   */
  public void testAddSpecies() {
    int iSpecies, iActual;

    //No value has been set
    iSpecies = 0;
    iActual = m_oHarvestData.getNumberOfSpecies();
    assertEquals(iActual, iSpecies);

    //No value has been set - attempting to get species out
    try {
      iSpecies = 1;
      iActual = m_oHarvestData.getSpecies(iSpecies);
      fail("GetSpecies did not throw an error when there were no species.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Add some values
      iSpecies = 0;
      m_oHarvestData.addSpecies(iSpecies);
      iActual = m_oHarvestData.getNumberOfSpecies();
      assertEquals(iActual, 1);
      iActual = m_oHarvestData.getSpecies(0);
      assertEquals(iActual, iSpecies);

      iSpecies = 2;
      m_oHarvestData.addSpecies(iSpecies);
      iActual = m_oHarvestData.getNumberOfSpecies();
      assertEquals(iActual, 2);
      iActual = m_oHarvestData.getSpecies(1);
      assertEquals(iActual, iSpecies);
      
      m_oHarvestData.getSeedlingMortRate(2);

      //Adding duplicate
      m_oHarvestData.addSpecies(iSpecies);
      iActual = m_oHarvestData.getNumberOfSpecies();
      assertEquals(iActual, 2);
      iActual = m_oHarvestData.getSpecies(1);
      assertEquals(iActual, iSpecies);

    }
    catch (ModelException oErr) {
      fail("Normal processing for AddSpecies, GetNumberOfSpecies, and GetSpecies failed with message " +
           oErr.getMessage());
    }

    //Exception processing
    try {
      iActual = m_oHarvestData.getSpecies(3);
      fail(
          "GetSpecies did not throw an error when the species index was too large.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      iActual = m_oHarvestData.getSpecies( -3);
      fail(
          "GetSpecies did not throw an error when the species index was negative.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "Normal processing succeeded for species getting and setting.");
    System.out.println(
        "Exception processing succeeded for species getting and setting.");
  }

  /**
   * Tests the RemoveSpecies method
   */
  public void testRemoveSpecies() {
    try {
      //Load up a species list
      HarvestData oData = new HarvestData(8, 8);
      oData.addSpecies(4);
      oData.addSpecies(3);
      oData.addSpecies(2);
      oData.addSpecies(1);

      assertEquals(4, oData.getNumberOfSpecies());

      //Try to remove a species that doesn't exist
      oData.removeSpecies(5);
      assertEquals(4, oData.getNumberOfSpecies());

      //Remove one in the middle
      oData.removeSpecies(3);
      assertEquals(3, oData.getNumberOfSpecies());
      assertEquals(4, oData.getSpecies(0));
      assertEquals(2, oData.getSpecies(1));
      assertEquals(1, oData.getSpecies(2));

      //Remove the first one
      oData.removeSpecies(4);
      assertEquals(2, oData.getNumberOfSpecies());
      assertEquals(2, oData.getSpecies(0));
      assertEquals(1, oData.getSpecies(1));

      //Remove the last one
      oData.removeSpecies(1);
      assertEquals(1, oData.getNumberOfSpecies());
      assertEquals(2, oData.getSpecies(0));

      //Remove the only one
      oData.removeSpecies(2);
      assertEquals(0, oData.getNumberOfSpecies());

    }
    catch (ModelException oErr) {
      fail("Species removal testing failed with message " + oErr.getMessage());
    }

    System.out.println(
        "Normal processing succeeded for species removal.");
  }

  /**
   * Tests cut ranges.
   */
  public void testCutRanges() {
    float fLow = 0, fHigh = 0, fAmountToCut = 0;
    int iIndex;

    //Normal processing
    try {

      //No cut ranges
      iIndex = m_oHarvestData.getNumberOfCutRanges();
      assertEquals(iIndex, 0);

      //First cut range setting
      fLow = 2;
      fHigh = 5;
      fAmountToCut = (float) 45.2;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      iIndex = m_oHarvestData.getNumberOfCutRanges();
      assertEquals(iIndex, 1);

      //Second cut range
      fLow = (float) 6.5;
      fHigh = 10;
      fAmountToCut = (float) 35.2;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);

      //Retrieving cut range values
      assertEquals(fLow, m_oHarvestData.getLowerBound(1), 0);
      assertEquals(fHigh, m_oHarvestData.getUpperBound(1), 0);
      assertEquals(fAmountToCut, m_oHarvestData.getCutAmount(1), 0);

      fLow = 2;
      fHigh = 5;
      fAmountToCut = (float) 45.2;
      assertEquals(fLow, m_oHarvestData.getLowerBound(0), 0);
      assertEquals(fHigh, m_oHarvestData.getUpperBound(0), 0);
      assertEquals(fAmountToCut, m_oHarvestData.getCutAmount(0), 0);

      System.out.println("Normal processing succeeded for cut ranges.");

    }
    catch (ModelException oErr) {
      fail("Normal processing for cut ranges failed with message " +
           oErr.getMessage());
    }

    //Exception processing

    //Negative lower bound
    try {
      fLow = -23;
      fHigh = 4;
      fAmountToCut = (float) 56.32;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject negative lower bound.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Negative upper bound
    try {
      fLow = (float) 2.4;
      fHigh = -2;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject negative upper bound.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Negative cut amount
    try {
      fLow = 2;
      fHigh = 4;
      fAmountToCut = (float) - 56.32;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject negative cut amount.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Lower bound less than upper bound
    try {
      fLow = 6;
      fHigh = 4;
      fAmountToCut = (float) 56.32;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject lower bound greater than upper bound.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Non-percentage value
    try {
      fLow = 3;
      fHigh = 4;
      fAmountToCut = (float) 145;
      m_oHarvestData.setCutAmountType(HarvestData.
                                      PERCENTAGE_BASAL_AREA);
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject non-percentage.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      fLow = 3;
      fHigh = 4;
      fAmountToCut = (float) 145;
      m_oHarvestData.setCutAmountType(HarvestData.PERCENTAGE_DENSITY);
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      fail("AddCutRange did not reject non-percentage.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Invalid cut range index
    try {
      m_oHarvestData.getLowerBound(4);
      fail("GetLowerBound did not reject bad index.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      m_oHarvestData.getUpperBound(4);
      fail("GetUpperBound did not reject bad index.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      m_oHarvestData.getCutAmount(4);
      fail("GetCutAmount did not reject bad index.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Too many cut ranges
    try {
      assertEquals(2, m_oHarvestData.getNumberOfCutRanges());

      fLow = 3;
      fHigh = 4;
      fAmountToCut = 34;
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
      m_oHarvestData.addCutRange(fLow, fHigh, fAmountToCut);
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "Exception processing succeeded for cut ranges getting and setting.");
  }

  /**
   * Tests methods for cells.
   */
  public void testCells() {
    Plot oPlot = null;
    //Normal processing
    try {
      oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(100);
      oPlot.setPlotYLength(100);

      assertEquals(m_oHarvestData.getNumberOfCells(), 0);

      m_oHarvestData.addCell(0, 0, oPlot);
      assertEquals(m_oHarvestData.getNumberOfCells(), 1);
      assertEquals(0, m_oHarvestData.getCell(0).getX());
      assertEquals(0, m_oHarvestData.getCell(0).getY());

      m_oHarvestData.addCell(9, 9, oPlot);
      assertEquals(m_oHarvestData.getNumberOfCells(), 2);
      assertEquals(9, m_oHarvestData.getCell(1).getX());
      assertEquals(9, m_oHarvestData.getCell(1).getY());

      m_oHarvestData.addCell(3, 4, oPlot);
      assertEquals(m_oHarvestData.getNumberOfCells(), 3);
      assertEquals(3, m_oHarvestData.getCell(2).getX());
      assertEquals(4, m_oHarvestData.getCell(2).getY());

      //Duplicate cell
      m_oHarvestData.addCell(9, 9, oPlot);
      assertEquals(m_oHarvestData.getNumberOfCells(), 3);

      //New cell passed as cell
      Cell oCell = new Cell(2, 6, oPlot);
      m_oHarvestData.addCell(oCell);
      assertEquals(4, m_oHarvestData.getNumberOfCells());
      assertEquals(2, m_oHarvestData.getCell(3).getX());
      assertEquals(6, m_oHarvestData.getCell(3).getY());

      //Duplicate passed as cell
      oCell = new Cell(3, 4, oPlot);
      m_oHarvestData.addCell(oCell);
      assertEquals(4, m_oHarvestData.getNumberOfCells());

    }
    catch (ModelException oErr) {
      System.out.println("Normal processing failed for cells with message " +
                         oErr.getMessage());
    }

    System.out.println("Normal processing succeeded for cells.");

    //Exception processing
    try {
      //Bad cell index
      m_oHarvestData.getCell(6);
      fail("GetCell did not reject bad cell index.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      //Bad cell index
      m_oHarvestData.getCell( -6);
      fail("GetCell did not reject bad cell index.");
    }
    catch (ModelException oErr) {
      ;
    }
    System.out.println("Exception processing succeeded for cells.");
  }

  /**
   * Tests validation of HarvestData objects.
   */
  public void testValidation() {
    GUIManager oManager = null;
    HarvestData oToTest = null;
    Plot oPlot = null;
    TreePopulation oPop = null;
    String sFileName = null;

    try {
      sFileName = writeXMLFile1();
      oManager = new GUIManager(null);
      oManager.inputXMLParameterFile(sFileName);
      oPlot = oManager.getPlot();
      oPop = oManager.getTreePopulation();

      //Validation passes
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addSpecies(0);
      oToTest.addCutRange(1, 2, 3);
      oToTest.setSeedlingMortRate(0, 50);
      oToTest.setSeedlingMortRate(1, 50);
      oToTest.validateCut(oPop, oPlot);

      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addSpecies(0);
      oToTest.addCutRange(1, 2, 3);
      oToTest.validateCut(oPop, oPlot);
    }
    catch (ModelException oErr) {
      fail("Normal processing failed for validation with message " +
          oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }

    //Exception processing
    //No species
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Invalid species
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.addSpecies(2);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch invalid species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No cut amount type specified
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of cut amount type.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No cut type specified
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of cut type.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No timestep specified
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of timesteps.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No cut range specified
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addSpecies(0);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of cut ranges.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No cells
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch lack of cells.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Invalid seedling mortality rate
    try {
      oToTest = null;
      oToTest = new HarvestData(8, 8);
      oToTest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oToTest.setCutType(HarvestData.CLEAR_CUT);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addCutRange(1, 2, 3);
      oToTest.addSpecies(0);
      oToTest.setSeedlingMortRate(0, 50);
      oToTest.setSeedlingMortRate(1, 150);
      oToTest.validateCut(oPop, oPlot);
      fail("Validation did not catch bad seedling mortality rate.");
    }
    catch (ModelException oErr) {
      ;
    }
  }
  
  public void testUpdateCellsNewGridResolution() {
    GUIManager oManager = null;
    String sFileName = null;
    
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(2, oHarvest.getNumberHarvestEvents());
                
      //First cut event
      HarvestData oCut = oHarvest.getHarvestEvent(0);      
      assertEquals(8, oCut.getXCellLength(), 0.0001);
      assertEquals(8, oCut.getYCellLength(), 0.0001);
      assertEquals(1, oCut.getTimestep());
      
      oCut.updateCellsNewGridResolution(2, 3, oManager.getPlot());
      assertEquals(2, oCut.getXCellLength(), 0.0001);
      assertEquals(3, oCut.getYCellLength(), 0.0001);
      int iCellCount = 0, iX, iY;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 40; iX < 44; iX++) {
        for (iY = 5; iY < 8; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 48; iX < 50; iX++) {
        for (iY = 0; iY < 67; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());
      
      //Second cut event
      oCut = oHarvest.getHarvestEvent(1);
      assertEquals(8, oCut.getXCellLength(), 0.0001);
      assertEquals(8, oCut.getYCellLength(), 0.0001);
      assertEquals(2, oCut.getTimestep());
      oCut.updateCellsNewGridResolution(2, 3, oManager.getPlot());
      assertEquals(2, oCut.getXCellLength(), 0.0001);
      assertEquals(3, oCut.getYCellLength(), 0.0001);
      iCellCount = 0;
      for (iX = 16; iX < 44; iX++) {
        for (iY = 13; iY < 30; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());  
      
      
      //*******************************************
      //This goes from smaller cell size to larger.
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oHarvest.getNumberHarvestEvents());
      
      //First cut event
      oCut = oHarvest.getHarvestEvent(0);
      assertEquals(4, oCut.getXCellLength(), 0.0001);
      assertEquals(5, oCut.getYCellLength(), 0.0001);
      assertEquals(1, oCut.getTimestep());
      oCut.updateCellsNewGridResolution(9, 10, oManager.getPlot());
      assertEquals(9, oCut.getXCellLength(), 0.0001);
      assertEquals(10, oCut.getYCellLength(), 0.0001);
      iCellCount = 0;
      for (iX = 0; iX < 3; iX++) {
        for (iY = 0; iY < 5; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(4, oCut.getCell(iCellCount).getX());
      assertEquals(1, oCut.getCell(iCellCount).getY());
      iCellCount++;
      for (iX = 10; iX < 12; iX++) {
        for (iY = 0; iY < 20; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      
      
      //*******************************************
      //This does the whole plot.
      sFileName = writeXMLFile4();
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oHarvest.getNumberHarvestEvents());
            
      //First cut event
      oCut = oHarvest.getHarvestEvent(0);
      assertEquals(8, oCut.getXCellLength(), 0.0001);
      assertEquals(8, oCut.getYCellLength(), 0.0001);
      assertEquals(1, oCut.getTimestep());
      oCut.updateCellsNewGridResolution(9, 10, oManager.getPlot());
      assertEquals(9, oCut.getXCellLength(), 0.0001);
      assertEquals(10, oCut.getYCellLength(), 0.0001);iCellCount = 0;
      for (iX = 0; iX < 12; iX++) {
        for (iY = 0; iY < 20; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());
      
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oHarvest.getNumberHarvestEvents());
      oCut = oHarvest.getHarvestEvent(0);
      assertEquals(8, oCut.getXCellLength(), 0.0001);
      assertEquals(8, oCut.getYCellLength(), 0.0001);
      assertEquals(1, oCut.getTimestep());
      oCut.updateCellsNewGridResolution(4, 5, oManager.getPlot());
      assertEquals(4, oCut.getXCellLength(), 0.0001);
      assertEquals(5, oCut.getYCellLength(), 0.0001);
      iCellCount = 0;
      for (iX = 0; iX < 25; iX++) {
        for (iY = 0; iY < 40; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      
    }
    catch (ModelException oErr) {
      fail("Grid cell resolution change failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }
  
    
  
  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>100.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");

      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("</trees>");

      //Include an empty behavior list - this triggers setup
      oOut.write("<behaviorList>");
      oOut.write("</behaviorList>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile2() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 10; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("<ha_applyToCell x=\"10\" y=\"2\" />");
      for (int i = 0; i < 26; i++)
      oOut.write("<ha_applyToCell x=\"12\" y=\"" + i + "\" />");
      oOut.write("</ha_cutEvent>");

      //Cut event 2.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>2</ha_timestep>");
      oOut.write("<ha_cutType>clear</ha_cutType>");
      oOut.write("<ha_cutAmountType>absolute basal area</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>20.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 4; i < 11; i++) {
        for (int j = 5; j < 11; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ha_cutEvent>");
      oOut.write("</Harvest1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile3() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<grid gridName=\"harvestmastercuts\">");
      oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
      oOut.write("</grid>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 10; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("<ha_applyToCell x=\"10\" y=\"2\" />");
      for (int i = 0; i < 40; i++)
      oOut.write("<ha_applyToCell x=\"24\" y=\"" + i + "\" />");
      oOut.write("</ha_cutEvent>");

      oOut.write("</Harvest1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile4() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 13; i++) {
        for (int j = 0; j < 25; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ha_cutEvent>");

      oOut.write("</Harvest1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
