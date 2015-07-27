package sortie.data.simpletypes;

import sortie.ModelTestCase;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.DetailedGridSettings;

/**
 * Tests the DetailedOutputSettings class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestDetailedGridSettings
    extends ModelTestCase {

  /** Object to test on */
  private DetailedGridSettings m_oDetailedGridSettings = null;
  private DetailedGridSettings m_oCopyTarget = null;

  /**
   * Does setup.
   * @throws Exception Won't.
   */
  protected void setUp() throws Exception {
    super.setUp();
    m_oDetailedGridSettings = new DetailedGridSettings("Test Grid Settings");
    assertEquals(0, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals(0, m_oDetailedGridSettings.getSaveFrequency());
    assertEquals("Test Grid Settings", m_oDetailedGridSettings.getName());
  }

  /**
   * Does teardown
   * @throws Exception Won't.
   */
  protected void tearDown() throws Exception {
    m_oDetailedGridSettings = null;
    super.tearDown();
  }

  protected void TestCloning() {
    //test cloning
    m_oCopyTarget = (DetailedGridSettings) m_oDetailedGridSettings.clone();
    assertEquals(1, m_oCopyTarget.getNumberOfBools());
    assertEquals(2, m_oCopyTarget.getNumberOfChars());
    assertEquals(3, m_oCopyTarget.getNumberOfFloats());
    assertEquals(4, m_oCopyTarget.getNumberOfInts());
    assertEquals(1, m_oCopyTarget.getNumberOfPackageBools());
    assertEquals(2, m_oCopyTarget.getNumberOfPackageChars());
    assertEquals(3, m_oCopyTarget.getNumberOfPackageFloats());
    assertEquals(4, m_oCopyTarget.getNumberOfPackageInts());
    assertEquals(5, m_oCopyTarget.getSaveFrequency());
    assertEquals(m_oCopyTarget.getName(), m_oDetailedGridSettings.getName());

    //Data member names
    assertEquals(m_oDetailedGridSettings.getBool(0).getCodeName(),
                 m_oCopyTarget.getBool(0).getCodeName());
    assertEquals(m_oDetailedGridSettings.getFloat(0).getDisplayName(),
                 m_oCopyTarget.getFloat(0).getDisplayName());
    assertEquals(m_oDetailedGridSettings.getInt(0).getDisplayName(),
                 m_oCopyTarget.getInt(0).getDisplayName());
    assertEquals(m_oDetailedGridSettings.getChar(0).getDisplayName(),
                 m_oCopyTarget.getChar(0).getDisplayName());
    assertEquals(m_oDetailedGridSettings.getPackageBool(0).getCodeName(),
                 m_oCopyTarget.getPackageBool(0).getCodeName());
    assertEquals(m_oDetailedGridSettings.getPackageFloat(0).getDisplayName(),
                 m_oCopyTarget.getPackageFloat(0).getDisplayName());
    assertEquals(m_oDetailedGridSettings.getPackageInt(0).getDisplayName(),
                 m_oCopyTarget.getPackageInt(0).getDisplayName());
    assertEquals(m_oDetailedGridSettings.getPackageChar(0).getDisplayName(),
                 m_oCopyTarget.getPackageChar(0).getDisplayName());

    //Make sure the clone was deep
    DataMember oMember = m_oDetailedGridSettings.getBool(0);
    int iSaveCode = oMember.getCode();
    oMember.setCode(iSaveCode+4);

    assertEquals(iSaveCode, m_oCopyTarget.getBool(0).getCode());
  }

  protected void TestRemoval() {
    /////////////////////////////////
    //Test data member removal
    /////////////////////////////////

    //Invalid index
    m_oDetailedGridSettings.removeBool(-1);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfBools());
    m_oDetailedGridSettings.removeBool(1);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfBools());
    //Good index
    m_oDetailedGridSettings.removeBool(0);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    //Float-checking deep cloning...
    assertEquals(1, m_oCopyTarget.getNumberOfBools());

    //Invalid index
    m_oDetailedGridSettings.removeChar(-1);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfChars());
    m_oDetailedGridSettings.removeChar(5);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfChars());
    //Good index
    m_oDetailedGridSettings.removeChar(0);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals("test char 2", m_oDetailedGridSettings.getChar(0).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(2, m_oCopyTarget.getNumberOfChars());

    //Invalid index
    m_oDetailedGridSettings.removeFloat(-1);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    m_oDetailedGridSettings.removeFloat(5);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    //Good index
    m_oDetailedGridSettings.removeFloat(2);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals("test float 1", m_oDetailedGridSettings.getFloat(0).getDisplayName());
    assertEquals("test float 2", m_oDetailedGridSettings.getFloat(1).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(3, m_oCopyTarget.getNumberOfFloats());

    //Invalid index
    m_oDetailedGridSettings.removeInt(-1);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    m_oDetailedGridSettings.removeInt(5);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    //Good index
    m_oDetailedGridSettings.removeInt(2);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals("test int 1", m_oDetailedGridSettings.getInt(0).getDisplayName());
    assertEquals("test int 2", m_oDetailedGridSettings.getInt(1).getDisplayName());
    assertEquals("test int 4", m_oDetailedGridSettings.getInt(2).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(4, m_oCopyTarget.getNumberOfInts());

    //////////////////////
    // Repeat with packages
    //////////////////////
    //Invalid index
    m_oDetailedGridSettings.removePackageBool(-1);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageBools());
    m_oDetailedGridSettings.removePackageBool(1);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageBools());
    //Good index
    m_oDetailedGridSettings.removePackageBool(0);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    //Float-checking deep cloning...
    assertEquals(1, m_oCopyTarget.getNumberOfPackageBools());

    //Invalid index
    m_oDetailedGridSettings.removePackageChar(-1);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageChars());
    m_oDetailedGridSettings.removePackageChar(5);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageChars());
    //Good index
    m_oDetailedGridSettings.removePackageChar(0);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package char 2", m_oDetailedGridSettings.getPackageChar(0).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(2, m_oCopyTarget.getNumberOfPackageChars());

    //Invalid index
    m_oDetailedGridSettings.removePackageFloat(-1);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageFloats());
    m_oDetailedGridSettings.removePackageFloat(5);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageFloats());
    //Good index
    m_oDetailedGridSettings.removePackageFloat(2);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package float 1", m_oDetailedGridSettings.getPackageFloat(0).getDisplayName());
    assertEquals("test package float 2", m_oDetailedGridSettings.getPackageFloat(1).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(3, m_oCopyTarget.getNumberOfPackageFloats());

    //Invalid index
    m_oDetailedGridSettings.removePackageInt(-1);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    m_oDetailedGridSettings.removePackageInt(5);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    //Good index
    m_oDetailedGridSettings.removePackageInt(2);
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package int 1", m_oDetailedGridSettings.getPackageInt(0).getDisplayName());
    assertEquals("test package int 2", m_oDetailedGridSettings.getPackageInt(1).getDisplayName());
    assertEquals("test package int 4", m_oDetailedGridSettings.getPackageInt(2).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(4, m_oCopyTarget.getNumberOfPackageInts());
  }

  /**
   * This concentrates on adding using both a code name and a display name.
   */
  protected void TestAdding() {
    String sDisplayName = "test bool 1";
    String sCodeName = "bool1";
    m_oDetailedGridSettings.addBool(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(sCodeName, m_oDetailedGridSettings.getBool(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getBool(0).getDisplayName());

    sDisplayName = "test char 1";
    sCodeName = "char1";
    m_oDetailedGridSettings.addChar(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(sCodeName, m_oDetailedGridSettings.getChar(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getChar(0).getDisplayName());

    sDisplayName = "test char 2";
    sCodeName = "char2";
    m_oDetailedGridSettings.addChar(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals("test char 1", m_oDetailedGridSettings.getChar(0).getDisplayName());
    assertEquals("char1", m_oDetailedGridSettings.getChar(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getChar(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getChar(1).getDisplayName());

    sDisplayName = "test float 1";
    sCodeName = "float1";
    m_oDetailedGridSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(sCodeName, m_oDetailedGridSettings.getFloat(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getFloat(0).getDisplayName());

    sDisplayName = "test float 2";
    sCodeName = "float2";
    m_oDetailedGridSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals("test float 1", m_oDetailedGridSettings.getFloat(0).getDisplayName());
    assertEquals("float1", m_oDetailedGridSettings.getFloat(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getFloat(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getFloat(1).getDisplayName());

    sDisplayName = "test float 3";
    sCodeName = "float3";
    m_oDetailedGridSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals("test float 1", m_oDetailedGridSettings.getFloat(0).getDisplayName());
    assertEquals("float1", m_oDetailedGridSettings.getFloat(0).getCodeName());
    assertEquals("test float 2", m_oDetailedGridSettings.getFloat(1).getDisplayName());
    assertEquals("float2", m_oDetailedGridSettings.getFloat(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getFloat(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getFloat(2).getDisplayName());

    sDisplayName = "test int 1";
    sCodeName = "int1";
    m_oDetailedGridSettings.addInt(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals(sCodeName, m_oDetailedGridSettings.getInt(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getInt(0).getDisplayName());

    sDisplayName = "test int 2";
    sCodeName = "int2";
    m_oDetailedGridSettings.addInt(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test int 1", m_oDetailedGridSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedGridSettings.getInt(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getInt(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getInt(1).getDisplayName());

    sDisplayName = "test int 3";
    sCodeName = "int3";
    m_oDetailedGridSettings.addInt(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test int 1", m_oDetailedGridSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedGridSettings.getInt(0).getCodeName());
    assertEquals("test int 2", m_oDetailedGridSettings.getInt(1).getDisplayName());
    assertEquals("int2", m_oDetailedGridSettings.getInt(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getInt(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getInt(2).getDisplayName());

    sDisplayName = "test int 4";
    sCodeName = "int4";
    m_oDetailedGridSettings.addInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test int 1", m_oDetailedGridSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedGridSettings.getInt(0).getCodeName());
    assertEquals("test int 2", m_oDetailedGridSettings.getInt(1).getDisplayName());
    assertEquals("int2", m_oDetailedGridSettings.getInt(1).getCodeName());
    assertEquals("test int 3", m_oDetailedGridSettings.getInt(2).getDisplayName());
    assertEquals("int3", m_oDetailedGridSettings.getInt(2).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getInt(3).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getInt(3).getDisplayName());

    sDisplayName = "test package bool 1";
    sCodeName = "packagebool1";
    m_oDetailedGridSettings.addPackageBool(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedGridSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageBools());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageBool(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageBool(0).getDisplayName());

    sDisplayName = "test package char 1";
    sCodeName = "packagechar1";
    m_oDetailedGridSettings.addPackageChar(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfChars());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageChar(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageChar(0).getDisplayName());

    sDisplayName = "test package char 2";
    sCodeName = "packagechar2";
    m_oDetailedGridSettings.addPackageChar(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageChars());
    assertEquals("test package char 1", m_oDetailedGridSettings.getPackageChar(0).getDisplayName());
    assertEquals("packagechar1", m_oDetailedGridSettings.getPackageChar(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageChar(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageChar(1).getDisplayName());

    sDisplayName = "test package float 1";
    sCodeName = "packagefloat1";
    m_oDetailedGridSettings.addPackageFloat(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageFloat(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageFloat(0).getDisplayName());

    sDisplayName = "test package float 2";
    sCodeName = "packagefloat2";
    m_oDetailedGridSettings.addPackageFloat(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals("test package float 1", m_oDetailedGridSettings.getPackageFloat(0).getDisplayName());
    assertEquals("packagefloat1", m_oDetailedGridSettings.getPackageFloat(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageFloat(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageFloat(1).getDisplayName());

    sDisplayName = "test package float 3";
    sCodeName = "packagefloat3";
    m_oDetailedGridSettings.addPackageFloat(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedGridSettings.getNumberOfFloats());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageFloats());
    assertEquals("test package float 1", m_oDetailedGridSettings.getPackageFloat(0).getDisplayName());
    assertEquals("packagefloat1", m_oDetailedGridSettings.getPackageFloat(0).getCodeName());
    assertEquals("test package float 2", m_oDetailedGridSettings.getPackageFloat(1).getDisplayName());
    assertEquals("packagefloat2", m_oDetailedGridSettings.getPackageFloat(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageFloat(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageFloat(2).getDisplayName());

    sDisplayName = "test package int 1";
    sCodeName = "packageint1";
    m_oDetailedGridSettings.addPackageInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(1, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageInt(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageInt(0).getDisplayName());

    sDisplayName = "test package int 2";
    sCodeName = "packageint2";
    m_oDetailedGridSettings.addPackageInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(2, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package int 1", m_oDetailedGridSettings.getPackageInt(0).getDisplayName());
    assertEquals("packageint1", m_oDetailedGridSettings.getPackageInt(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageInt(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageInt(1).getDisplayName());

    sDisplayName = "test package int 3";
    sCodeName = "packageint3";
    m_oDetailedGridSettings.addPackageInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(3, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package int 1", m_oDetailedGridSettings.getPackageInt(0).getDisplayName());
    assertEquals("packageint1", m_oDetailedGridSettings.getPackageInt(0).getCodeName());
    assertEquals("test package int 2", m_oDetailedGridSettings.getPackageInt(1).getDisplayName());
    assertEquals("packageint2", m_oDetailedGridSettings.getPackageInt(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageInt(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageInt(2).getDisplayName());

    sDisplayName = "test package int 4";
    sCodeName = "packageint4";
    m_oDetailedGridSettings.addPackageInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedGridSettings.getNumberOfInts());
    assertEquals(4, m_oDetailedGridSettings.getNumberOfPackageInts());
    assertEquals("test package int 1", m_oDetailedGridSettings.getPackageInt(0).getDisplayName());
    assertEquals("packageint1", m_oDetailedGridSettings.getPackageInt(0).getCodeName());
    assertEquals("test package int 2", m_oDetailedGridSettings.getPackageInt(1).getDisplayName());
    assertEquals("packageint2", m_oDetailedGridSettings.getPackageInt(1).getCodeName());
    assertEquals("test package int 3", m_oDetailedGridSettings.getPackageInt(2).getDisplayName());
    assertEquals("packageint3", m_oDetailedGridSettings.getPackageInt(2).getCodeName());
    assertEquals(sCodeName, m_oDetailedGridSettings.getPackageInt(3).getCodeName());
    assertEquals(sDisplayName, m_oDetailedGridSettings.getPackageInt(3).getDisplayName());

    m_oDetailedGridSettings.setSaveFrequency(5);
    assertEquals(5, m_oDetailedGridSettings.getSaveFrequency());
  }

  /**
   * Tests detailed output settings functionality. Order of test functions
   * is important.
   */
  public void testEverything() {

    TestAdding();
    TestCloning();
    TestRemoval();

  }
}
