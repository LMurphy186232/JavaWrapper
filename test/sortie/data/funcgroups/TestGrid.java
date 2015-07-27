package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;



import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.funcgroups.PackageGridValue;
import sortie.data.funcgroups.Plot;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.sax.ParameterFileParser;

/**
 * Tests the Grid class.
 *oOut.write("<p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 *oOut.write("<p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestGrid extends ModelTestCase {

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

      Grid oGrid = oManager.getGridByName("Dispersed Seeds");
      int iX, iY, iNumX = 20, iNumY = 20, iCount = 0;

      assertEquals(5, oGrid.m_fLengthXCells, 0.0001);
      assertEquals(5, oGrid.m_fLengthYCells, 0.0001);
      assertEquals((iNumX*iNumY), oGrid.mp_oGridVals.size());
      assertEquals(5, oGrid.mp_oDataMembers.length);

      assertEquals("seeds_0", oGrid.mp_oDataMembers[0].getCodeName());
      assertEquals(DataMember.FLOAT, oGrid.mp_oDataMembers[0].getType());
      //assertEquals(0, oGrid.mp_oDataMembers[0].getCode());

      assertEquals("seeds_1", oGrid.mp_oDataMembers[1].getCodeName());
      assertEquals(DataMember.FLOAT, oGrid.mp_oDataMembers[1].getType());
      //assertEquals(1, oGrid.mp_oDataMembers[1].getCode());

      assertEquals("seeds_2", oGrid.mp_oDataMembers[2].getCodeName());
      assertEquals(DataMember.FLOAT, oGrid.mp_oDataMembers[2].getType());
      //assertEquals(2, oGrid.mp_oDataMembers[2].getCode());

      assertEquals("count", oGrid.mp_oDataMembers[4].getCodeName());
      assertEquals(DataMember.INTEGER, oGrid.mp_oDataMembers[4].getType());
      //assertEquals(4, oGrid.mp_oDataMembers[3].getCode());

      assertEquals("Is Gap", oGrid.mp_oDataMembers[3].getCodeName());
      assertEquals(DataMember.BOOLEAN, oGrid.mp_oDataMembers[3].getType());
      //assertEquals(0, oGrid.mp_oDataMembers[4].getCode());

      for (iX = 0; iX < iNumX; iX++) {
        for (iY = 0; iY < iNumY; iY++) {
          assertEquals(iX, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
          assertEquals(iY, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
          assertEquals((iX + iY), oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
          assertEquals((2 * (iX + iY)), oGrid.mp_oGridVals.get(iCount).getFloat(1), 0.0001);
          assertEquals((3 * (iX + iY)), oGrid.mp_oGridVals.get(iCount).getFloat(2), 0.0001);
          iCount++;
        }
      }      

      oGrid = oManager.getGridByName("Resource");
      assertEquals(1, oGrid.mp_oDataMembers.length);
      assertEquals(4, oGrid.m_fLengthXCells, 0.0001);
      assertEquals(4, oGrid.m_fLengthYCells, 0.0001);

      assertEquals("Resource", oGrid.mp_oDataMembers[0].getCodeName());
      assertEquals(DataMember.FLOAT, oGrid.mp_oDataMembers[0].getType());
      //assertEquals(0, oGrid.mp_oDataMembers[0].getCode());

      assertEquals(10, oGrid.mp_oGridVals.size());

      iCount = 0;
      assertEquals(1, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(12, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(0.1, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(2, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(11, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(10, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(3, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(10, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(3, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(5, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(8, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(4, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(6, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(7, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(0, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(7, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(6, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(0, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(8, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(5, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(5, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(10, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(3, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(20, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(11, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(2, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(2, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

      assertEquals(12, oGrid.mp_oGridVals.get(iCount).m_oCell.getX());
      assertEquals(1, oGrid.mp_oGridVals.get(iCount).m_oCell.getY());
      assertEquals(5, oGrid.mp_oGridVals.get(iCount).getFloat(0), 0.0001);
      iCount++;

    }
    catch (ModelException oErr) {
      fail("Grid reading failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  /**
   * This checks for correct handling of grids that are not connected to
   * existing behaviors (i.e. they are ignored).
   */
  public void testReadGrid2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileGrid1();
      oManager.inputXMLParameterFile(sFileName);
      Grid oGrid = null;

      //Grid: Basal area light. No behavior, ignored
      oGrid = oManager.getGridByName("Basal Area Light");
      assertNull(oGrid);

      //Grid: storm damage. No behavior, ignored
      oGrid = oManager.getGridByName("Storm Damage");
      assertNull(oGrid);

      //Grid: storm susceptibility. No behavior, ignored
      oGrid = oManager.getGridByName("Storm Susceptibility");
      assertNull(oGrid);
      
      //Grid: substrate. No behavior, ignored
      oGrid = oManager.getGridByName("Substrate");
      assertNull(oGrid);

      //Grid: dispersed seeds. Not ignored
      oGrid = oManager.getGridByName("Dispersed Seeds");
      assertNotNull(oGrid);

      assertEquals(10.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(10.0, oGrid.getYCellLength(), 0.0001);

      TreePopulation oPop = oManager.getTreePopulation();
      assertEquals(4, oPop.getTrees().size());
    }
    catch (ModelException oErr) {
      fail("Grid reading failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests the retrieval and addition of GridVal objects.
   */
  public void testGetGridValue() {
    try {
      Grid oGrid = new Grid("test grid",
          new DataMember[] {new DataMember("test", "test", DataMember.BOOLEAN)}, //data member list
          new DataMember[] {new DataMember("testpackage", "testpackage", DataMember.FLOAT)}, //package data member list
          0, 0);

      //Normal processing
      Plot oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(200);
      oPlot.setPlotYLength(200);

      //Case:  no grid values yet
      assertEquals(0, oGrid.mp_oGridVals.size());
      GridValue oVal = oGrid.getGridValue(0, 10, oPlot);
      assertEquals(1, oGrid.mp_oGridVals.size());

      //Case:  no packages in grid
      assertEquals(0, oVal.mp_oPackages.size());
      //Adding a package
      PackageGridValue oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.mp_oPackages.size());
      //Set a value
      oPackage.setValue(0, new Float(10));

      //Case: adding a new one
      oVal = oGrid.getGridValue(1, 10, oPlot);
      assertEquals(2, oGrid.mp_oGridVals.size());
      //Make sure packages get assigned correctly
      oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.mp_oPackages.size());

      //Finds an existing
      oVal = oGrid.getGridValue(0, 10, oPlot);
      assertEquals(2, oGrid.mp_oGridVals.size());
      assertEquals(0, oVal.getCell().getX());
      assertEquals(10, oVal.getCell().getY());
      assertEquals( (float) 8, oVal.getCell().getXCellLength(), 0.00000001);
      assertEquals( (float) 8, oVal.getCell().getYCellLength(), 0.00000001);
      assertEquals(1, oVal.mp_oPackages.size());
      oPackage = oGrid.getPackageGridValue(oVal, 0);

      //New one when there have been grid values set - this should never happen
      //this way in real life but saves the trouble of going back and starting
      //over
      oGrid.setXCellLength( (float) 12.3);
      oGrid.setYCellLength( (float) 15.2);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      assertEquals(3, oGrid.mp_oGridVals.size());
      assertEquals(5, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals( (float) 12.3, oVal.getCell().getXCellLength(), 0.00000001);
      assertEquals( (float) 15.2, oVal.getCell().getYCellLength(), 0.00000001);

      //Case:  all data member types present
      float fXCellLength = 8, fYCellLength = 8;
      oGrid = null;
      DataMember[] p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };
      DataMember[] p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };
      oGrid = new Grid("test", p_oDataMembers, p_oPackageDataMembers,
          fXCellLength, fYCellLength);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.getNumberOfFloats());
      assertEquals(2, oVal.getNumberOfInts());
      assertEquals(3, oVal.getNumberOfChars());
      assertEquals(4, oVal.getNumberOfBools());
      assertEquals(2, oPackage.getNumberOfFloats());
      assertEquals(3, oPackage.getNumberOfInts());
      assertEquals(4, oPackage.getNumberOfChars());
      assertEquals(5, oPackage.getNumberOfBools());

      //Case: no floats - cell
      //Case: no bools - package
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };
      p_oPackageDataMembers = new DataMember[] {

          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };
      oGrid = new Grid("test", p_oDataMembers, p_oPackageDataMembers,
          fXCellLength, fYCellLength);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(0, oVal.getNumberOfFloats());
      assertEquals(2, oVal.getNumberOfInts());
      assertEquals(3, oVal.getNumberOfChars());
      assertEquals(4, oVal.getNumberOfBools());
      assertEquals(2, oPackage.getNumberOfFloats());
      assertEquals(3, oPackage.getNumberOfInts());
      assertEquals(4, oPackage.getNumberOfChars());
      assertEquals(0, oPackage.getNumberOfBools());

      //Case: no ints - cell
      //Case: no chars - package
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };
      p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
      };
      oGrid = new Grid("test", p_oDataMembers, p_oPackageDataMembers,
          fXCellLength, fYCellLength);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.getNumberOfFloats());
      assertEquals(0, oVal.getNumberOfInts());
      assertEquals(3, oVal.getNumberOfChars());
      assertEquals(4, oVal.getNumberOfBools());
      assertEquals(2, oPackage.getNumberOfFloats());
      assertEquals(3, oPackage.getNumberOfInts());
      assertEquals(0, oPackage.getNumberOfChars());
      assertEquals(5, oPackage.getNumberOfBools());

      //Case: no chars - cell
      //Case: no floats - package
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
      };
      p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };
      oGrid = new Grid("test", p_oDataMembers, p_oPackageDataMembers,
          fXCellLength, fYCellLength);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.getNumberOfFloats());
      assertEquals(2, oVal.getNumberOfInts());
      assertEquals(0, oVal.getNumberOfChars());
      assertEquals(4, oVal.getNumberOfBools());
      assertEquals(0, oPackage.getNumberOfFloats());
      assertEquals(3, oPackage.getNumberOfInts());
      assertEquals(4, oPackage.getNumberOfChars());
      assertEquals(5, oPackage.getNumberOfBools());

      //Case: no bools - cell
      //Case: no ints - package
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };
      p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };
      oGrid = new Grid("test", p_oDataMembers, p_oPackageDataMembers,
          fXCellLength, fYCellLength);
      oVal = oGrid.getGridValue(5, 6, oPlot);
      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(1, oVal.getNumberOfFloats());
      assertEquals(2, oVal.getNumberOfInts());
      assertEquals(3, oVal.getNumberOfChars());
      assertEquals(0, oVal.getNumberOfBools());
      assertEquals(2, oPackage.getNumberOfFloats());
      assertEquals(0, oPackage.getNumberOfInts());
      assertEquals(4, oPackage.getNumberOfChars());
      assertEquals(5, oPackage.getNumberOfBools());

      System.out.println("GetGridValue testing succeeded.");
    }
    catch (ModelException oErr) {
      fail("GetGridValue failed with message " + oErr.getMessage());
    }
  }

  /**
   * Tests the Grid constructor.
   */
  public void testGrid() {
    try {
      //Case:  Some of each type of data member.
      DataMember[] p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };
      DataMember[] p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };

      Grid oGrid = new Grid("test grid", p_oDataMembers, p_oPackageDataMembers,
          8, 8);
      assertEquals(1, oGrid.mp_sFloatDataMembers.length);
      assertEquals(2, oGrid.mp_sIntDataMembers.length);
      assertEquals(3, oGrid.mp_sCharDataMembers.length);
      assertEquals(4, oGrid.mp_sBoolDataMembers.length);

      assertEquals(2, oGrid.mp_sPackageFloatDataMembers.length);
      assertEquals(3, oGrid.mp_sPackageIntDataMembers.length);
      assertEquals(4, oGrid.mp_sPackageCharDataMembers.length);
      assertEquals(5, oGrid.mp_sPackageBoolDataMembers.length);

      assertEquals(1, oGrid.mp_iGridFloatTransforms.size());
      assertEquals(2, oGrid.mp_iGridIntTransforms.size());
      assertEquals(3, oGrid.mp_iGridCharTransforms.size());
      assertEquals(4, oGrid.mp_iGridBoolTransforms.size());

      assertEquals(2, oGrid.mp_iGridPackageFloatTransforms.size());
      assertEquals(3, oGrid.mp_iGridPackageIntTransforms.size());
      assertEquals(4, oGrid.mp_iGridPackageCharTransforms.size());
      assertEquals(5, oGrid.mp_iGridPackageBoolTransforms.size());
      assertEquals("float 1", oGrid.mp_sFloatDataMembers[0]);
      Integer iTest = oGrid.mp_iGridFloatTransforms.get(0);
      assertEquals(iTest.intValue(), 0);

      assertEquals("int 1", oGrid.mp_sIntDataMembers[0]);
      iTest = oGrid.mp_iGridIntTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("int 2", oGrid.mp_sIntDataMembers[1]);
      iTest = oGrid.mp_iGridIntTransforms.get(1);
      assertEquals(iTest.intValue(), 1);

      assertEquals("char 1", oGrid.mp_sCharDataMembers[0]);
      iTest = oGrid.mp_iGridCharTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("char 2", oGrid.mp_sCharDataMembers[1]);
      iTest = oGrid.mp_iGridCharTransforms.get(1);
      assertEquals(iTest.intValue(), 1);
      assertEquals("char 3", oGrid.mp_sCharDataMembers[2]);
      iTest = oGrid.mp_iGridCharTransforms.get(2);
      assertEquals(iTest.intValue(), 2);

      assertEquals("bool 1", oGrid.mp_sBoolDataMembers[0]);
      iTest = oGrid.mp_iGridBoolTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("bool 2", oGrid.mp_sBoolDataMembers[1]);
      iTest = oGrid.mp_iGridBoolTransforms.get(1);
      assertEquals(iTest.intValue(), 1);
      assertEquals("bool 3", oGrid.mp_sBoolDataMembers[2]);
      iTest = oGrid.mp_iGridBoolTransforms.get(2);
      assertEquals(iTest.intValue(), 2);
      assertEquals("bool 4", oGrid.mp_sBoolDataMembers[3]);
      iTest = oGrid.mp_iGridBoolTransforms.get(3);
      assertEquals(iTest.intValue(), 3);

      assertEquals("package float 1", oGrid.mp_sPackageFloatDataMembers[0]);
      iTest = oGrid.mp_iGridPackageFloatTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("package float 2", oGrid.mp_sPackageFloatDataMembers[1]);
      iTest = oGrid.mp_iGridPackageFloatTransforms.get(1);
      assertEquals(iTest.intValue(), 1);

      assertEquals("package int 1", oGrid.mp_sPackageIntDataMembers[0]);
      iTest = oGrid.mp_iGridPackageIntTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("package int 2", oGrid.mp_sPackageIntDataMembers[1]);
      iTest = oGrid.mp_iGridPackageIntTransforms.get(1);
      assertEquals(iTest.intValue(), 1);
      assertEquals("package int 3", oGrid.mp_sPackageIntDataMembers[2]);
      iTest = oGrid.mp_iGridPackageIntTransforms.get(2);
      assertEquals(iTest.intValue(), 2);

      assertEquals("package char 1", oGrid.mp_sPackageCharDataMembers[0]);
      iTest = oGrid.mp_iGridPackageCharTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("package char 2", oGrid.mp_sPackageCharDataMembers[1]);
      iTest = oGrid.mp_iGridPackageCharTransforms.get(1);
      assertEquals(iTest.intValue(), 1);
      assertEquals("package char 3", oGrid.mp_sPackageCharDataMembers[2]);
      iTest = oGrid.mp_iGridPackageCharTransforms.get(2);
      assertEquals(iTest.intValue(), 2);
      assertEquals("package char 4", oGrid.mp_sPackageCharDataMembers[3]);
      iTest = oGrid.mp_iGridPackageCharTransforms.get(3);
      assertEquals(iTest.intValue(), 3);

      assertEquals("package bool 1", oGrid.mp_sPackageBoolDataMembers[0]);
      iTest = oGrid.mp_iGridPackageBoolTransforms.get(0);
      assertEquals(iTest.intValue(), 0);
      assertEquals("package bool 2", oGrid.mp_sPackageBoolDataMembers[1]);
      iTest = oGrid.mp_iGridPackageBoolTransforms.get(1);
      assertEquals(iTest.intValue(), 1);
      assertEquals("package bool 3", oGrid.mp_sPackageBoolDataMembers[2]);
      iTest = oGrid.mp_iGridPackageBoolTransforms.get(2);
      assertEquals(iTest.intValue(), 2);
      assertEquals("package bool 4", oGrid.mp_sPackageBoolDataMembers[3]);
      iTest = oGrid.mp_iGridPackageBoolTransforms.get(3);
      assertEquals(iTest.intValue(), 3);
      assertEquals("package bool 5", oGrid.mp_sPackageBoolDataMembers[4]);
      iTest = oGrid.mp_iGridPackageBoolTransforms.get(4);
      assertEquals(iTest.intValue(), 4);

      //Case:  not all data members
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
      };

      p_oPackageDataMembers = new DataMember[] {

          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };

      oGrid = new Grid("test grid", p_oDataMembers, p_oPackageDataMembers, 8, 8);
      assertEquals(1, oGrid.mp_sFloatDataMembers.length);
      assertEquals(2, oGrid.mp_sIntDataMembers.length);
      assertTrue(null == oGrid.mp_sCharDataMembers);
      assertEquals(4, oGrid.mp_sBoolDataMembers.length);

      assertEquals(2, oGrid.mp_sPackageFloatDataMembers.length);
      assertEquals(3, oGrid.mp_sPackageIntDataMembers.length);
      assertEquals(4, oGrid.mp_sPackageCharDataMembers.length);
      assertTrue(null == oGrid.mp_sPackageBoolDataMembers);

      assertEquals("float 1", oGrid.mp_sFloatDataMembers[0]);

      assertEquals("int 1", oGrid.mp_sIntDataMembers[0]);
      assertEquals("int 2", oGrid.mp_sIntDataMembers[1]);

      assertEquals("bool 1", oGrid.mp_sBoolDataMembers[0]);
      assertEquals("bool 2", oGrid.mp_sBoolDataMembers[1]);
      assertEquals("bool 3", oGrid.mp_sBoolDataMembers[2]);
      assertEquals("bool 4", oGrid.mp_sBoolDataMembers[3]);

      assertEquals("package float 1", oGrid.mp_sPackageFloatDataMembers[0]);
      assertEquals("package float 2", oGrid.mp_sPackageFloatDataMembers[1]);

      assertEquals("package int 1", oGrid.mp_sPackageIntDataMembers[0]);
      assertEquals("package int 2", oGrid.mp_sPackageIntDataMembers[1]);
      assertEquals("package int 3", oGrid.mp_sPackageIntDataMembers[2]);

      assertEquals("package char 1", oGrid.mp_sPackageCharDataMembers[0]);
      assertEquals("package char 2", oGrid.mp_sPackageCharDataMembers[1]);
      assertEquals("package char 3", oGrid.mp_sPackageCharDataMembers[2]);
      assertEquals("package char 4", oGrid.mp_sPackageCharDataMembers[3]);

      //Case: no package data members
      oGrid = null;
      p_oDataMembers = null;
      p_oPackageDataMembers = null;
      p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
      };

      oGrid = new Grid("test grid", p_oDataMembers, p_oPackageDataMembers, 8, 8);
      assertEquals(1, oGrid.mp_sFloatDataMembers.length);
      assertEquals(2, oGrid.mp_sIntDataMembers.length);
      assertTrue(null == oGrid.mp_sCharDataMembers);
      assertEquals(4, oGrid.mp_sBoolDataMembers.length);

      assertTrue(null == oGrid.mp_sPackageFloatDataMembers);
      assertTrue(null == oGrid.mp_sPackageIntDataMembers);
      assertTrue(null == oGrid.mp_sPackageCharDataMembers);
      assertTrue(null == oGrid.mp_sPackageBoolDataMembers);

      assertEquals("float 1", oGrid.mp_sFloatDataMembers[0]);

      assertEquals("int 1", oGrid.mp_sIntDataMembers[0]);
      assertEquals("int 2", oGrid.mp_sIntDataMembers[1]);

      assertEquals("bool 1", oGrid.mp_sBoolDataMembers[0]);
      assertEquals("bool 2", oGrid.mp_sBoolDataMembers[1]);
      assertEquals("bool 3", oGrid.mp_sBoolDataMembers[2]);
      assertEquals("bool 4", oGrid.mp_sBoolDataMembers[3]);

      System.out.println("Grid constructor testing succeeded.");

    }
    catch (ModelException oErr) {
      fail("Constructor testing failed with message " + oErr.getMessage());
    }
  }

  /**
   * Tests setting the various types of grid values.
   */
  public void testSettingGridValues() {
    try {
      Plot oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(200);
      oPlot.setPlotYLength(200);

      //Case:  Some of each type of data member.
      DataMember[] p_oDataMembers = new DataMember[] {

          new DataMember("bool 1", "bool 1", DataMember.BOOLEAN),
          new DataMember("float 1", "float 1", DataMember.FLOAT),
          new DataMember("bool 2", "bool 2", DataMember.BOOLEAN),
          new DataMember("int 1", "int 1", DataMember.INTEGER),
          new DataMember("int 2", "int 2", DataMember.INTEGER),
          new DataMember("char 1", "char 1", DataMember.CHAR),
          new DataMember("char 2", "char 2", DataMember.CHAR),
          new DataMember("bool 3", "bool 3", DataMember.BOOLEAN),
          new DataMember("bool 4", "bool 4", DataMember.BOOLEAN),
          new DataMember("char 3", "char 3", DataMember.CHAR)
      };

      DataMember[] p_oPackageDataMembers = new DataMember[] {

          new DataMember("package bool 1", "package bool 1", DataMember.BOOLEAN),
          new DataMember("package float 1", "package float 1", DataMember.FLOAT),
          new DataMember("package float 2", "package float 2", DataMember.FLOAT),
          new DataMember("package bool 2", "package bool 2", DataMember.BOOLEAN),
          new DataMember("package int 1", "package int 1", DataMember.INTEGER),
          new DataMember("package int 2", "package int 2", DataMember.INTEGER),
          new DataMember("package int 3", "package int 3", DataMember.INTEGER),
          new DataMember("package char 1", "package char 1", DataMember.CHAR),
          new DataMember("package char 2", "package char 2", DataMember.CHAR),
          new DataMember("package bool 3", "package bool 3", DataMember.BOOLEAN),
          new DataMember("package bool 4", "package bool 4", DataMember.BOOLEAN),
          new DataMember("package bool 5", "package bool 5", DataMember.BOOLEAN),
          new DataMember("package char 3", "package char 3", DataMember.CHAR),
          new DataMember("package char 4", "package char 4", DataMember.CHAR)
      };

      Grid oGrid = new Grid("test grid", p_oDataMembers, p_oPackageDataMembers,
          8, 8);

      //Straight up value setting
      int iX = 2, iY = 3, iFloatCode = 0, iIntCode = 1, iCharCode = 2,
          iBoolCode = 3;
      oGrid.setGridValue(iX, iY, iFloatCode, new Float(0.1), oPlot);
      oGrid.setGridValue(iX, iY, iIntCode, new Integer(1), oPlot);
      oGrid.setGridValue(iX, iY, iCharCode, "test val 1", oPlot);
      oGrid.setGridValue(iX, iY, iBoolCode, new Boolean(true), oPlot);

      iFloatCode = 1;
      iIntCode = 2;
      iCharCode = 3;
      iBoolCode = 4;
      oGrid.setGridPackageValue(iX, iY, 0, iFloatCode, new Float(0.2), oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, iIntCode, new Integer(2), oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, iCharCode, "test val 2", oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, iBoolCode, new Boolean(true), oPlot);

      iFloatCode = 0;
      iIntCode = 1;
      iCharCode = 2;
      iBoolCode = 3;
      GridValue oVal = oGrid.getGridValue(iX, iY, oPlot);
      assertEquals(0.1, oVal.getFloat(iFloatCode).floatValue(), 0.01);
      assertEquals(1, oVal.getInt(iIntCode).intValue());
      assertEquals("test val 1", oVal.getChar(iCharCode));
      assertEquals(true, oVal.getBool(iBoolCode).booleanValue());

      iFloatCode = 1;
      iIntCode = 2;
      iCharCode = 3;
      iBoolCode = 4;
      PackageGridValue oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(0.2, oPackage.getFloat(iFloatCode).floatValue(), 0.01);
      assertEquals(2, oPackage.getInt(iIntCode).intValue());
      assertEquals("test val 2", oPackage.getChar(iCharCode));
      assertEquals(true, oPackage.getBool(iBoolCode).booleanValue());

      //Mess with the transforms
      oGrid.setGridBoolCode("bool 1", 5);
      oGrid.setGridFloatCode("float 1", 2);
      oGrid.setGridCharCode("char 1", 4);
      oGrid.setGridIntCode("int 1", 3);

      oGrid.setGridPackageBoolCode("package bool 1", 6);
      oGrid.setGridPackageFloatCode("package float 1", 3);
      oGrid.setGridPackageCharCode("package char 1", 5);
      oGrid.setGridPackageIntCode("package int 1", 4);

      iX = 4;
      iY = 5;
      oGrid.setGridValue(iX, iY, 2, new Float(0.1), oPlot);
      oGrid.setGridValue(iX, iY, 3, new Integer(1), oPlot);
      oGrid.setGridValue(iX, iY, 4, "test val 1", oPlot);
      oGrid.setGridValue(iX, iY, 5, new Boolean(true), oPlot);

      oGrid.setGridPackageValue(iX, iY, 0, 3, new Float(0.2), oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, 4, new Integer(2), oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, 5, "test val 2", oPlot);
      oGrid.setGridPackageValue(iX, iY, 0, 6, new Boolean(true), oPlot);

      oVal = oGrid.getGridValue(iX, iY, oPlot);
      assertEquals(0.1, oVal.getFloat(0).floatValue(), 0.01);
      assertEquals(1, oVal.getInt(0).intValue());
      assertEquals("test val 1", oVal.getChar(0));
      assertEquals(true, oVal.getBool(0).booleanValue());

      oPackage = oGrid.getPackageGridValue(oVal, 0);
      assertEquals(0.2, oPackage.getFloat(0).floatValue(), 0.01);
      assertEquals(2, oPackage.getInt(0).intValue());
      assertEquals("test val 2", oPackage.getChar(0));
      assertEquals(true, oPackage.getBool(0).booleanValue());

      System.out.println("Value setting testing succeeded.");

    }
    catch (ModelException oErr) {
      fail("Value setting failed with message " + oErr.getMessage());
    }
  }

  /**
   * Tests setting the grid cell lengths.
   */
  public void testSetCellLength() {
    try {
      float fXCellLength = 1;
      Grid oGrid = new Grid(null, null, null, fXCellLength, fXCellLength);

      assertEquals(fXCellLength, oGrid.m_fLengthXCells, 0.0001);
      assertEquals(fXCellLength, oGrid.m_fLengthYCells, 0.0001);

      assertTrue(oGrid.m_bEdited == false);
      oGrid.setXCellLength(fXCellLength);
      assertTrue(oGrid.m_bEdited == false);
      oGrid.setYCellLength(fXCellLength);
      assertTrue(oGrid.m_bEdited == false);

      float fYCellLength = 3;
      oGrid.setYCellLength(fYCellLength);
      assertTrue(oGrid.m_bEdited == true);
      assertEquals(fXCellLength, oGrid.m_fLengthXCells, 0.0001);
      assertEquals(fYCellLength, oGrid.m_fLengthYCells, 0.0001);

      oGrid.setXCellLength(0);
      oGrid.setYCellLength(0);
      oGrid.setYCellLength(fYCellLength);
      assertEquals(fYCellLength, oGrid.m_fLengthXCells, 0.0001);
      assertEquals(fYCellLength, oGrid.m_fLengthYCells, 0.0001);

      System.out.println(
          "Grid cell length setting normal processing testing succeeded.");

    }
    catch (ModelException oErr) {
      fail("Constructor testing failed with message " + oErr.getMessage());
    }

    //Exception processing
    try {
      Grid oGrid = new Grid(null, null, null, 10, 10);
      oGrid.setYCellLength( -14);
      fail("Did not catch negative value for grid cell length setting.");
    }
    catch (ModelException oErr) {
      ;
    }
    try {
      Grid oGrid = new Grid(null, null, null, 10, 10);
      oGrid.setXCellLength( -14);
      fail("Did not catch negative value for grid cell length setting.");
    }
    catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Tests XML grid map reading.
   */
  public void testGridMapReading() {
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
      sFileName = writeXMLGridMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      assertEquals(3, oManager.getTreePopulation().getTrees().size());

      Grid oGrid = oManager.getGridByName("Quadrat GLI");
      assertFalse(oGrid == null);
      assertEquals(2, oGrid.mp_oGridVals.size());
      GridValue oVal = oGrid.mp_oGridVals.get(0);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(12.65, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0, oVal.getNumberOfBools());
      assertEquals(0, oVal.getNumberOfInts());
      assertEquals(0, oVal.getNumberOfChars());

      oVal = oGrid.mp_oGridVals.get(1);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(45.22, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0, oVal.getNumberOfBools());
      assertEquals(0, oVal.getNumberOfInts());
      assertEquals(0, oVal.getNumberOfChars());

      new File(sFileName).delete();

      //Now load a grid map
      sFileName = writeXMLGridMap2();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      oGrid = oManager.getGridByName("Quadrat GLI");
      assertFalse(oGrid == null);
      assertEquals(4, oGrid.mp_oGridVals.size());
      oVal = oGrid.mp_oGridVals.get(2);
      assertEquals(10, oVal.getCell().getX());
      assertEquals(11, oVal.getCell().getY());
      assertEquals(12.65, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0, oVal.getNumberOfBools());
      assertEquals(0, oVal.getNumberOfInts());
      assertEquals(0, oVal.getNumberOfChars());

      oVal = oGrid.mp_oGridVals.get(3);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(45.22, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0, oVal.getNumberOfBools());
      assertEquals(0, oVal.getNumberOfInts());
      assertEquals(0, oVal.getNumberOfChars());

      oGrid = oManager.getGridByName("Substrate");
      assertFalse(oGrid == null);
      assertEquals(100, oGrid.mp_oGridVals.size());
      int iCount = 0;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;
      
      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(0).floatValue(), 0.0001);
      assertEquals(0.00873672, oVal.getFloat(1).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(2).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(3).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(4).floatValue(), 0.0001);
      assertEquals(0.202843, oVal.getFloat(5).floatValue(), 0.0001);
      assertEquals(4, oVal.mp_oPackages.get(0).getInt(0).intValue());
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);


      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    catch (SAXException oE) {
      fail("Parse test could not parse parameter file - message: " +
          oE.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - bad X cell length
    //We don't validate X and Y cell length anymore because I haven't taken the
    //trouble to distinguish between a user-set change and a grid map-based
    //change
    /*    try {
      oManager.ClearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      String sFileName = WriteXMLGridMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      new File(sFileName).delete();

      //Now add an error file
      sFileName = WriteErrorXMLGridMap1();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("XML grid map reading - bad X cell length was not caught.");

    } catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    //Exception processing - bad Y cell length
    try {
      oManager.ClearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      String sFileName = WriteXMLGridMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      new File(sFileName).delete();

      //Now add an error file
      sFileName = WriteErrorXMLGridMap2();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("XML grid map reading - bad Y cell length was not caught.");

    } catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } */

    //Exception processing - bad X plot length
    try {
      oManager.clearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeXMLGridMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      new File(sFileName).delete();

      //Now add an error file
      sFileName = writeErrorXMLGridMap3();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("XML grid map reading - bad X plot length was not caught.");

    } catch (ModelException oErr) {
      ;
    }
    catch (org.xml.sax.SAXException oE) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - bad Y plot length
    try {
      oManager.clearCurrentData();

      //Set up and parse our test file
      //Create our parser
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(oManager);
      oParser.setContentHandler(oHandler);
      //Parse the file
      sFileName = writeXMLGridMap1();
      InputSource oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);

      new File(sFileName).delete();

      //Now add an error file
      sFileName = writeErrorXMLGridMap4();
      oToParse = new InputSource(sFileName);
      oParser.parse(oToParse);
      fail("XML grid map reading - bad Y plot length was not caught.");

    } catch (ModelException oErr) {
      ;
    }
    catch (SAXException oE) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("XML grid map reading test succeeded.");
  }
  
  /**
   * This tests the setDataMembers and setPackageDataMembers functions. This ensures
   * that maps are respected if possible and that data member tracking happens
   * successfully.
   */
  public void testSetDataMembers() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLGridMap1();
      oManager.inputXMLParameterFile(sFileName);

      Grid oGrid = oManager.getGridByName("Substrate");
      assertFalse(oGrid == null);
      
      assertEquals(6, oGrid.mp_sFloatDataMembers.length);
      assertTrue(oGrid.getFloatCode("scarsoil") > -1);
      assertTrue(oGrid.getFloatCode("tipup") > -1);
      assertTrue(oGrid.getFloatCode("freshlog") > -1);
      assertTrue(oGrid.getFloatCode("declog") > -1);
      assertTrue(oGrid.getFloatCode("ffmoss") > -1);
      assertTrue(oGrid.getFloatCode("fflitter") > -1);
      
      assertNull(oGrid.mp_sIntDataMembers);
      assertNull(oGrid.mp_sCharDataMembers);
      assertNull(oGrid.mp_sBoolDataMembers);
      
      assertEquals(1, oGrid.mp_sPackageIntDataMembers.length);
      assertTrue(oGrid.getIntPackageCode("age") > -1);
      assertEquals(3, oGrid.mp_sPackageFloatDataMembers.length);
      assertTrue(oGrid.getFloatPackageCode("scarsoil") > -1);
      assertTrue(oGrid.getFloatPackageCode("tipup") > -1);
      assertTrue(oGrid.getFloatPackageCode("freshlog") > -1);
      assertNull(oGrid.mp_sPackageBoolDataMembers);
      assertNull(oGrid.mp_sPackageCharDataMembers);
          
      assertEquals(100, oGrid.mp_oGridVals.size());
      
      // Change up the data members
      DataMember[] p_oDataMembers = new DataMember[7];

      p_oDataMembers[0] = new DataMember("Proportion of scarified soil",
          "scarsoil",
          DataMember.FLOAT);
      p_oDataMembers[1] = new DataMember("Change1", "change1", DataMember.FLOAT);
      p_oDataMembers[2] = new DataMember("Proportion of fresh logs", "freshlog",
          DataMember.FLOAT);
      p_oDataMembers[3] = new DataMember("Proportion of decayed logs", "declog",
          DataMember.FLOAT);
      p_oDataMembers[4] = new DataMember("Proportion of forest floor moss",
          "ffmoss", DataMember.FLOAT);
      p_oDataMembers[5] = new DataMember("Diff type", "fflitter", DataMember.INTEGER);
      p_oDataMembers[6] = new DataMember("Change2", "change2", DataMember.FLOAT);

      DataMember[] p_oPackageDataMembers = new DataMember[3];

      p_oPackageDataMembers[0] = new DataMember(
          "Substrate cohort new scarified soil substrate", "scarsoil",
          DataMember.FLOAT);
      p_oPackageDataMembers[1] = new DataMember(
          "Substrate cohort new tip-up mounds substrate",
          "tipup", DataMember.FLOAT);
      p_oPackageDataMembers[2] = new DataMember("Substrate cohort new fresh logs",
          "freshlog",
          DataMember.FLOAT);
      
      oGrid.setDataMembers(p_oDataMembers, p_oPackageDataMembers);
      //Check codes and accounting
      assertEquals(6, oGrid.mp_sFloatDataMembers.length);
      int iScarsoil = oGrid.getFloatCode("scarsoil"),
          iFreshlog = oGrid.getFloatCode("freshlog"), 
          iDeclog = oGrid.getFloatCode("declog"),
          iFFMoss = oGrid.getFloatCode("ffmoss"); 
      assertTrue(iScarsoil > -1);
      assertTrue(oGrid.getFloatCode("change1") > -1);
      assertTrue(iFreshlog > -1);
      assertTrue(iDeclog > -1);
      assertTrue(iFFMoss > -1);
      assertTrue(oGrid.getFloatCode("change2") > -1);
      
      assertEquals(1, oGrid.mp_sIntDataMembers.length);
      assertTrue(oGrid.getIntCode("fflitter") > -1);
      
      assertNull(oGrid.mp_sBoolDataMembers);
      assertNull(oGrid.mp_sCharDataMembers);
      
      assertNull(oGrid.mp_sPackageIntDataMembers);
      assertTrue(oGrid.getIntPackageCode("age") == -1);
      assertEquals(3, oGrid.mp_sPackageFloatDataMembers.length);
      assertTrue(oGrid.getFloatPackageCode("scarsoil") > -1);
      assertTrue(oGrid.getFloatPackageCode("tipup") > -1);
      assertTrue(oGrid.getFloatPackageCode("freshlog") > -1);
      
      assertNull(oGrid.mp_sPackageBoolDataMembers);
      assertNull(oGrid.mp_sPackageCharDataMembers);
      
      //Make sure maps come out right: values for eliminated data members
      //erased
      int iCount = 0;

      GridValue oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(0, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(1, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(2, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(3, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(4, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(5, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(6, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(7, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;
      
      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(8, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(0, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(1, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(2, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(3, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(4, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(5, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(6, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(7, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(8, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);
      iCount++;

      oVal = oGrid.mp_oGridVals.get(iCount);
      assertEquals(9, oVal.getCell().getX());
      assertEquals(9, oVal.getCell().getY());
      assertEquals(0.0, oVal.getFloat(iScarsoil).floatValue(), 0.0001);
      assertEquals(0.0, oVal.getFloat(iFreshlog).floatValue(), 0.0001);
      assertEquals(0.239994, oVal.getFloat(iDeclog).floatValue(), 0.0001);
      assertEquals(0.548426, oVal.getFloat(iFFMoss).floatValue(), 0.0001);
      assertEquals(0.0, oVal.mp_oPackages.get(0).getFloat(0).floatValue(), 0.0001);
      assertEquals(0.13, oVal.mp_oPackages.get(0).getFloat(1).floatValue(), 0.0001);
      assertEquals(0.1, oVal.mp_oPackages.get(0).getFloat(2).floatValue(), 0.0001);

    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
 
  }

  /**
   * Writes a grid map that has different X grid length from WriteXMLGridMap2.
   * @return File name of new file.
   * @throws ModelException if there is a problem writing the file.
   */
  /*private String writeErrorXMLGridMap1() throws ModelException {
    try {
      String sFileName = "\\testgridmap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

     oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      //A false GLI grid map
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">0</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>200.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>2.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>10.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"11\" y=\"12\">");
      oOut.write("<fl c=\"0\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"10\">");
      oOut.write("<fl c=\"0\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }*/

  /**
   * Writes a grid map that has different Y grid length from WriteXMLGridMap2.
   * @return File name of new file.
   * @throws ModelException if there is a problem writing the file.
   */
  /*private String writeErrorXMLGridMap2() throws ModelException {
    try {
      String sFileName = "\\testgridmap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      //A false GLI grid map
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">0</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>200.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>10.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>2.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"12\" y=\"13\">");
      oOut.write("<fl c=\"0\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"11\">");
      oOut.write("<fl c=\"0\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }*/

  /**
   * Writes a grid map that has bad X plot length from WriteXMLGridMap2.
   * @return File name of new file.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLGridMap3() throws ModelException {
    try {
      String sFileName = "\\testgridmap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      //A false GLI grid map
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">0</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>210.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>10.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>10.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"13\" y=\"14\">");
      oOut.write("<fl c=\"0\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"11\">");
      oOut.write("<fl c=\"0\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a grid map that has bad Y plot length from WriteXMLGridMap2.
   * @return File name of new file.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeErrorXMLGridMap4() throws ModelException {
    try {
      String sFileName = "\\testgridmap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      //A false GLI grid map
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">0</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>200.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>220.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>10.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>10.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"13\" y=\"14\">");
      oOut.write("<fl c=\"0\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"11\">");
      oOut.write("<fl c=\"0\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a tree map file.  There are all four kinds of data members.  This
   * is actually a parameter file with a grid map in it.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLGridMap1() throws ModelException {
    try {
      String sFileName = "\\testgridmap1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");

      //Trees and a tree map - to make sure it doesn't interfere with the grid
      //map reading
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
      oOut.write("<tr_treemap>");
      oOut.write("<tm_treeSettings sp=\"ACSA\" tp=\"2\">");
      oOut.write("<tm_floatCodes>");
      oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
      oOut.write("<tm_floatCode label=\"DBH\">2</tm_floatCode>");
      oOut.write("</tm_floatCodes>");
      oOut.write("</tm_treeSettings>");
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"0\">6.34029</fl>");
      oOut.write("<fl c=\"1\">7.07883</fl>");
      oOut.write("<fl c=\"2\">15.7698</fl>");
      oOut.write("</tree>");
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"0\">7.34029</fl>");
      oOut.write("<fl c=\"1\">7.07883</fl>");
      oOut.write("<fl c=\"2\">15.7698</fl>");
      oOut.write("</tree>");
      oOut.write("<tree sp=\"0\" tp=\"2\">");
      oOut.write("<fl c=\"0\">8.34029</fl>");
      oOut.write("<fl c=\"1\">7.07883</fl>");
      oOut.write("<fl c=\"2\">15.7698</fl>");
      oOut.write("</tree>");
      oOut.write("</tr_treemap>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Substrate</behaviorName>");
      oOut.write("<version>2.1</version>");
      oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"TSCA\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"FRAM\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"BEAL\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"FAGR\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">0</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>200.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>10.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>10.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"0\" y=\"2\">");
      oOut.write("<fl c=\"0\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"1\">");
      oOut.write("<fl c=\"0\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("<grid gridName=\"Substrate\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"scarsoil\">0</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"tipup\">1</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"freshlog\">2</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"declog\">3</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"ffmoss\">4</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"fflitter\">5</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_packageIntCodes>");
      oOut.write("<ma_intCode label=\"age\">0</ma_intCode>");
      oOut.write("</ma_packageIntCodes>");
      oOut.write("<ma_packageFloatCodes>");
      oOut.write("<ma_floatCode label=\"scarsoil\">0</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"tipup\">1</ma_floatCode>");
      oOut.write("<ma_floatCode label=\"freshlog\">2</ma_floatCode>");
      oOut.write("</ma_packageFloatCodes>");
      oOut.write("<ma_plotLenX>200.0</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.0</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>20.0</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>20.0</ma_lengthYCells>");
      oOut.write("<ma_v x=\"0\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"0\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"1\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"2\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"3\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"5\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"6\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"7\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"8\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"0\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"1\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"2\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"3\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"4\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"5\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"6\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"7\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"8\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"9\" y=\"9\">");
      oOut.write("<fl c=\"0\">0.0</fl>");
      oOut.write("<fl c=\"1\">0.00873672</fl>");
      oOut.write("<fl c=\"2\">0.0</fl>");
      oOut.write("<fl c=\"3\">0.239994</fl>");
      oOut.write("<fl c=\"4\">0.548426</fl>");
      oOut.write("<fl c=\"5\">0.202843</fl>");
      oOut.write("<pkg>");
      oOut.write("<pint c=\"0\">4</pint>");
      oOut.write("<pfl c=\"0\">0.0</pfl>");
      oOut.write("<pfl c=\"1\">0.13</pfl>");
      oOut.write("<pfl c=\"2\">0.1</pfl>");
      oOut.write("</pkg>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("<GeneralLight>");
      oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
      oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
      oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
      oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
      oOut.write("<li_lightExtinctionCoefficient>");
      oOut.write("<li_lecVal species=\"ACSA\">0.08</li_lecVal>");
      oOut.write("</li_lightExtinctionCoefficient>");
      oOut.write("</GeneralLight>");
      oOut.write("<QuadratLight2>");
      oOut.write("<li_minSunAngle>0.885</li_minSunAngle>");
      oOut.write("<li_numAltGrids>13</li_numAltGrids>");
      oOut.write("<li_numAziGrids>19</li_numAziGrids>");
      oOut.write("</QuadratLight2>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * This writes a fake rundata file with a grid map in it.
   * @return File name of new file.
   * @throws ModelException if there is a problem writing the file.
   */
  private String writeXMLGridMap2() throws ModelException {
    try {
      String sFileName = "\\testgridmap2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<timestepRundata fileCode=\"06010701\">");
      oOut.write("<rt_timestep>0</rt_timestep>");

      //A false GLI grid map
      oOut.write("<grid gridName=\"Quadrat GLI\">");
      oOut.write("<ma_floatCodes>");
      oOut.write("<ma_floatCode label=\"GLI\">2</ma_floatCode>");
      oOut.write("</ma_floatCodes>");
      oOut.write("<ma_plotLenX>200.000000</ma_plotLenX>");
      oOut.write("<ma_plotLenY>200.000000</ma_plotLenY>");
      oOut.write("<ma_lengthXCells>10.000000</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>10.000000</ma_lengthYCells>");
      oOut.write("<ma_v x=\"10\" y=\"11\">");
      oOut.write("<fl c=\"2\">12.65</fl>");
      oOut.write("</ma_v>");
      oOut.write("<ma_v x=\"4\" y=\"9\">");
      oOut.write("<fl c=\"2\">45.22</fl>");
      oOut.write("</ma_v>");
      oOut.write("</grid>");
      oOut.write("</timestepRundata>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    int iX, iY, iNumX = 20, iNumY = 20;

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>5</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
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
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">4</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>5</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");;

    for (iX = 0; iX < iNumX; iX++)
    {
      for (iY = 0; iY < iNumY; iY++)
      {
        oOut.write("<ma_v x=\"" + iX + "\" y=\"" + iY + "\">");
        oOut.write("<fl c=\"0\">" + (iX + iY) + "</fl>");
        oOut.write("<fl c=\"1\">" + (2 * (iX + iY)) + "</fl>");
        oOut.write("<fl c=\"2\">" + (3 * (iX + iY)) + "</fl>");
        oOut.write("</ma_v>");
      }
    }
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Resource\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Resource\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>4</ma_lengthYCells>");
    oOut.write("<ma_v x=\"1\" y=\"12\">");
    oOut.write("<fl c=\"0\">0.1</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"2\" y=\"11\">");
    oOut.write("<fl c=\"0\">10</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"10\">");
    oOut.write("<fl c=\"0\">3</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"5\" y=\"8\">");
    oOut.write("<fl c=\"0\">4</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"6\" y=\"7\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"7\" y=\"6\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"8\" y=\"5\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"10\" y=\"3\">");
    oOut.write("<fl c=\"0\">20</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"11\" y=\"2\">");
    oOut.write("<fl c=\"0\">2</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"12\" y=\"1\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Double resource relative diam with auto height</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>"); 
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>"); 
    oOut.write("</behavior>"); 
    oOut.write("<behavior>");
    oOut.write("<behaviorName>germination</behaviorName>"); 
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>"); 
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>"); 
    oOut.write("</behaviorList>");
    oOut.write("<growth>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.799</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.019</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_diamDoubleMMResourceInfluence>");
    oOut.write("<gr_ddmmriVal species=\"Species_2\">10</gr_ddmmriVal>");
    oOut.write("</gr_diamDoubleMMResourceInfluence>");
    oOut.write("</growth>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_1\">1</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">1</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">1</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<establishment>"); 
    oOut.write("<ge_proportionGerminating>"); 
    oOut.write("<ge_pgVal species=\"Species_1\">0.3</ge_pgVal>");
    oOut.write("<ge_pgVal species=\"Species_2\">0.7</ge_pgVal>"); 
    oOut.write("</ge_proportionGerminating>");
    oOut.write("</establishment>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a parameter file with an unused grid, to test fail-safeness.
   * @return The file name.
   * @throws ModelException
   */
  private String writeXMLFileGrid1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1000</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("<plot_latitude>-43.53</plot_latitude>");
    oOut.write("<plot_title>testwaitutu</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"NOTCLI\"/>");
    oOut.write("<tr_species speciesName=\"NOTMEN\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"NOTCLI\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"NOTMEN\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"NOTCLI\">2.0</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"NOTMEN\">2.0</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("<tr_treemap>");
    oOut.write("<tm_treeSettings sp=\"NOTCLI\" tp=\"2\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tm_treeSettings sp=\"NOTMEN\" tp=\"2\">");
    oOut.write("<tm_floatCodes>");
    oOut.write("<tm_floatCode label=\"X\">0</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Y\">1</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"Height\">2</tm_floatCode>");
    oOut.write("<tm_floatCode label=\"DBH\">3</tm_floatCode>");
    oOut.write("</tm_floatCodes>");
    oOut.write("</tm_treeSettings>");
    oOut.write("<tree sp=\"1\" tp=\"2\">");
    oOut.write("<fl c=\"0\">246.41945</fl>");
    oOut.write("<fl c=\"1\">298.5247</fl>");
    oOut.write("<fl c=\"2\">10.0</fl>");
    oOut.write("<fl c=\"3\">27.737665</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"2\">");
    oOut.write("<fl c=\"0\">270.4987</fl>");
    oOut.write("<fl c=\"1\">279.84903</fl>");
    oOut.write("<fl c=\"2\">10.0</fl>");
    oOut.write("<fl c=\"3\">37.878666</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"1\" tp=\"2\">");
    oOut.write("<fl c=\"0\">210.5483</fl>");
    oOut.write("<fl c=\"1\">286.62756</fl>");
    oOut.write("<fl c=\"2\">10.0</fl>");
    oOut.write("<fl c=\"3\">29.143831</fl>");
    oOut.write("</tree>");
    oOut.write("<tree sp=\"0\" tp=\"2\">");
    oOut.write("<fl c=\"0\">240.3584</fl>");
    oOut.write("<fl c=\"1\">263.18625</fl>");
    oOut.write("<fl c=\"2\">10.0</fl>");
    oOut.write("<fl c=\"3\">47.38765</fl>");
    oOut.write("</tree>");
    oOut.write("</tr_treemap>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>QuadratLight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIBAGrowth</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>5</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RelUnlimGrowth</behaviorName>");
    oOut.write("<version>1.2</version>");
    oOut.write("<listPosition>6</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DiameterIncrementer</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RelativeHeight</behaviorName>");
    oOut.write("<version>1.2</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>7</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>10</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>13</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonGapDisperse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>14</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>16</listPosition>");
    oOut.write("<applyTo species=\"NOTCLI\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"NOTMEN\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Basal Area Light\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Light\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"Con BA\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"Ang BA\">2</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_plotLenX>300.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>300.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>5.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5.0</ma_lengthYCells>");
    oOut.write("<ma_v x=\"0\" y=\"2\">");
    oOut.write("<fl c=\"0\">12.65</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"1\">");
    oOut.write("<fl c=\"0\">45.22</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Storm Damage\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"dmg_index\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"stormtime\">1</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_packageFloatCodes>");
    oOut.write("<ma_floatCode label=\"1dmg_index\">0</ma_floatCode>");
    oOut.write("</ma_packageFloatCodes>");
    oOut.write("<ma_plotLenX>300.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>300.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>20.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>20.0</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Storm Susceptibility\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"index\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_plotLenX>300.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>300.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>20.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>20.0</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Substrate\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"scarsoil\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tipup\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"ffmoss\">4</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fflitter\">5</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_packageIntCodes>");
    oOut.write("<ma_intCode label=\"age\">0</ma_intCode>");
    oOut.write("</ma_packageIntCodes>");
    oOut.write("<ma_packageFloatCodes>");
    oOut.write("<ma_floatCode label=\"scarsoil\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tipup\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog\">2</ma_floatCode>");
    oOut.write("</ma_packageFloatCodes>");
    oOut.write("<ma_plotLenX>300.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>300.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>8.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>8.0</ma_lengthYCells>");
    oOut.write("<ma_v x=\"0\" y=\"0\">");
    oOut.write("<fl c=\"0\">0.0</fl>");
    oOut.write("<fl c=\"1\">0.00873672</fl>");
    oOut.write("<fl c=\"2\">0.0</fl>");
    oOut.write("<fl c=\"3\">0.239994</fl>");
    oOut.write("<fl c=\"4\">0.548426</fl>");
    oOut.write("<fl c=\"5\">0.202843</fl>");
    oOut.write("<pkg>");
    oOut.write("<pint c=\"0\">4</pint>");
    oOut.write("<pfl c=\"0\">0.0</pfl>");
    oOut.write("<pfl c=\"1\">0.13</pfl>");
    oOut.write("<pfl c=\"2\">0.1</pfl>");
    oOut.write("</pkg>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"0\" y=\"1\">");
    oOut.write("<fl c=\"0\">0.0</fl>");
    oOut.write("<fl c=\"1\">0.00873672</fl>");
    oOut.write("<fl c=\"2\">0.0</fl>");
    oOut.write("<fl c=\"3\">0.239994</fl>");
    oOut.write("<fl c=\"4\">0.548426</fl>");
    oOut.write("<fl c=\"5\">0.202843</fl>");
    oOut.write("<pkg>");
    oOut.write("<pint c=\"0\">4</pint>");
    oOut.write("<pfl c=\"0\">0.0</pfl>");
    oOut.write("<pfl c=\"1\">0.13</pfl>");
    oOut.write("<pfl c=\"2\">0.1</pfl>");
    oOut.write("</pkg>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"0\" y=\"2\">");
    oOut.write("<fl c=\"0\">0.0</fl>");
    oOut.write("<fl c=\"1\">0.00873672</fl>");
    oOut.write("<fl c=\"2\">0.0</fl>");
    oOut.write("<fl c=\"3\">0.239994</fl>");
    oOut.write("<fl c=\"4\">0.548426</fl>");
    oOut.write("<fl c=\"5\">0.202843</fl>");
    oOut.write("<pkg>");
    oOut.write("<pint c=\"0\">4</pint>");
    oOut.write("<pfl c=\"0\">0.0</pfl>");
    oOut.write("<pfl c=\"1\">0.13</pfl>");
    oOut.write("<pfl c=\"2\">0.1</pfl>");
    oOut.write("</pkg>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_plotLenX>300.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>300.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>10.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>10.0</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"NOTCLI\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"NOTMEN\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"NOTCLI\">3</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"NOTMEN\">3</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"NOTCLI\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"NOTMEN\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"NOTCLI\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"NOTMEN\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"NOTCLI\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"NOTMEN\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"NOTCLI\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"NOTMEN\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"NOTCLI\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"NOTMEN\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"NOTCLI\">30.13</tr_chVal>");
    oOut.write("<tr_chVal species=\"NOTMEN\">28.93</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"NOTCLI\">0.37506</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"NOTMEN\">0.22116</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"NOTCLI\">0.474</tr_screVal>");
    oOut.write("<tr_screVal species=\"NOTMEN\">0.634</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"NOTCLI\">0.339</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"NOTMEN\">0.12</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"NOTCLI\">1.037</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"NOTMEN\">1.365</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"NOTCLI\">1.0537956</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"NOTMEN\">0.8695786</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"NOTCLI\">-1.2176225</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"NOTMEN\">-0.584685</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"NOTCLI\">0.041</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"NOTMEN\">0.047</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"NOTCLI\">0.0</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"NOTMEN\">0.0</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_saplingPowerA>");
    oOut.write("<tr_sapaVal species=\"NOTCLI\">1.2819</tr_sapaVal>");
    oOut.write("<tr_sapaVal species=\"NOTMEN\">1.5218</tr_sapaVal>");
    oOut.write("</tr_saplingPowerA>");
    oOut.write("<tr_saplingPowerB>");
    oOut.write("<tr_sapbVal species=\"NOTCLI\">0.97</tr_sapbVal>");
    oOut.write("<tr_sapbVal species=\"NOTMEN\">0.81</tr_sapbVal>");
    oOut.write("</tr_saplingPowerB>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"NOTCLI\">0.6889008</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"NOTMEN\">0.7224122</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"NOTCLI\">0.1</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"NOTMEN\">0.1</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("</allometry>");
    oOut.write("<GeneralLight>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"NOTCLI\">0.0672</li_lecVal>");
    oOut.write("<li_lecVal species=\"NOTMEN\">0.076</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("</GeneralLight>");
    oOut.write("<GLILight2>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("</GLILight2>");
    oOut.write("<QuadratLight1>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_quadratLightHeight>0.675</li_quadratLightHeight>");
    oOut.write("<li_quadratAllGLIs>0</li_quadratAllGLIs>");
    oOut.write("</QuadratLight1>");
    oOut.write("<NCIBAGrowth5>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"NOTCLI\">0.28768012</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"NOTMEN\">0.27427495</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"NOTCLI\">11.2</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"NOTMEN\">11.2</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"NOTCLI\">0.0</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"NOTMEN\">0.0</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");
    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"NOTCLI\">3.21E-31</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"NOTMEN\">0.048501156</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");
    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"NOTCLI\">3.0901406</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"NOTMEN\">0.001236313</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");
    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"NOTCLI\">10.0</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"NOTMEN\">10.0</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");
    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"NOTCLI\">75.12871</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"NOTMEN\">73.997314</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");
    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"NOTCLI\">1.7305545</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"NOTMEN\">1.8144404</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");
    oOut.write("<gr_banciBADivisor>1000.0</gr_banciBADivisor>");
    oOut.write("<gr_banciOnlyLargerNeighbors>1</gr_banciOnlyLargerNeighbors>");
    oOut.write("</NCIBAGrowth5>");
    oOut.write("<RelUnlimGrowth6>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"NOTCLI\">0.0699375</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"NOTMEN\">0.036319178</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"NOTCLI\">0.006031828</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"NOTMEN\">0.009934775</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_relGrowthDiamExp>");
    oOut.write("<gr_rgdeVal species=\"NOTCLI\">0.55</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"NOTMEN\">0.55</gr_rgdeVal>");
    oOut.write("</gr_relGrowthDiamExp>");
    oOut.write("</RelUnlimGrowth6>");
    oOut.write("<RelMMHeight4>");
    oOut.write("<gr_asympHeightGrowth>");
    oOut.write("<gr_ahgVal species=\"NOTCLI\">1.05399</gr_ahgVal>");
    oOut.write("<gr_ahgVal species=\"NOTMEN\">0.2046825</gr_ahgVal>");
    oOut.write("</gr_asympHeightGrowth>");
    oOut.write("<gr_slopeHeightGrowthResponse>");
    oOut.write("<gr_shgrVal species=\"NOTCLI\">3.29854</gr_shgrVal>");
    oOut.write("<gr_shgrVal species=\"NOTMEN\">0.49897686</gr_shgrVal>");
    oOut.write("</gr_slopeHeightGrowthResponse>");
    oOut.write("<gr_relHeightGrowthHeightExp>");
    oOut.write("<gr_rhgheVal species=\"NOTCLI\">0.01434</gr_rhgheVal>");
    oOut.write("<gr_rhgheVal species=\"NOTMEN\">0.807854</gr_rhgheVal>");
    oOut.write("</gr_relHeightGrowthHeightExp>");
    oOut.write("</RelMMHeight4>");
    oOut.write("<StochasticMortality7>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"NOTCLI\">0.010386839</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"NOTMEN\">0.013640402</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality7>");
    oOut.write("<StochasticMortality10>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"NOTCLI\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"NOTMEN\">0.0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality10>");
    oOut.write("<NonGapDisperse14>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"NOTCLI\">10.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"NOTMEN\">10.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"NOTCLI\">1.276294</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"NOTMEN\">0.0</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"NOTCLI\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"NOTMEN\">0.0</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"NOTCLI\">0.07783747</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"NOTMEN\">0.0</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"NOTCLI\">1.1304</di_wctVal>");
    oOut.write("<di_wctVal species=\"NOTMEN\">0.0</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"NOTCLI\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"NOTMEN\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</NonGapDisperse14>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

}
