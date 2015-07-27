package sortie.data.simpletypes;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(ModelVectorTest.class);
    suite.addTestSuite(TestDataMember.class);
    suite.addTestSuite(TestDataMemberData.class);
    suite.addTestSuite(TestDetailedGridSettings.class);
    suite.addTestSuite(TestDetailedOutputSettings.class);
    suite.addTestSuite(TestModelEnum.class);
    //$JUnit-END$
    return suite;
  }

}
