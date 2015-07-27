package sortie.data.funcgroups.statechange;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(PrecipitationClimateChangeTest.class);
    suite.addTestSuite(SeasonalWaterDeficitTest.class);
    suite.addTestSuite(TemperatureClimateChangeTest.class);
    //$JUnit-END$
    return suite;
  }

}
