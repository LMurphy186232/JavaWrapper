package sortie.data.funcgroups.establishment;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(ConspecificTreeDensitySeedSurvivalTest.class);
    suite.addTestSuite(DensityDependentSeedSurvivalTest.class);
    suite.addTestSuite(GapSubstrateSeedSurvivalTest.class);
    suite.addTestSuite(LightDependentSeedSurvivalTest.class);
    suite.addTestSuite(MicroEstablishmentTest.class);
    suite.addTestSuite(MicrotopographicSubstrateSeedSurvivalTest.class);
    suite.addTestSuite(NoGapSubstrateSeedSurvivalTest.class);
    suite.addTestSuite(ProportionalSeedSurvivalTest.class);
    suite.addTestSuite(StormLightDependentSeedSurvivalTest.class);
    //$JUnit-END$
    return suite;
  }

}
