package sortie.data.funcgroups.seedpredation;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(DensDepRodentSeedPredationTest.class);
    suite.addTestSuite(FuncResponseSeedPredationLnkTest.class);
    suite.addTestSuite(FuncResponseSeedPredationTest.class);
    suite.addTestSuite(NeighborhoodSeedPredationLnkTest.class);
    suite.addTestSuite(NeighborhoodSeedPredationTest.class);
    //$JUnit-END$
    return suite;
  }

}
