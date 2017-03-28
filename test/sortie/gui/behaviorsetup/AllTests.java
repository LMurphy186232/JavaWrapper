package sortie.gui.behaviorsetup;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(TestHarvestInterface.class);
    suite.addTestSuite(TestClimateImporterEditor.class);
    //$JUnit-END$
    return suite;
  }

}
