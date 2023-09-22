package sortie.data.funcgroups.nci;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(CrowdingEffectDefaultTest.class);
    suite.addTestSuite(CrowdingEffectTempDepTest.class);
    suite.addTestSuite(DamageEffectDefaultTest.class);
    suite.addTestSuite(DefaultSizeEffectTest.class);
    suite.addTestSuite(GaussianNitrogenEffectTest.class);
    suite.addTestSuite(InfectionEffectSizeDependentTest.class);
    suite.addTestSuite(InfectionEffectTest.class);
    suite.addTestSuite(NCILargerNeighborsTest.class);
    suite.addTestSuite(NCIMasterGrowthTest.class);
    suite.addTestSuite(NCIMasterMortalityTest.class);
    suite.addTestSuite(NCIMasterQuadratTest.class);
    suite.addTestSuite(NCINeighborBATest.class);
    suite.addTestSuite(NCIParameterEditTest.class);
    suite.addTestSuite(NCITermBARatioDBHDefaultTest.class);
    suite.addTestSuite(NCITermBARatioTest.class);
    suite.addTestSuite(NCITermDefaultTest.class);
    suite.addTestSuite(NCITermNCITempDepBARatioDBHDefaultTest.class);
    suite.addTestSuite(NCITermNCITempDepBARatioTest.class);
    suite.addTestSuite(NCITermWithNeighborDamageTest.class);
    suite.addTestSuite(NCIWithSeedlingsTest.class);
    suite.addTestSuite(PrecipitationEffectDoubleLogisticTest.class);
    suite.addTestSuite(PrecipitationEffectDoubleNoLocalDiffTest.class);
    suite.addTestSuite(PrecipitationEffectWeibullTest.class);
    suite.addTestSuite(SizeEffectCompoundExpInfTest.class);
    suite.addTestSuite(SizeEffectCompoundExponentialTest.class);
    suite.addTestSuite(SizeEffectLowerBoundedTest.class);
    suite.addTestSuite(SizeEffectShiftedLogInfTest.class);
    suite.addTestSuite(SizeEffectShiftedLognormalTest.class);
    suite.addTestSuite(TemperatureEffectDoubleLogisticTest.class);
    suite.addTestSuite(TemperatureEffectDoubleNoLocalDiffTest.class);
    suite.addTestSuite(TemperatureEffectWeibullTest.class);
    //$JUnit-END$
    return suite;
  }

}
