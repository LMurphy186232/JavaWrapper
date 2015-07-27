package sortie.data.funcgroups.planting;

import java.io.File;
import java.io.IOException;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Tests the PlantingData class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0 
 */
public class TestPlantingData
    extends ModelTestCase {

  /** Object data */
  private PlantingData m_oPlantingData = null;

  /**
   * Does set up by creating the test object.
   * @throws Exception Won't.
   */
  protected void setUp() throws Exception {
    super.setUp();
    m_oPlantingData = new PlantingData(8, 8);
  }

  /**
   * Does tear down.
   * @throws Exception Won't.
   */
  protected void tearDown() throws Exception {
    m_oPlantingData = null;
    super.tearDown();
  }

  /**
   * Tests the AddAbundance method.
   */
  public void testAddGetAbundance() {

    //Normal processing
    int iSpecies = 0;
    float fAbundance = 0;

    try {

      //First abundance added
      m_oPlantingData.addAbundance(iSpecies, fAbundance);
      assertEquals(fAbundance, m_oPlantingData.getAbundance(iSpecies), 0);

      //Second abundance added
      iSpecies = 4;
      fAbundance = 14;
      m_oPlantingData.addAbundance(iSpecies, fAbundance);
      assertEquals(fAbundance, m_oPlantingData.getAbundance(iSpecies), 0);

      //Replace the abundance for a species
      iSpecies = 4;
      fAbundance = (float) 0.4;
      m_oPlantingData.addAbundance(iSpecies, fAbundance);
      assertEquals(fAbundance, m_oPlantingData.getAbundance(iSpecies), 0);

    }
    catch (ModelException oErr) {
      fail("Normal processing for AddAbundance() failed with message " +
           oErr.getMessage());
    }

    //Exception processing - negative abundance
    try {
      iSpecies = 3;
      fAbundance = -5;
      m_oPlantingData.addAbundance(iSpecies, fAbundance);
      fail("AddAbundance() did not catch negative abundance value of " +
           fAbundance + ".");
    }
    catch (ModelException oErr) {
      System.out.println("Exception processing succeeded for AddAbundance().");
    }

  }

  /**
   * Tests cell methods.
   */
  public void testCell() {
    Plot oPlot = null;
    //Normal processing
    try {
      oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(100);
      oPlot.setPlotYLength(100);

      assertEquals(m_oPlantingData.getNumberOfCells(), 0);

      m_oPlantingData.addCell(0, 0, oPlot);
      assertEquals(m_oPlantingData.getNumberOfCells(), 1);
      assertEquals(0, m_oPlantingData.getCell(0).getX());
      assertEquals(0, m_oPlantingData.getCell(0).getY());

      m_oPlantingData.addCell(9, 9, oPlot);
      assertEquals(m_oPlantingData.getNumberOfCells(), 2);
      assertEquals(9, m_oPlantingData.getCell(1).getX());
      assertEquals(9, m_oPlantingData.getCell(1).getY());

      m_oPlantingData.addCell(3, 4, oPlot);
      assertEquals(m_oPlantingData.getNumberOfCells(), 3);
      assertEquals(3, m_oPlantingData.getCell(2).getX());
      assertEquals(4, m_oPlantingData.getCell(2).getY());

      //Duplicate cell
      m_oPlantingData.addCell(9, 9, oPlot);
      assertEquals(m_oPlantingData.getNumberOfCells(), 3);

      //New cell passed as cell
      Cell oCell = new Cell(2, 6, oPlot);
      m_oPlantingData.addCell(oCell);
      assertEquals(4, m_oPlantingData.getNumberOfCells());
      assertEquals(2, m_oPlantingData.getCell(3).getX());
      assertEquals(6, m_oPlantingData.getCell(3).getY());

      //Duplicate passed as cell
      oCell = new Cell(3, 4, oPlot);
      m_oPlantingData.addCell(oCell);
      assertEquals(4, m_oPlantingData.getNumberOfCells());

    }
    catch (ModelException oErr) {
      fail("Normal processing failed for cells with message " +
           oErr.getMessage());
    }

    System.out.println("Normal processing succeeded for cells.");

    //Exception processing
    try {
      //Bad cell index
      m_oPlantingData.getCell(6);
      fail("GetCell did not reject bad cell index.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      //Bad cell index
      m_oPlantingData.getCell( -6);
      fail("GetCell did not reject bad cell index.");
    }
    catch (ModelException oErr) {
      ;
    }
    System.out.println("Exception processing succeeded for cells.");

  }

  /**
   * Tests RemoveSpecies method.
   */
  public void testRemoveSpecies() {
    try {
      //Load up a species list
      PlantingData oData = new PlantingData(8, 8);
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
   * Tests the AddSpecies method.
   */
  public void testAddSpecies() {
    int iSpecies, iActual;

    //No value has been set
    iSpecies = 0;
    iActual = m_oPlantingData.getNumberOfSpecies();
    assertEquals(iActual, iSpecies);

    //No value has been set - attempting to get species out
    try {
      iSpecies = 1;
      iActual = m_oPlantingData.getSpecies(iSpecies);
      fail("GetSpecies did not throw an error when there were no species.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      //Add some values
      iSpecies = 0;
      m_oPlantingData.addSpecies(iSpecies);
      iActual = m_oPlantingData.getNumberOfSpecies();
      assertEquals(iActual, 1);
      iActual = m_oPlantingData.getSpecies(0);
      assertEquals(iActual, iSpecies);

      iSpecies = 2;
      m_oPlantingData.addSpecies(iSpecies);
      iActual = m_oPlantingData.getNumberOfSpecies();
      assertEquals(iActual, 2);
      iActual = m_oPlantingData.getSpecies(1);
      assertEquals(iActual, iSpecies);

      //Adding duplicate
      m_oPlantingData.addSpecies(iSpecies);
      iActual = m_oPlantingData.getNumberOfSpecies();
      assertEquals(iActual, 2);
      iActual = m_oPlantingData.getSpecies(1);
      assertEquals(iActual, iSpecies);

    }
    catch (ModelException oErr) {
      fail("Normal processing for AddSpecies, GetNumberOfSpecies, and GetSpecies failed with message " +
           oErr.getMessage());
    }

    //Exception processing
    try {
      iActual = m_oPlantingData.getSpecies(3);
      fail(
          "GetSpecies did not throw an error when the species index was too large.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      iActual = m_oPlantingData.getSpecies( -3);
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
   * Tests GetPlantSpacing and GetPlantSpacing methods.
   */
  public void testGetSetPlantSpacing() {
    //No value has been set
    int iPlanting = -1;
    int iActualReturn = m_oPlantingData.getPlantSpacing();
    assertEquals("return value", -1, iActualReturn);

    try {
      //Normal processing
      iPlanting = Planting.GRIDDED;
      m_oPlantingData.setPlantSpacing(iPlanting);
      iActualReturn = m_oPlantingData.getPlantSpacing();
      assertEquals(iPlanting, iActualReturn);

      iPlanting = Planting.RANDOM;
      m_oPlantingData.setPlantSpacing(iPlanting);
      iActualReturn = m_oPlantingData.getPlantSpacing();
      assertEquals(iPlanting, iActualReturn);

      System.out.println(
          "Normal processing succeeded for GetPlantSpacing() and SetPlantSpacing().");
    }
    catch (ModelException oErr) {
      fail("Normal processing for GetPlantSpacing() and SetPlantSpacing() " +
           " failed with message " + oErr.getMessage() + ".");
    }

    //Exception processing
    try {
      iPlanting = -2;
      m_oPlantingData.setPlantSpacing(iPlanting);
      fail("SetPlantSpacing() did not catch passed value of " + iPlanting + ".");
    }
    catch (ModelException oErr) {
      System.out.println(
          "Exception processing succeeded for GetPlantSpacing() and SetPlantSpacing().");
    }
  }

  /**
   * Tests GetTimestep and SetTimestep methods.
   */
  public void testGetSetTimestep() {
    int iTimestep, iActual;

    try {
      iTimestep = 0;
      m_oPlantingData.setTimestep(iTimestep);
      iActual = m_oPlantingData.getTimestep();
      assertEquals(iActual, iTimestep);

      iTimestep = 10;
      m_oPlantingData.setTimestep(iTimestep);
      iActual = m_oPlantingData.getTimestep();
      assertEquals(iActual, iTimestep);

    }
    catch (ModelException oErr) {
      fail("Normal processing for SetTimestep failed with message " +
           oErr.getMessage());
    }

    try {
      iTimestep = -3;
      m_oPlantingData.setTimestep(iTimestep);
      fail("SetTimestep did not catch a timestep value of " +
           String.valueOf(iTimestep));
    }
    catch (ModelException oErr) {
      System.out.println("Exception processing succeeded for SetTimestep.");
    }
  }

  /**
   * Tests GetSpacingDistance and SetSpacingDistance methods.
   */
  public void testGetSetSpacingDistance() {
    float fSpace = 0;
    try {

      //No value set
      assertEquals(0, m_oPlantingData.getSpacingDistance(), 0);
      m_oPlantingData.setSpacingDistance(fSpace);
      assertEquals(fSpace, m_oPlantingData.getSpacingDistance(), 0);

      //Valid value
      fSpace = (float) 24.645;
      m_oPlantingData.setSpacingDistance(fSpace);
      assertEquals(fSpace, m_oPlantingData.getSpacingDistance(), 0);

    }
    catch (ModelException oErr) {
      fail("Normal processing for SetSpacingDistance failed with message " +
           oErr.getMessage());
    }

    //Exception processing
    try {
      fSpace = (float) - 24.645;
      m_oPlantingData.setSpacingDistance(fSpace);
      fail("SetSpacingDistance did not catch a timestep value of " +
           String.valueOf(fSpace));
    }
    catch (ModelException oErr) {
      System.out.println(
          "Exception processing succeeded for SetSpacingDistance.");
    }
  }

  /**
   * Tests ValidatePlant method.
   */
  public void testValidatePlant() {
    
    GUIManager oManager = null;
    TreePopulation oPop = null;
    Plot oPlot = null;
    PlantingData oToTest = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oPlot = oManager.getPlot();
      
      //oPlot = new Plot(null, null, "", "", "");
      //oPlot.setPlotXLength(100);
      //oPlot.setPlotYLength(100);
      //oPlot.setNumberOfTimesteps(3);

      //oPop = new TreePopulation(null);
      //oPop.setSpeciesNames(new String[] {"ACRU", "ACSA"});

      //Validation passes - random spacing
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 77);
      oToTest.addAbundance(1, 23);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.setDensity(300);
      oToTest.validatePlant(oPop, oPlot);

      //Validation passes - gridded spacing
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.GRIDDED);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 35);
      oToTest.addAbundance(1, 65);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.setSpacingDistance( (float) 4.3);
      oToTest.validatePlant(oPop, oPlot);
    }
    catch (ModelException oErr) {
      fail("Normal processing failed for validation with message " +
           oErr.getMessage());
    } catch (IOException oErr) {
      fail("Normal processing failed for validation with message " +
          oErr.getMessage());
   } finally {
     new File(sFileName).delete();
   }
    

    //Exception processing
    //No species
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 12);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Invalid species
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addSpecies(0);
      oToTest.addSpecies(2);
      oToTest.addAbundance(0, 12);
      oToTest.addAbundance(2, 12);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch invalid species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No planting specified specified
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addSpecies(0);
      oToTest.addAbundance(0, 12);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of cut amount type.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No timestep specified
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 12);
      oToTest.addSpecies(0);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of timesteps.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No cells
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addAbundance(0, 12);
      oToTest.addSpecies(0);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of cells.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No abundance
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.addCell(0, 0, oPlot);
      oToTest.setTimestep(1);
      oToTest.addSpecies(0);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Not abundances for all species
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.GRIDDED);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, (float) 0.35);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance for each species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Gridded abundances are not expressed as percentages - GRIDDED
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.GRIDDED);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 135);
      oToTest.addAbundance(1, 123);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance for each species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Gridded abundances are not expressed as percentages - RANDOM
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 135);
      oToTest.addAbundance(1, 123);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance for each species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Gridded abundances don't add up to 100
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.GRIDDED);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 35);
      oToTest.addAbundance(1, 23);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance for each species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Random abundances don't add up to 100
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 35);
      oToTest.addAbundance(1, 23);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of abundance for each species.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No spacing distance for gridded
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.GRIDDED);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 35);
      oToTest.addAbundance(1, 65);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of plant distance for gridded.");
    }
    catch (ModelException oErr) {
      ;
    }

    //No density distance for random
    try {
      oToTest = null;
      oToTest = new PlantingData(8, 8);
      oToTest.setPlantSpacing(Planting.RANDOM);
      oToTest.setTimestep(1);
      oToTest.addCell(0, 0, oPlot);
      oToTest.addAbundance(0, 35);
      oToTest.addAbundance(1, 65);
      oToTest.addSpecies(0);
      oToTest.addSpecies(1);
      oToTest.validatePlant(oPop, oPlot);
      fail("Validation did not catch lack of density for random.");
    }
    catch (ModelException oErr) {
      ;
    }
  }
}
