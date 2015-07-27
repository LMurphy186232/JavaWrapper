package sortie.data.funcgroups.mortality;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(AggregatedMortalityTest.class);
    suite.addTestSuite(BCMortTest.class);
    suite.addTestSuite(BrowsedStochasticMortalityTest.class);
    suite.addTestSuite(ClimateCompDepNeighborhoodSurvivalTest.class);
    suite.addTestSuite(CompetitionMortalityTest.class);
    suite.addTestSuite(DensitySelfThinningGompertzTest.class);
    suite.addTestSuite(DensitySelfThinningTest.class);
    suite.addTestSuite(ExpResourceMortalityTest.class);
    suite.addTestSuite(GMFMortTest.class);
    suite.addTestSuite(GrowthResourceMortalityTest.class);
    suite.addTestSuite(HeightGLIWeibullMortalityTest.class);
    suite.addTestSuite(InsectInfestationMortalityTest.class);
    suite.addTestSuite(LogisticBiLevelMortalityTest.class);
    suite.addTestSuite(PostHarvestSkiddingMortTest.class);
    suite.addTestSuite(SelfThinMortTest.class);
    suite.addTestSuite(SenescenceMortTest.class);
    suite.addTestSuite(SizeDependentLogisticMortalityTest.class);
    suite.addTestSuite(StochasticBiLevelLightMortalityTest.class);
    suite.addTestSuite(StochasticMortTest.class);
    suite.addTestSuite(SuppressionDurationMortTest.class);
    suite.addTestSuite(TempDepNeighborhoodSurvivalTest.class);
    suite.addTestSuite(WeibullSnagMortTest.class);
    //$JUnit-END$
    return suite;
  }

}
