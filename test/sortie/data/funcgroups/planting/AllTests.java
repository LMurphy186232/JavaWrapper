package sortie.data.funcgroups.planting;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(TestPlantingBehaviors.class);
    suite.addTestSuite(TestPlantingData.class);
    //$JUnit-END$
    return suite;
  }

}
