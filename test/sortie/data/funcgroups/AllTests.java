package sortie.data.funcgroups;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(MortalityUtilitiesBehaviorsTest.class);
    suite.addTestSuite(OutputBehaviorsTest.class);
    suite.addTestSuite(TestAllometry.class);
    suite.addTestSuite(TestBehavior.class);
    suite.addTestSuite(TestBehaviorTypeBase.class);
    suite.addTestSuite(TestEstablishmentBehaviors.class);
    suite.addTestSuite(TestGrid.class);
    suite.addTestSuite(TestGrowthBehaviors.class);
    suite.addTestSuite(TestLightBehaviors.class);
    suite.addTestSuite(TestMortalityBehaviors.class);
    suite.addTestSuite(TestOtherDisturbanceBehaviors.class);
    suite.addTestSuite(TestParseReader.class);
    suite.addTestSuite(TestPlot.class);
    suite.addTestSuite(TestSeedPredationBehaviors.class);
    suite.addTestSuite(TestSubstrateBehaviors.class);
    suite.addTestSuite(TestTreeBehavior.class);
    suite.addTestSuite(TestTreePopulation.class);
    suite.addTestSuite(TestValidationHelpers.class);
    //$JUnit-END$
    return suite;
  }

}
