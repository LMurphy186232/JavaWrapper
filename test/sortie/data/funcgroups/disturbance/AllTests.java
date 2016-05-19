package sortie.data.funcgroups.disturbance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(CompetitionHarvestTest.class);
    suite.addTestSuite(DensDepInfestationTest.class);
    suite.addTestSuite(EpisodicMortalityTest.class);
    suite.addTestSuite(GeneralizedHarvestRegimeTest.class);
    suite.addTestSuite(HarvestInterfaceTest.class);
    suite.addTestSuite(InsectInfestationTest.class);
    suite.addTestSuite(RandomBrowseTest.class);
    suite.addTestSuite(SelectionHarvestTest.class);
    suite.addTestSuite(StormDamageApplierTest.class);
    suite.addTestSuite(StormDamageKillerTest.class);
    suite.addTestSuite(StormDirectKillerTest.class);
    suite.addTestSuite(StormTest.class);
    suite.addTestSuite(TestHarvestBehaviors.class);
    suite.addTestSuite(TestHarvestData.class);
    suite.addTestSuite(WindstormTest.class);
    //$JUnit-END$
    return suite;
  }

}
