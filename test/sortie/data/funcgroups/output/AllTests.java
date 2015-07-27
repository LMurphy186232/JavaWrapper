package sortie.data.funcgroups.output;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(TestDetailedOutput.class);
    suite.addTestSuite(TestShortOutput.class);
    //$JUnit-END$
    return suite;
  }

}
