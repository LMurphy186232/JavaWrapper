package sortie.data.simpletypes;

import sortie.ModelTestCase;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.DetailedOutputSettings;

/**
 * Tests the DetailedOutputSettings class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestDetailedOutputSettings
    extends ModelTestCase {

  /** Object to test on */
  private DetailedOutputSettings m_oDetailedOutputSettings = null;

  /**
   * Does setup.
   * @throws Exception Won't.
   */
  protected void setUp() throws Exception {
    super.setUp();
    m_oDetailedOutputSettings = new DetailedOutputSettings();
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals(0, m_oDetailedOutputSettings.getSaveFrequency());
  }

  /**
   * Does teardown
   * @throws Exception Won't.
   */
  protected void tearDown() throws Exception {
    m_oDetailedOutputSettings = null;
    super.tearDown();
  }

  /**
   * Tests detailed output settings functionality.
   */
  public void testEverything() {
    String sDisplayName = "test bool 1";
    String sCodeName = "bool1";
    m_oDetailedOutputSettings.addBool(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getBool(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getBool(0).getDisplayName());

    sDisplayName = "test char 1";
    sCodeName = "char1";
    m_oDetailedOutputSettings.addChar(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getChar(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getChar(0).getDisplayName());

    sDisplayName = "test char 2";
    sCodeName = "char2";
    m_oDetailedOutputSettings.addChar(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals("test char 1", m_oDetailedOutputSettings.getChar(0).getDisplayName());
    assertEquals("char1", m_oDetailedOutputSettings.getChar(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getChar(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getChar(1).getDisplayName());

    sDisplayName = "test float 1";
    sCodeName = "float1";
    m_oDetailedOutputSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getFloat(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getFloat(0).getDisplayName());

    sDisplayName = "test float 2";
    sCodeName = "float2";
    m_oDetailedOutputSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals("test float 1", m_oDetailedOutputSettings.getFloat(0).getDisplayName());
    assertEquals("float1", m_oDetailedOutputSettings.getFloat(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getFloat(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getFloat(1).getDisplayName());

    sDisplayName = "test float 3";
    sCodeName = "float3";
    m_oDetailedOutputSettings.addFloat(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals("test float 1", m_oDetailedOutputSettings.getFloat(0).getDisplayName());
    assertEquals("float1", m_oDetailedOutputSettings.getFloat(0).getCodeName());
    assertEquals("test float 2", m_oDetailedOutputSettings.getFloat(1).getDisplayName());
    assertEquals("float2", m_oDetailedOutputSettings.getFloat(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getFloat(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getFloat(2).getDisplayName());

    sDisplayName = "test int 1";
    sCodeName = "int1";
    m_oDetailedOutputSettings.addInt(sCodeName, sDisplayName);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getInt(0).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getInt(0).getDisplayName());

    sDisplayName = "test int 2";
    sCodeName = "int2";
    m_oDetailedOutputSettings.addInt(sCodeName, sDisplayName);
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test int 1", m_oDetailedOutputSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedOutputSettings.getInt(0).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getInt(1).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getInt(1).getDisplayName());

    sDisplayName = "test int 3";
    sCodeName = "int3";
    m_oDetailedOutputSettings.addInt(sCodeName, sDisplayName);
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test int 1", m_oDetailedOutputSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedOutputSettings.getInt(0).getCodeName());
    assertEquals("test int 2", m_oDetailedOutputSettings.getInt(1).getDisplayName());
    assertEquals("int2", m_oDetailedOutputSettings.getInt(1).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getInt(2).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getInt(2).getDisplayName());

    sDisplayName = "test int 4";
    sCodeName = "int4";
    m_oDetailedOutputSettings.addInt(sCodeName, sDisplayName);
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test int 1", m_oDetailedOutputSettings.getInt(0).getDisplayName());
    assertEquals("int1", m_oDetailedOutputSettings.getInt(0).getCodeName());
    assertEquals("test int 2", m_oDetailedOutputSettings.getInt(1).getDisplayName());
    assertEquals("int2", m_oDetailedOutputSettings.getInt(1).getCodeName());
    assertEquals("test int 3", m_oDetailedOutputSettings.getInt(2).getDisplayName());
    assertEquals("int3", m_oDetailedOutputSettings.getInt(2).getCodeName());
    assertEquals(sCodeName, m_oDetailedOutputSettings.getInt(3).getCodeName());
    assertEquals(sDisplayName, m_oDetailedOutputSettings.getInt(3).getDisplayName());

    m_oDetailedOutputSettings.setSaveFrequency(5);
    assertEquals(5, m_oDetailedOutputSettings.getSaveFrequency());


    m_oDetailedOutputSettings.setSaveFrequency(5);
    assertEquals(5, m_oDetailedOutputSettings.getSaveFrequency());

    //Test cloning
    DetailedOutputSettings oCopyTarget = new DetailedOutputSettings();
    DetailedOutputSettings.copyData(oCopyTarget, m_oDetailedOutputSettings);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals(m_oDetailedOutputSettings.getNumberOfBools(), oCopyTarget.getNumberOfBools());
    assertEquals(m_oDetailedOutputSettings.getNumberOfChars(), oCopyTarget.getNumberOfChars());
    assertEquals(m_oDetailedOutputSettings.getNumberOfFloats(), oCopyTarget.getNumberOfFloats());
    assertEquals(m_oDetailedOutputSettings.getNumberOfInts(), oCopyTarget.getNumberOfInts());
    assertEquals(m_oDetailedOutputSettings.getSaveFrequency(), oCopyTarget.getSaveFrequency());

    //Data member names
   assertEquals(m_oDetailedOutputSettings.getBool(0).getCodeName(),
                 oCopyTarget.getBool(0).getCodeName());
    assertEquals(m_oDetailedOutputSettings.getFloat(0).getDisplayName(),
                 oCopyTarget.getFloat(0).getDisplayName());
    assertEquals(m_oDetailedOutputSettings.getInt(0).getDisplayName(),
                 oCopyTarget.getInt(0).getDisplayName());
    assertEquals(m_oDetailedOutputSettings.getChar(0).getDisplayName(),
                 oCopyTarget.getChar(0).getDisplayName());

    //Make sure the clone was deep
    DataMember oMember = m_oDetailedOutputSettings.getBool(0);
    int iSaveCode = oMember.getCode();
    oMember.setCode(iSaveCode+4);

    assertEquals(iSaveCode, oCopyTarget.getBool(0).getCode());

    /////////////////////////////////
    //Test data member removal
    /////////////////////////////////

    //Invalid index
    m_oDetailedOutputSettings.removeBool(-1);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfBools());
    m_oDetailedOutputSettings.removeBool(1);
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfBools());
    //Good index
    m_oDetailedOutputSettings.removeBool(0);
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    //Float-checking deep cloning...
    assertEquals(1, oCopyTarget.getNumberOfBools());

    //Invalid index
    m_oDetailedOutputSettings.removeChar(-1);
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfChars());
    m_oDetailedOutputSettings.removeChar(5);
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfChars());
    //Good index
    m_oDetailedOutputSettings.removeChar(0);
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test char 2", m_oDetailedOutputSettings.getChar(0).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(2, oCopyTarget.getNumberOfChars());

    //Invalid index
    m_oDetailedOutputSettings.removeFloat(-1);
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    m_oDetailedOutputSettings.removeFloat(5);
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfFloats());
    //Good index
    m_oDetailedOutputSettings.removeFloat(2);
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test float 1", m_oDetailedOutputSettings.getFloat(0).getDisplayName());
    assertEquals("test float 2", m_oDetailedOutputSettings.getFloat(1).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(3, oCopyTarget.getNumberOfFloats());

    //Invalid index
    m_oDetailedOutputSettings.removeInt(-1);
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    m_oDetailedOutputSettings.removeInt(5);
    assertEquals(4, m_oDetailedOutputSettings.getNumberOfInts());
    //Good index
    m_oDetailedOutputSettings.removeInt(2);
    assertEquals(0, m_oDetailedOutputSettings.getNumberOfBools());
    assertEquals(1, m_oDetailedOutputSettings.getNumberOfChars());
    assertEquals(2, m_oDetailedOutputSettings.getNumberOfFloats());
    assertEquals(3, m_oDetailedOutputSettings.getNumberOfInts());
    assertEquals("test int 1", m_oDetailedOutputSettings.getInt(0).getDisplayName());
    assertEquals("test int 2", m_oDetailedOutputSettings.getInt(1).getDisplayName());
    assertEquals("test int 4", m_oDetailedOutputSettings.getInt(2).getDisplayName());
    //Float-checking deep cloning...
    assertEquals(4, oCopyTarget.getNumberOfInts());
  }
}
