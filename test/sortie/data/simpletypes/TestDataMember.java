package sortie.data.simpletypes;

import sortie.ModelTestCase;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;

/**
 * Tests the DataMember class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestDataMember
    extends ModelTestCase {

  /**
   * Test of constructor
   */
  public void testDataMember() {
    String sDisplayName = "test display name";
    String sCodeName = "test code name";
    int iType = DataMember.BOOLEAN;
    DataMember oDataMember = null;
    try {
      oDataMember = new DataMember(sDisplayName, sCodeName, iType);
    }
    catch (ModelException oErr) {
      fail("DataMember constructor failed with message " + oErr.getMessage());
    }

    oDataMember.setCode(6);
    assertEquals(DataMember.BOOLEAN, oDataMember.getType());
    assertEquals(sDisplayName, oDataMember.getDisplayName());
    assertEquals(sCodeName, oDataMember.getCodeName());
    assertEquals(6, oDataMember.getCode());

    System.out.println("Constructor test succeeded for DataMember.");

    //Exception testing with the constructor
    try {
      oDataMember = null;
      oDataMember = new DataMember(sDisplayName, sCodeName, 25);
      fail("Data member constructor failed to spot bad type code.");
    }
    catch (ModelException oErr) {
      System.out.println(
          "Exception test for constructor test succeeded for DataMember.");
    }
  }

  /**
   * Tests the clone() method.
   */
  public void testClone() {
    String sDisplayName = "test display name";
    String sCodeName = "test code name";
    int iType = DataMember.BOOLEAN;
    int iCode = 6;
    DataMember oDataMember = null;
    try {
      oDataMember = new DataMember(sDisplayName, sCodeName, iType);
    }
    catch (ModelException oErr) {
      fail("DataMember constructor failed with message " + oErr.getMessage());
    }
    oDataMember.setCode(iCode);

    DataMember oClone = (DataMember) oDataMember.clone();
    assertEquals(oDataMember.getCode(), oClone.getCode());
    assertEquals(oDataMember.getCodeName(), oClone.getCodeName());
    assertEquals(oDataMember.getDisplayName(), oClone.getDisplayName());
    assertEquals(oDataMember.getType(), oClone.getType());

    //Now make sure it's a deep clone
    oDataMember.setCode(8);
    assertEquals(iCode, oClone.getCode());
    oDataMember = null;
    assertEquals(iCode, oClone.getCode());
    assertEquals(sCodeName, oClone.getCodeName());
    assertEquals(sDisplayName, oClone.getDisplayName());
    assertEquals(iType, oClone.getType());

    System.out.println("Clone test succeeded for DataMember.");

  }

}
