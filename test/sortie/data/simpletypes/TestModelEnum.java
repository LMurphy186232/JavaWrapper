package sortie.data.simpletypes;

import sortie.ModelTestCase;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;

/**
 * Tests the ModelEnum class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestModelEnum
    extends ModelTestCase {

  /**
   * Tests the ModelEnum equality method
   */
  public void testEquals() {
    try {
      //The object to test against
      ModelEnum oToTest = new ModelEnum(new int[] {1, 2, 3}
                                        , new String[] {"test 1", "test 2",
                                        "test 3"}
                                        , "test descriptor", "test xml tag");
      oToTest.setValue(2);

      assertFalse(oToTest.equals(null)); //null
      assertFalse(oToTest.equals(new Float(1))); //non-ModelEnum

      //ModelEnum with different value
      ModelEnum oToTest2 = new ModelEnum(new int[] {1, 2, 3}
                                         , new String[] {"test 1", "test 2",
                                         "test 3"}
                                         , "test descriptor", "test xml tag");
      oToTest2.setValue(1);
      assertFalse(oToTest.equals(oToTest2));
      //Change the value to make it work
      oToTest2.setValue(2);
      assertTrue(oToTest.equals(oToTest2));

      //ModelEnum with different XML tag
      oToTest2 = null;
      oToTest2 = new ModelEnum(new int[] {1, 2, 3}
                               , new String[] {"test 1", "test 2", "test 3"}
                               , "test descriptor", "test xml tag 1");
      oToTest2.setValue(2);
      assertFalse(oToTest.equals(oToTest2));

      //ModelEnum with different descriptor
      oToTest2 = null;
      oToTest2 = new ModelEnum(new int[] {1, 2, 3}
                               , new String[] {"test 1", "test 2", "test 3"}
                               , "test descriptor 1", "test xml tag");
      oToTest2.setValue(2);
      assertFalse(oToTest.equals(oToTest2));

      //ModelEnum with null allowed values
      oToTest2 = null;
      oToTest2 = new ModelEnum(null, new String[] {"test 1", "test 2", "test 3"}
                               , "test descriptor", "test xml tag");
      assertFalse(oToTest.equals(oToTest2));

      //ModelEnum with null value labels
      oToTest2 = null;
      oToTest2 = new ModelEnum(new int[] {1, 2, 3}
                               , null, "test descriptor", "test xml tag");
      assertFalse(oToTest.equals(oToTest2));

      //ModelEnum with different allowed values
      oToTest2 = null;
      oToTest2 = new ModelEnum(new int[] {0, 2, 3}
                               , new String[] {"test 1", "test 2", "test 3"}
                               , "test descriptor", "test xml tag");
      oToTest2.setValue(2);
      assertFalse(oToTest.equals(oToTest2));

      //ModelEnum with different value labels
      oToTest2 = null;
      oToTest2 = new ModelEnum(new int[] {1, 2, 3}
                               , new String[] {"test 1", "test 2 diff",
                               "test 3"}
                               , "test descriptor", "test xml tag");
      oToTest2.setValue(2);
      assertFalse(oToTest.equals(oToTest2));

      System.out.println("ModelEnum test succeeded.");
    }
    catch (ModelException oErr) {
      fail("ModelEnum equality test failed with message " + oErr.getMessage());
    }
  }

  public void testClone() {
    try {
      //The object to test against
      ModelEnum oToTest = new ModelEnum(new int[] {1, 2, 3}
                                        , new String[] {"test 1", "test 2",
                                        "test 3"}
                                        , "test descriptor", "test xml tag");
      oToTest.setValue(2);

      ModelEnum oClone = (ModelEnum) oToTest.clone();
      assertEquals(oClone.getValue(), oToTest.getValue());
      String[] p_sCloneLabels = oClone.getAllowedValueLabels(),
               p_sToTestLabels = oToTest.getAllowedValueLabels();
      int[] p_iCloneVals = oClone.getAllowedValues(),
            p_iToTestVals = oToTest.getAllowedValues();
      assertEquals(p_sCloneLabels.length, p_sToTestLabels.length);
      assertEquals(p_iCloneVals.length, p_iToTestVals.length);
      assertEquals(p_sCloneLabels.length, p_iCloneVals.length);
      int i;
      for (i = 0; i < p_sCloneLabels.length; i++) {
        assertEquals(p_sCloneLabels[i], p_sToTestLabels[i]);
        assertEquals(p_iCloneVals[i], p_iToTestVals[i]);
      }

      //Now change the original and verify that the clone does not change
      oToTest.setValue(1);
      assertEquals(2, oClone.getValue());

    } catch (ModelException oErr) {
      fail("ModelEnum equality test failed with message " + oErr.getMessage());
    }

  }

}
