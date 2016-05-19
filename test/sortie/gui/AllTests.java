package sortie.gui;

import sortie.gui.behaviorsetup.TestHarvestInterface;
import sortie.gui.modelflowsetup.ModelFlowSetupTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(TestDetailedOutputGridSetup.class);
    suite.addTestSuite(TestGUIManager.class);
    suite.addTestSuite(TestLightPlusModelSetup.class);
    suite.addTestSuite(TestShortOutputFileSetup.class);
    suite.addTestSuite(TestTreeSetup.class);
    //$JUnit-END$
    return suite;
  }

}
