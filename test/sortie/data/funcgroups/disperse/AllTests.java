package sortie.data.funcgroups.disperse;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(GapSpatialDisperseTest.class);
    suite.addTestSuite(MastingNonSpatialDisperseTest.class);
    suite.addTestSuite(MastingSpatialDisperseTest.class);
    suite.addTestSuite(NonGapSpatialDisperseTest.class);
    suite.addTestSuite(NonSpatialDisperseTest.class);
    suite.addTestSuite(StochDoubleLogTempDepNeighDisperseTest.class);
    suite.addTestSuite(TemperatureDependentNeighborhoodDisperseTest.class);
    //$JUnit-END$
    return suite;
  }

}
