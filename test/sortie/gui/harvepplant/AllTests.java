package sortie.gui.harvepplant;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(TestHarvestEdit.class);
    suite.addTestSuite(TestMortalityEpisodeEdit.class);
    suite.addTestSuite(TestPlantEdit.class);
    //$JUnit-END$
    return suite;
  }

}
