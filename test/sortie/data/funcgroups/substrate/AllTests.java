package sortie.data.funcgroups.substrate;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(DetailedSubstrateTest.class);
    suite.addTestSuite(SubstrateTest.class);
    //$JUnit-END$
    return suite;
  }

}
