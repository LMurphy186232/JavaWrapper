package sortie.data.funcgroups.analysis;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(BoleVolumeCalculatorTest.class);
    suite.addTestSuite(CarbonValueCalculatorTest.class);
    suite.addTestSuite(ConditOmegaCalculatorTest.class);
    suite.addTestSuite(DimensionAnalysisTest.class);
    suite.addTestSuite(FoliarChemistryTest.class);
    suite.addTestSuite(MerchValueCalculatorTest.class);
    suite.addTestSuite(PartitionedDBHBiomassTest.class);
    suite.addTestSuite(PartitionedHeightBiomassTest.class);
    suite.addTestSuite(RipleysKCalculatorTest.class);
    suite.addTestSuite(StormKilledPartitionedDBHBiomassTest.class);
    suite.addTestSuite(StormKilledPartitionedHeightBiomassTest.class);
    suite.addTestSuite(VolumeCalculatorTest.class);
    //$JUnit-END$
    return suite;
  }

}
