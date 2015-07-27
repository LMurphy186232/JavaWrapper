package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.nci.NCIMasterQuadratTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(AbsoluteGrowthBALimitedTest.class);
    suite.addTestSuite(AbsoluteGrowthRadialLimitedTest.class);
    suite.addTestSuite(AbsoluteUnlimitedTest.class);
    suite.addTestSuite(AllometricDiamTest.class);
    suite.addTestSuite(AllometricHeightTest.class);
    suite.addTestSuite(BrowsedRelativeGrowthTest.class);
    suite.addTestSuite(ConstantBATest.class);
    suite.addTestSuite(ConstantRadialTest.class);
    suite.addTestSuite(DoubleMMRelTest.class);
    suite.addTestSuite(LaggedPostHarvestGrowthTest.class);
    suite.addTestSuite(LinearBiLevelTest.class);
    suite.addTestSuite(LogBiLevelTest.class);
    suite.addTestSuite(LogisticTest.class);
    suite.addTestSuite(LognormalTest.class);
    suite.addTestSuite(MichMenNegTest.class);
    suite.addTestSuite(MichMenPhotoinhibitionTest.class);
    suite.addTestSuite(NCIMasterQuadratTest.class);
    suite.addTestSuite(PowerHeightTest.class);
    suite.addTestSuite(PRSemiStochDiamOnlyTest.class);
    suite.addTestSuite(PRStormBiLevelTest.class);
    suite.addTestSuite(RelativeGrowthTest.class);
    suite.addTestSuite(ShadedLinearGrowthTest.class);
    suite.addTestSuite(SimpleLinearTest.class);
    suite.addTestSuite(SizeDepLogisticTest.class);
    //$JUnit-END$
    return suite;
  }

}
